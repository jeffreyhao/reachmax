package com.xcyh.reachmax.adv.detail.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xcyh.reachmax.R
import com.xcyh.reachmax.adv.detail.AdvDetail
import com.xcyh.reachmax.model.constant.AdvItemLevel

// --------------------------------------------------------------------
// 【Compose 基础：颜色定义】
// 在 Compose 中，颜色直接使用 Color(0xFFxxxxxx) 的十六进制方式定义。
// --------------------------------------------------------------------
private val TextDark = Color(0xFF333333)
private val TextGray = Color(0xFF666666)
private val DividerColor = Color(0xFFF0F0F0)

/**
 * 广告详情页顶层 Composable。
 * * 💡【Compose 核心：单向数据流与状态提升】
 * - `detail`: 纯数据源，UI 只负责读取并渲染它。
 * - `onBack` / `onCopy`: Lambda 表达式（回调函数）。当 UI 发生交互（如点击）时，
 * 不直接在 UI 内部处理业务（如弹 Toast、返回上页），而是通过回调通知上层（Activity/ViewModel）处理。
 */
@Preview
@OptIn(ExperimentalMaterial3Api::class) // 某些 Material 3 组件处于实验阶段，需要加此注解
@Composable // 必须加此注解，告知编译器这是一个 Compose 组件（类似 XML 布局文件）
fun AdvDetailScreen(
    detail: AdvDetail,
    onBack: () -> Unit,
    onCopy: (String) -> Unit,
    modifier: Modifier = Modifier, // 规范：几乎所有自定义 Composable 都应暴露一个 Modifier，方便外部修饰
) {
    // 💡【Scaffold：脚手架组件】
    // 它是 Material Design 的标准页面结构骨架，内置了 topBar（顶栏）、bottomBar（底栏）、floatingActionButton 等槽位（Slots）。
    Scaffold(
        modifier = modifier.fillMaxSize(), // 让脚手架占满整个屏幕大小
        containerColor = Color.White,      // 设置整个页面的背景色为白色
        topBar = {
            // 💡【CenterAlignedTopAppBar：居中对齐的标题顶栏】
            CenterAlignedTopAppBar(
                title = {
                    // 💡【Text：文本组件（相当于 TextView）】
                    Text(
                        text = detail.title,
                        color = TextDark,
                        fontSize = 16.sp, // sp 用于字号
                        maxLines = 1,     // 限制单行
                        overflow = TextOverflow.Ellipsis, // 超出显示省略号 "..."
                    )
                },
                navigationIcon = {
                    // 💡【IconButton：可点击的图标按钮（自带点击水波纹效果）】
                    IconButton(onClick = onBack) { // 点击时执行传入的 onBack 回调
                        // 💡【Icon：图标组件（相当于 ImageView）】
                        Icon(
                            painter = painterResource(R.drawable.ic_back_common), // 加载 drawable 资源
                            contentDescription = "返回", // 无障碍辅助说明
                            tint = Color.Unspecified,  // 保持图标原有颜色，不进行统一着色
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White, // 顶栏背景设为白色
                ),
                // 💡【Modifier 链式调用：绘制顶栏底部的超薄分割线】
                // drawWithContent 允许我们在组件内容绘制完成后，继续在上面“画”东西（类似 Canvas 绘制）
                modifier = Modifier.drawWithContent {
                    drawContent() // 1. 先绘制顶栏原本的内容（标题、返回按钮）
                    // 2. 在底部绘制一条超细的分割线（避免用额外的 View 占用布局高度）
                    drawRect(
                        color = Color.Black.copy(alpha = 0.047f), // 约 4.7% 透明度的黑色
                        topLeft = Offset(x = 0f, y = size.height - 0.3.dp.toPx()), // 起点在左下角
                        size = Size(width = size.width, height = 0.3.dp.toPx()),    // 高度为 0.3dp
                    )
                },
            )
        },
    ) { padding -> // Scaffold 会自动计算顶栏的高度，并通过 padding 传给我们，防止内容被顶栏遮挡

        // 💡【Column：垂直线性布局（相当于 Android 中的 LinearLayout 且 orientation="vertical"）】
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // 💡【滚动状态】：使 Column 具备垂直滚动能力（相当于 ScrollView）
                .padding(padding), // 应用 Scaffold 算出的 padding，避开顶栏
        ) {
            // 顶部 0.33dp 分割线
            Divider(thickness = 0.33.dp)

            // 名称区（所有层级公共）
            NameSection(detail = detail, onCopy = onCopy)

            // 中间粗分割线
            Divider(thickness = 0.5.dp)

            // 💡【声明式 UI 的魅力：条件渲染】
            // 在 Compose 中，你不需要写 view.setVisibility(View.GONE)。
            // 只需要写标准的 Kotlin standard if 语句，条件满足就渲染，不满足就自动从 UI 树中销毁。

            // 账户维度：所属用户
            if (detail.advLevel == AdvItemLevel.ADV_ACCOUNT) {
                UserBlock(userName = detail.launchName)
            }
            // 非账户维度：所属账户（账户块状态恒为"正常"）
            if (detail.advLevel != AdvItemLevel.ADV_ACCOUNT) {
                DetailBlock(
                    label = "所属账户",
                    name = detail.adAccountName,
                    id = detail.adAccount,
                    stateText = "normal",
                    onCopy = onCopy,
                )
            }
            // 组/计划维度：所属系列
            if (detail.advLevel == AdvItemLevel.ADV_GROUP ||
                detail.advLevel == AdvItemLevel.ADV_PLAN
            ) {
                DetailBlock(
                    label = "所属系列",
                    name = detail.campaignName,
                    id = detail.campaignId,
                    stateText = detail.campaignStatus,
                    onCopy = onCopy,
                )
            }
            // 计划维度：所属组
            if (detail.advLevel == AdvItemLevel.ADV_PLAN) {
                DetailBlock(
                    label = "所属组",
                    name = detail.adsetName,
                    id = detail.adsetId,
                    stateText = detail.adsetStatus,
                    onCopy = onCopy,
                )
            }
            // 💡【Spacer：空白占位组件】
            // 用来在底部留出 30dp 的空隙，防止内容贴底，体验更好
            Spacer(Modifier.height(30.dp))
        }
    }
}

/** * 自定义全宽分割线。
 * * 💡【Box：堆叠布局（相当于 FrameLayout）】
 * 在这里被当做一个纯粹的高宽色块来使用。
 */
@Composable
private fun Divider(thickness: androidx.compose.ui.unit.Dp) {
    Box(
        Modifier
            .fillMaxWidth()        // 占满宽度
            .height(thickness)     // 高度由外部传入
            .background(DividerColor) // 背景填充灰色
    )
}

/**
 * 头部名称区：Meta 图标 + 名称（加粗）+ ID + 复制按钮 + 状态徽章。
 *
 * 💡【Row：水平线性布局（相当于 LinearLayout 且 orientation="horizontal"）】
 */
@Composable
private fun NameSection(detail: AdvDetail, onCopy: (String) -> Unit) {
    // 💡【数据准备阶段】
    // 使用普通的 Kotlin when 表达式，根据广告层级，计算出当前应该展示的文字和状态
    val showName: String
    val showId: String
    val showState: String
    when (detail.advLevel) {
        AdvItemLevel.ADV_ACCOUNT -> {
            showName = detail.adAccountName
            showId = detail.adAccount
            showState = "normal"
        }
        AdvItemLevel.ADV_SERIAL -> {
            showName = detail.campaignName
            showId = detail.campaignId
            showState = detail.campaignStatus
        }
        AdvItemLevel.ADV_GROUP -> {
            showName = detail.adsetName
            showId = detail.adsetId
            showState = detail.adsetStatus
        }
        AdvItemLevel.ADV_PLAN -> {
            showName = detail.adName
            showId = detail.adId
            showState = detail.adStatus
        }
        else -> {
            showName = ""
            showId = ""
            showState = ""
        }
    }

    // 开始渲染水平行
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 90.dp) // 保证这一栏最小高度有 90dp
            .padding(vertical = 10.dp, horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically, // 子组件在垂直方向上居中对齐
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_meta),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(28.dp),
        )
        // 间距
        Spacer(Modifier.width(18.dp))

        // 右侧的信息列
        Column(
            // 💡【weight(1f)：权重分配】
            // 类似于 LinearLayout 的 layout_weight="1"，让这一个 Column 自动占满剩下的所有宽度
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = showName,
                color = TextDark,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold, // 文字加粗
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(2.dp))

            // ID和复制按钮所在的行
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = showId,
                    color = TextGray,
                    fontSize = 13.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    // 💡【weight(1f, fill = false)：自适应权重】
                    // 意思是：如果 ID 很短，就按实际宽度显示；如果 ID 极长，最多只能占满剩余宽度的 100%，不会把后面的复制按钮挤出屏幕。
                    modifier = Modifier.weight(1f, fill = false),
                )
                if (showId.isNotEmpty()) {
                    IconButton(
                        onClick = { onCopy(showId) }, // 点击时回调，把 ID 传给外部
                        modifier = Modifier.padding(start = 2.dp),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_copy),
                            contentDescription = "复制",
                            tint = Color.Unspecified,
                        )
                    }
                }
            }

            // 状态徽章：如果不为 null，就渲染出来
            val badge = StateBadge.from(showState)
            if (badge != null) {
                Spacer(Modifier.height(4.dp))
                StateBadge(badge) // 调用自定义的 StateBadge 组件
            }
        }
    }
}

/** * 「所属用户」块：仅账户维度显示。
 * 结构：一行，左边标签，右边名字。
 */
@Composable
private fun UserBlock(userName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 15.dp, end = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "所属用户",
            color = TextDark,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.width(14.dp))
        Text(
            text = userName,
            color = Color.Black,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f), // 占满右侧剩余空间
        )
    }
}

/**
 * 通用「所属账户/系列/组」块。
 * 结构：垂直排列，第一行是标签+名称，第二行是ID+复制按钮，第三行是状态徽章。
 */
@Composable
private fun DetailBlock(
    label: String,
    name: String,
    id: String,
    stateText: String,
    onCopy: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 15.dp, end = 15.dp),
    ) {

        // 第一行：标签 + 名称（名称可换行，完整展示，不打点）
        Row(verticalAlignment = Alignment.Top) {
            Text(
                text = label,
                color = TextDark,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(56.dp)
            )
            Spacer(Modifier.width(14.dp))
            Text(
                text = name,
                color = Color.Black,
                fontSize = 14.sp,
                modifier = Modifier.weight(1f),
            )
        }
        // 第二行：ID + 复制按钮
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.width(70.dp))
            Text(
                text = id,
                color = TextGray,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f, fill = false),
            )
            if (id.isNotEmpty()) {
                IconButton(
                    onClick = { onCopy(id) },
                    modifier = Modifier.padding(start = 1.dp),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_copy),
                        contentDescription = "复制",
                        tint = Color.Unspecified,
                    )
                }
            }
        }
        // 第三行：状态徽章（用 Row 缩进 70dp，与上方名称/ID 左对齐）
        val badge = StateBadge.from(stateText)
        if (badge != null) {
            Spacer(Modifier.height(4.dp))
            Row {
                Spacer(Modifier.width(70.dp))
                StateBadge(badge)
            }
        }
    }
}

// --------------------------------------------------------------------
// 【数据建模：状态徽章对应的数据类】
// --------------------------------------------------------------------
private data class StateBadgeData(val text: String, val color: Color)

/**
 * 状态映射工具单例。
 * 输入后端的各种状态字符串（如 "ACTIVE"），返回对应的显示文本与颜色配对。
 */
private object StateBadge {
    fun from(originState: String): StateBadgeData? = when (originState) {
        "ACTIVE" -> StateBadgeData("投放中", Color(0xFF16BD4F)) // 绿色
        "PAUSED" -> StateBadgeData("暂停", Color(0xFFF9983A)) // 橙色
        "CLOSE" -> StateBadgeData("删除", Color(0xFFFE5D5D))  // 红色
        "normal" -> StateBadgeData("正常", Color(0xFF16BD4F)) // 绿色
        else -> null // 其他不支持的状态不予显示（返回 null）
    }
}

/**
 * 具体的状态徽章 UI 组件。
 */
@Composable
private fun StateBadge(data: StateBadgeData) {
    // 💡【Surface：元器件容器】
    // 类似于卡片布局（CardView），通常自带裁剪圆角、设置背景色和阴影的能力。
    Surface(
        color = data.color, // 动态设置背景色
        shape = RoundedCornerShape(12.dp), // 四角裁剪成 12dp 的大圆角
        modifier = Modifier.height(16.dp),  // 限制徽章整体高度
    ) {
        // Box 容器用来做内部文本的居中对齐
        Box(
            modifier = Modifier.padding(horizontal = 6.dp), // 左右各空出 6dp 间距
            contentAlignment = Alignment.Center, // 里面的文字绝对居中
        ) {
            Text(
                text = data.text,
                color = Color.White, // 白色文字
                fontSize = 10.sp,    // 极小字号
            )
        }
    }
}

// --------------------------------------------------------------------
// 【Compose 特色：预览机制】
// 不需要跑模拟器，在 IDE 的 Split 或 Design 视图中直接双击即可查看 UI 效果。
// --------------------------------------------------------------------
@Preview(showBackground = true, name = "AdvDetail Group Preview")
@Composable
private fun AdvDetailScreenPreview() {
    AdvDetailScreen(
        detail = AdvDetail(
            advLevel = AdvItemLevel.ADV_GROUP, // 模拟“广告组”层级数据
            launchName = "shenying",
            adAccountName = "飞书-ZT-web测试",
            adAccount = "4212884302307877",
            campaignName = "系列A",
            campaignId = "1202090000",
            campaignStatus = "ACTIVE",
            adsetName = "组1",
            adsetId = "1202090001",
            adsetStatus = "PAUSED",
            adName = "",
            adId = "",
            adStatus = "",
        ),
        onBack = {}, // 预览中传空实现即可
        onCopy = {},
    )
}