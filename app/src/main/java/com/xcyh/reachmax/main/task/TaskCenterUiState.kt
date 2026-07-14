package com.xcyh.reachmax.main.task

import androidx.compose.runtime.Immutable
import com.xcyh.reachmax.model.bean.task.TaskBean
import com.xcyh.reachmax.model.constant.TaskStatus

/**
 * 任务中心状态数据。
 *
 * 对齐旧实现：4 个 Tab（全部/未开始/已完成/已取消）各自独立分页，
 * 分页规则复刻 [com.baidu.baselibrary.base.fragment.BaseListFragment]：
 * 单页 pageSize=10，返回条数 >= 10 时认为还有更多。
 */
data class TaskCenterUiState(
    val tabTitles: List<String> = listOf("全部", "未开始", "已完成", "已取消"),
    /** 每个 Tab 对应的任务状态，顺序与 [tabTitles] 一致。 */
    val tabStatuses: List<Int> = listOf(
        TaskStatus.ALL,
        TaskStatus.NOT_START,
        TaskStatus.FINISH,
        TaskStatus.CANCEL,
    ),
    val selectedTabIndex: Int = 0,
    val tabs: Map<Int, TabState> = emptyMap(),
)

/** 单个 Tab 的分页/列表状态。 */
@Immutable
data class TabState(
    val items: List<TaskBean> = emptyList(),
    val page: Int = 1,
    val hasMore: Boolean = false,
    val isInitialLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: TabError = TabError.NONE,
) {
    /** 是否已完成首次加载（不论成功失败），用于区分初始骨架态与空态。 */
    val isLoaded: Boolean get() = !isInitialLoading
}

/** Tab 的错误/空态类型，对应 BaseListFragment 的 EmptyView 三态。 */
enum class TabError {
    /** 无错误。 */
    NONE,
    /** 首页加载失败且无数据 —— 无网络。 */
    NET,
    /** 首页加载失败且无数据 —— 服务器错误。 */
    DATA,
}

/** 一次性 UI 事件。 */
sealed interface TaskCenterEvent {
    data class Toast(val message: String) : TaskCenterEvent
    /** 点击取消任务，请求 Activity 弹出确认框。 */
    data class ShowCancelConfirm(val task: TaskBean) : TaskCenterEvent
}
