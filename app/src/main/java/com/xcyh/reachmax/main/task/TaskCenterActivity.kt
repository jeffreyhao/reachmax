package com.xcyh.reachmax.main.task

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.baidu.baselibrary.util.ui.AnimationUtil
import com.gyf.immersionbar.ImmersionBar
import com.tencent.common.util.ToastUtils
import com.xcyh.reachmax.R
import com.xcyh.reachmax.main.task.compose.TaskCenterScreen
import com.xcyh.reachmax.model.bean.task.TaskBean

/**
 * 任务中心（Compose 实现）。
 *
 * 原类继承 BaseActivity<ActivityTaskCenterBinding, EmptyPresenter>，用 MagicIndicator +
 * ViewPager2 + 4 个 TaskListFragment 渲染；现改为 ComponentActivity，用 Compose 渲染、
 * ViewModel 管理状态、[com.base.net.request.BaseRequest] 复用网络层。
 * 类名/包路径保持不变，外部引用（MineFragment、AndroidManifest）无需改动。
 */
class TaskCenterActivity : ComponentActivity() {

    private val viewModel: TaskCenterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupImmersive()
        setContent {
            val state by viewModel.uiState.collectAsState()
            var pendingCancelTask by remember { mutableStateOf<TaskBean?>(null) }

            LaunchedEffect(Unit) {
                viewModel.events.collect { event ->
                    when (event) {
                        is TaskCenterEvent.Toast -> ToastUtils.showCenterShort(event.message)
                        is TaskCenterEvent.ShowCancelConfirm -> pendingCancelTask = event.task
                    }
                }
            }

            TaskCenterScreen(
                state = state,
                onTabSelected = viewModel::selectTab,
                onRefresh = viewModel::refresh,
                onLoadMore = viewModel::loadMore,
                onCancelClick = viewModel::requestCancel,
                onBack = { finish() },
            )

            pendingCancelTask?.let { task ->
                AlertDialog(
                    onDismissRequest = { pendingCancelTask = null },
                    title = { Text("提示") },
                    text = { Text("确定要取消任务吗？") },
                    confirmButton = {
                        TextButton(onClick = {
                            pendingCancelTask = null
                            viewModel.cancelTask(task)
                        }) { Text("确定") }
                    },
                    dismissButton = {
                        TextButton(onClick = { pendingCancelTask = null }) {
                            Text("取消")
                        }
                    },
                )
            }
        }
    }

    private fun setupImmersive() {
        ImmersionBar.with(this)
            .reset()
            .transparentStatusBar()
            .transparentNavigationBar()
            .statusBarDarkFont(true)
            .navigationBarDarkIcon(true)
            .init()
    }

    override fun finish() {
        super.finish()
        AnimationUtil.overridePendingTransition(this, R.anim.anim_none, R.anim.push_right_out)
    }
}
