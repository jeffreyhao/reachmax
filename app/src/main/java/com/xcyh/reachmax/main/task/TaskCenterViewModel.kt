package com.xcyh.reachmax.main.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.base.net.bean.CacheEnum
import com.base.net.request.BaseRequest
import com.base.util.net.NetworkUtil
import com.baidu.baselibrary.util.App
import com.xcyh.reachmax.model.bean.task.TaskBean
import com.xcyh.reachmax.model.bean.task.TaskBody
import com.xcyh.reachmax.model.bean.task.TaskCenter
import com.xcyh.reachmax.model.constant.TaskStatus
import com.xcyh.reachmax.model.manager.Pitcher
import com.xcyh.reachmax.model.request.RequestCallback
import com.xcyh.reachmax.model.request.Url
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * 任务中心 ViewModel：复用回调式 [BaseRequest] 完成网络请求，并把结果桥接到
 * [StateFlow]/[SharedFlow] 供 Compose 消费。
 *
 * 行为对齐旧版 `TaskListPresenter` + `TaskListFragment` + `BaseListFragment`：
 * - 列表请求 `get_ad_tasks`（GET），`status` 仅在非 ALL 时传。
 * - 分页 pageSize=10，返回条数 >= 10 时 page++ 并允许加载更多。
 * - 取消任务 `update_task`（GET），成功后局部更新对应 item（等价 `notifyItemChanged`）。
 */
class TaskCenterViewModel : ViewModel() {

    private val pageSize = 10

    private val _uiState = MutableStateFlow(TaskCenterUiState())
    val uiState: StateFlow<TaskCenterUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<TaskCenterEvent>(
        replay = 0,
        extraBufferCapacity = 4,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val events: SharedFlow<TaskCenterEvent> = _events.asSharedFlow()

    private val request = BaseRequest()

    init {
        // 进入即加载默认 Tab（对齐原 ViewPager2 默认页 ALL 自动 initData）
        _uiState.value.tabStatuses.firstOrNull()?.let { status ->
            loadFirst(status)
        }
    }

    override fun onCleared() {
        super.onCleared()
        request.disposeAllSubscribe()
    }

    fun selectTab(index: Int) {
        val state = _uiState.value
        if (index == state.selectedTabIndex || index !in state.tabStatuses.indices) return
        _uiState.update { it.copy(selectedTabIndex = index) }
        // 懒加载：切换到尚未加载过的 Tab 时触发首屏请求
        val status = state.tabStatuses[index]
        if (_uiState.value.tabs[status]?.isLoaded != true) {
            loadFirst(status)
        }
    }

    /** 下拉刷新（对齐 BaseListFragment.onRefresh：重置 page=1 再请求）。 */
    fun refresh(status: Int) {
        if (!isNetAvailable()) {
            emit(TaskCenterEvent.Toast("网络连接不可用，请检查网络"))
            updateTab(status) { it.copy(isRefreshing = false) }
            return
        }
        updateTab(status) { it.copy(isRefreshing = true, error = TabError.NONE) }
        fetchList(status, page = 1, isRefresh = true)
    }

    /** 上拉加载更多（对齐 BaseListFragment.onLoadMore：仅当 hasMore 才请求）。 */
    fun loadMore(status: Int) {
        val tab = _uiState.value.tabs[status] ?: return
        if (tab.isLoadingMore || !tab.hasMore) return
        if (!isNetAvailable()) {
            emit(TaskCenterEvent.Toast("网络连接不可用，请检查网络"))
            return
        }
        updateTab(status) { it.copy(isLoadingMore = true) }
        fetchList(status, page = tab.page, isRefresh = false)
    }

    /** 请求取消任务（先经 Activity 弹确认框，确认后调用）。 */
    fun requestCancel(task: TaskBean) {
        emit(TaskCenterEvent.ShowCancelConfirm(task))
    }

    /** 真正执行取消请求。 */
    fun cancelTask(task: TaskBean) {
        val paramMap = linkedMapOf<String, Any>(
            "name" to task.name,
            "task_id" to task.id,
            "status" to TaskStatus.CANCEL,
            "time_zone" to task.time_zone,
            "start_time" to task.start_time,
            "action" to task.action,
        )
        request.getRequest(
            CacheEnum.NET_ONLY,
            Url.API_TASK_MODIFY,
            authCode(),
            paramMap,
            paramMap,
            /* loading */ false,
            /* cacheListener */ null,
            object : RequestCallback<TaskBody>() {
                override fun onSuccess(content: String, t: TaskBody) {
                    if (t == null) {
                        emit(TaskCenterEvent.Toast("定时任务取消失败"))
                    } else {
                        emit(TaskCenterEvent.Toast("定时任务取消成功"))
                        applyTaskCanceled(t.task)
                    }
                }

                override fun onFail(e: com.base.net.bean.ApiException) {
                    val msg = e?.msg?.takeIf { it.isNotEmpty() } ?: ""
                    emit(
                        TaskCenterEvent.Toast(
                            if (msg.isEmpty()) "定时任务取消失败" else "定时任务取消失败: \n$msg",
                        ),
                    )
                }
            },
        )
    }

    private fun loadFirst(status: Int) {
        if (!isNetAvailable()) {
            updateTab(status) {
                it.copy(isInitialLoading = false, error = TabError.NET)
            }
            return
        }
        updateTab(status) { it.copy(isInitialLoading = true, error = TabError.NONE) }
        fetchList(status, page = 1, isRefresh = false)
    }

    private fun fetchList(status: Int, page: Int, isRefresh: Boolean) {
        val paramMap = linkedMapOf<String, Any>(
            "page" to page,
            "pageSize" to pageSize,
        )
        if (status != TaskStatus.ALL) {
            paramMap["status"] = status
        }
        request.getRequest(
            CacheEnum.NET_ONLY,
            Url.API_TASK_LIST,
            authCode(),
            paramMap,
            paramMap,
            /* loading */ isRefresh, // 首页/刷新时显示全局 loading（对齐原 page==1 loading 语义）
            /* cacheListener */ null,
            object : RequestCallback<TaskCenter>() {
                override fun onSuccess(content: String, t: TaskCenter) {
                    val list: List<TaskBean> = t?.data?.task ?: emptyList()
                    applyListSuccess(status, page, list)
                }

                override fun onFail(e: com.base.net.bean.ApiException) {
                    applyListFail(status, isRefresh)
                }
            },
        )
    }

    /** 列表请求成功：分页状态机（复刻 BaseListFragment.onRequestSuccess:151-176）。 */
    private fun applyListSuccess(status: Int, page: Int, list: List<TaskBean>) {
        val hasMore = list.size >= pageSize
        updateTab(status) { tab ->
            val merged = if (page == 1) list else tab.items + list
            tab.copy(
                items = merged,
                page = if (hasMore) page + 1 else page,
                hasMore = hasMore,
                isInitialLoading = false,
                isRefreshing = false,
                isLoadingMore = false,
                error = if (merged.isEmpty()) TabError.NONE else tab.error,
            )
        }
    }

    /** 列表请求失败（复刻 BaseListFragment.onRequestFail:178-197）。 */
    private fun applyListFail(status: Int, isRefresh: Boolean) {
        updateTab(status) { tab ->
            val isFirstPageAndEmpty = tab.items.isEmpty()
            val error = when {
                !isFirstPageAndEmpty -> TabError.NONE // 已有数据，仅 Toast，不切空态
                !isNetAvailable() -> TabError.NET
                else -> TabError.DATA
            }
            tab.copy(
                isInitialLoading = false,
                isRefreshing = false,
                isLoadingMore = false,
                error = error,
            )
        }
    }

    /** 取消成功：局部更新该 item（等价原 onTaskCanceled 的 notifyItemChanged）。 */
    private fun applyTaskCanceled(updated: TaskBean?) {
        if (updated == null) return
        val current = _uiState.value
        val newTabs = current.tabs.toMutableMap()
        var changed = false
        for ((status, tab) in current.tabs) {
            val items = tab.items
            val idx = items.indexOfFirst { it.id == updated.id }
            if (idx >= 0) {
                val target = items[idx]
                target.update(updated) // 复用 TaskBean.update 局部刷新
                newTabs[status] = tab.copy(items = items.toList())
                changed = true
            }
        }
        if (changed) {
            _uiState.update { it.copy(tabs = newTabs) }
        }
    }

    private fun updateTab(status: Int, block: (TabState) -> TabState) {
        _uiState.update { state ->
            val current = state.tabs[status] ?: TabState()
            state.copy(tabs = state.tabs + (status to block(current)))
        }
    }

    private fun emit(event: TaskCenterEvent) {
        // 网络回调线程不固定，用主线程发射保证 Compose 消费稳定
        viewModelScope.launch { _events.emit(event) }
    }

    private fun authCode(): String = "Bearer " + Pitcher.getInstance().token

    private fun isNetAvailable(): Boolean = NetworkUtil.isNetAvailable(App.getContext())
}
