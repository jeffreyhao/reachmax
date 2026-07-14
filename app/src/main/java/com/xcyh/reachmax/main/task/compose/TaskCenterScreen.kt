package com.xcyh.reachmax.main.task.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xcyh.reachmax.main.task.TabState
import com.xcyh.reachmax.main.task.TaskCenterUiState
import com.xcyh.reachmax.model.bean.task.TaskBean
import com.xcyh.reachmax.model.constant.TaskStatus

// 颜色对齐旧资源：tab 文本 #666666/#333333，indicator #222222，分割线 #F0F0F0
private val TabSelectedColor = Color(0xFF333333)
private val TabUnselectedColor = Color(0xFF666666)
private val IndicatorColor = Color(0xFF222222)
private val DividerColor = Color(0xFFF0F0F0)

/**
 * 任务中心顶层 Composable：标题栏 + 自定义 Tab 行 + ViewPager。
 *
 * Tab 与 Pager 双向同步，复刻旧 MagicIndicator + ViewPager2 行为。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCenterScreen(
    state: TaskCenterUiState,
    onTabSelected: (Int) -> Unit,
    onRefresh: (Int) -> Unit,
    onLoadMore: (Int) -> Unit,
    onCancelClick: (TaskBean) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(initialPage = state.selectedTabIndex) { state.tabTitles.size }

    // Pager 滑动 -> 选中 Tab
    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage != state.selectedTabIndex) {
            onTabSelected(pagerState.currentPage)
        }
    }
    // 外部选中 Tab -> Pager 滚动
    LaunchedEffect(state.selectedTabIndex) {
        if (pagerState.currentPage != state.selectedTabIndex) {
            pagerState.animateScrollToPage(state.selectedTabIndex)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "任务中心",
                        color = TabSelectedColor,
                        fontSize = 16.sp,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                            contentDescription = "返回",
                            tint = TabSelectedColor,
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White,
                ),
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            TaskTabBar(
                titles = state.tabTitles,
                selectedIndex = state.selectedTabIndex,
                onSelect = onTabSelected,
            )
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
            ) { page ->
                val status = state.tabStatuses[page]
                val tabState = state.tabs[status] ?: TabState()
                TaskListContent(
                    tabState = tabState,
                    onRefresh = { onRefresh(status) },
                    onLoadMore = { onLoadMore(status) },
                    onCancelClick = onCancelClick,
                )
            }
        }
    }
}

/**
 * 自定义居中 Tab 行，复刻 MagicIndicator 视觉：
 * 平分宽度、文本居中、选中加粗黑色；底部 20dp 宽 2dp 高圆角 #222222 指示条跟随滑动。
 */
@Composable
private fun TaskTabBar(
    titles: List<String>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom,
        ) {
            titles.forEachIndexed { index, title ->
                TabTitle(
                    text = title,
                    selected = index == selectedIndex,
                    onClick = { onSelect(index) },
                    modifier = Modifier.weight(1f),
                )
            }
        }
        TabIndicator(
            selectedIndex = selectedIndex,
            tabCount = titles.size,
            modifier = Modifier.fillMaxWidth(),
        )
        // Tab 行下方 0.33dp 分割线（对齐原 layout 的 tab_divider）
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(0.33.dp)
                .background(DividerColor),
        )
    }
}

@Composable
private fun TabTitle(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val color = if (selected) TabSelectedColor else TabUnselectedColor
    Text(
        text = text,
        color = color,
        fontSize = 15.sp,
        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
        textAlign = TextAlign.Center,
        maxLines = 1,
        modifier = modifier
            .height(40.dp)
            .wrapContentSize(Alignment.Center)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick,
            ),
    )
}

/**
 * 指示条：占满整行，内部用 fraction 定位；宽度 20dp、高 2dp、圆角 1dp。
 * 通过 [layout] 测量后用 IntOffset 偏移实现"跟随当前选中页"。
 */
@Composable
private fun TabIndicator(
    selectedIndex: Int,
    tabCount: Int,
    modifier: Modifier = Modifier,
) {
    if (tabCount <= 0) return
    BoxWithTabOffset(
        tabIndex = selectedIndex,
        tabCount = tabCount,
        modifier = modifier.height(2.dp),
    ) {
        Box(
            Modifier
                .size(width = 20.dp, height = 2.dp)
                .clip(RoundedCornerShape(1.dp))
                .background(IndicatorColor),
        )
    }
}

/**
 * 让子元素按当前 [tabIndex] 在 [tabCount] 等分的行上居中定位。
 */
@Composable
private fun BoxWithTabOffset(
    tabIndex: Int,
    tabCount: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    val indicatorWidthPx = with(density) { 20.dp.toPx() }
    Box(
        modifier = modifier
            .layout { measurable, constraints ->
                val placeable = measurable.measure(constraints)
                val rowWidth = constraints.maxWidth
                val slotWidth = if (tabCount > 0) rowWidth.toFloat() / tabCount else 0f
                val slotCenter = slotWidth * (tabIndex + 0.5f)
                val x = (slotCenter - indicatorWidthPx / 2f).toInt()
                layout(placeable.width, placeable.height) {
                    placeable.placeRelative(x = x, y = 0)
                }
            },
    ) {
        content()
    }
}

@Preview(showBackground = true, name = "TaskCenter Preview")
@Composable
private fun TaskCenterScreenPreview() {
    TaskCenterScreen(
        state = TaskCenterUiState(),
        onTabSelected = {},
        onRefresh = {},
        onLoadMore = {},
        onCancelClick = {},
        onBack = {},
    )
}
