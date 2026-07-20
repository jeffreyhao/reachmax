package com.xcyh.reachmax.main.task.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baidu.baselibrary.util.date.DateUtil
import com.xcyh.reachmax.main.task.TabError
import com.xcyh.reachmax.main.task.TabState
import com.xcyh.reachmax.model.bean.task.TaskBean
import com.xcyh.reachmax.model.constant.TaskStatus

// 卡片/文本颜色对齐旧资源
private val CardBg = Color.White
private val LabelColor = Color(0xFF333333)
private val ValueColor = Color(0xFF999999)
private val DividerColor = Color(0xFFF0F0F0)
private val StateBgNotStart = Color(0xFFF9983A)
private val StateBgFinish = Color(0xFF16BD4F)
private val StateBgCancel = Color(0xFFADADAD)
private val CancelBtnColor = Color(0xFF559AF0)

/**
 * 单个 Tab 的列表内容：下拉刷新 + 上拉加载更多 + 空态/错误态 + 任务卡片列表。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListContent(
    tabState: TabState,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    onCancelClick: (TaskBean) -> Unit,
    modifier: Modifier = Modifier,
) {
    when {
        tabState.isInitialLoading -> InitialLoading(modifier)
        tabState.error != TabError.NONE && tabState.items.isEmpty() -> {
            EmptyOrErrorState(
                error = tabState.error,
                onRetry = onRefresh,
                modifier = modifier,
            )
        }
        tabState.items.isEmpty() -> EmptyState(modifier = modifier)
        else -> TaskList(
            tabState = tabState,
            onRefresh = onRefresh,
            onLoadMore = onLoadMore,
            onCancelClick = onCancelClick,
            modifier = modifier,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskList(
    tabState: TabState,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    onCancelClick: (TaskBean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val listState = rememberLazyListState()
    // 触底加载更多：滑到接近底部且还有更多时触发
    val reachedBottom by remember {
        derivedStateOf {
            val last = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
            last >= 0 && last >= tabState.items.size - 3
        }
    }
    LaunchedEffect(reachedBottom, tabState.hasMore) {
        if (reachedBottom && tabState.hasMore && !tabState.isLoadingMore) {
            onLoadMore()
        }
    }

    PullToRefreshBox(
        isRefreshing = tabState.isRefreshing,
        onRefresh = onRefresh,
        modifier = modifier.fillMaxSize(),
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 20.dp),
        ) {
            itemsIndexed(
                items = tabState.items,
                key = { _, task -> task.id },
            ) { index, task ->
                TaskItem(
                    task = task,
                    showDivider = index != 0, // 首条不显示分割线（复刻 setTop position==0）
                    onCancelClick = onCancelClick,
                )
            }
            if (tabState.isLoadingMore) {
                item(key = "footer_loading") {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp,
                            color = CancelBtnColor,
                        )
                    }
                }
            }
        }
    }
}

/**
 * 任务卡片，对齐旧 [com.xcyh.reachmax.main.task.TaskListAdapter.setData]。
 */
@Composable
private fun TaskItem(
    task: TaskBean,
    showDivider: Boolean,
    onCancelClick: (TaskBean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        // 首条之外显示顶部 0.67dp 分割线（复刻 item_task_divider）
        if (showDivider) {
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(0.67.dp)
                    .background(DividerColor),
            )
        } else {
            // 首条顶部留 15dp（复刻 TaskListAdapter.setTop position==0 的 topMargin=dp_15）
            Spacer(Modifier.height(15.dp))
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(CardBg)
                .padding(15.dp),
        ) {
            // 第一行：任务名称（左）+ 状态徽章（右）
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp), // 对齐原 tv_task_name marginBottom=15dp
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "任务名称",
                    color = LabelColor,
                    fontSize = 13.sp,
                    modifier = Modifier.width(80.dp),
                )
                Text(
                    text = task.name ?: "",
                    color = ValueColor,
                    fontSize = 14.sp,
                    modifier = Modifier.weight(1f),
                    maxLines = 2,
                )
                StateBadge(status = task.status)
            }
            InfoRow(label = "动作类型", value = task.actionString ?: "")
            InfoRow(label = "时区", value = "时区")
            InfoRow(label = "定时器类型", value = "一次性")
            InfoRow(
                label = "时间设置",
                value = formatTime(task.start_time),
                bottomPadding = 5.dp, // 对齐原 tv_time_setting marginBottom=5dp
            )
            InfoRow(
                label = "所属账号",
                value = "launch_id：" + task.launch_id.toString()
                    + "\n" + "campaign：" + idOrDash(task.campaign_id)
                    + "\n" + "adset：" + idOrDash(task.adset_id)
                    + "\n" + "ad：" + idOrDash(task.ad_id),
                valueFontSize = 12.sp,
            )

            if (task.status == TaskStatus.NOT_START) {
                Spacer(Modifier.height(15.dp))
                CancelButton(onClick = { onCancelClick(task) })
            }
        }
    }
}

@Composable
private fun InfoRow(
    label: String,
    value: String,
    bottomPadding: androidx.compose.ui.unit.Dp = 15.dp,
    valueFontSize: androidx.compose.ui.unit.TextUnit = 14.sp,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = bottomPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            color = LabelColor,
            fontSize = 13.sp,
            modifier = Modifier.width(80.dp),
        )
        Text(
            text = value,
            color = ValueColor,
            fontSize = valueFontSize,
        )
    }
}

@Composable
private fun StateBadge(status: Int) {
    val (text, bg) = when (status) {
        TaskStatus.NOT_START -> "未开始" to StateBgNotStart
        TaskStatus.FINISH -> "已完成" to StateBgFinish
        TaskStatus.CANCEL -> "已取消" to StateBgCancel
        else -> "" to Color.Transparent
    }
    if (text.isEmpty()) return
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bg)
            .padding(horizontal = 8.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Normal,
        )
    }
}

/**
 * 取消任务按钮：1dp 边框 #559AF0、圆角 4dp、透明背景、82dp×30dp（复刻 bg_task_btn + btn_cancel_task）。
 * 右对齐（对齐原 layout_alignParentEnd）。
 */
@Composable
private fun CancelButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd,
    ) {
        Surface(
            shape = RoundedCornerShape(4.dp),
            color = Color.Transparent,
            border = BorderStroke(1.dp, CancelBtnColor),
            onClick = onClick,
            modifier = Modifier.size(width = 82.dp, height = 30.dp),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "取消任务",
                    color = CancelBtnColor,
                    fontSize = 13.sp,
                )
            }
        }
    }
}

@Composable
private fun InitialLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(28.dp),
            strokeWidth = 2.dp,
            color = CancelBtnColor,
        )
    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier, text: String = "无数据") {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = text, color = ValueColor, fontSize = 14.sp)
        }
    }
}

@Composable
private fun EmptyOrErrorState(
    error: TabError,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val text = when (error) {
        TabError.NET -> "网络异常，请稍后重试"
        TabError.DATA -> "出错了，请稍后重试"
        TabError.NONE -> "无数据"
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onRetry,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = text, color = ValueColor, fontSize = 14.sp)
    }
}

/** 账号 id 为 null/空串时用 "-" 占位。 */
private fun idOrDash(value: String?): String = if (value.isNullOrEmpty()) "-" else value

/** ISO8601 -> yyyy-MM-dd HH:mm:ss，复刻 TaskListAdapter 的时间格式化。 */
private fun formatTime(start: String?): String {
    if (start.isNullOrEmpty()) return ""
    return try {
        DateUtil.parseFromISO8061(start, DateUtil.YMD_T_HMSs, DateUtil.formatYMDHMS)
    } catch (e: Throwable) {
        ""
    }
}
