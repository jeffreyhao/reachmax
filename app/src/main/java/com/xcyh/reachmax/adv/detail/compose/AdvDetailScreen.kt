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

private val TextDark = Color(0xFF333333)
private val TextGray = Color(0xFF666666)
private val DividerColor = Color(0xFFF0F0F0)

/**
 * 广告详情页顶层 Composable，视觉对齐 [R.layout.activity_adv_detail]。
 * 图标复用真实 drawable（ic_back_common / ic_meta / ic_copy），
 * 文案/字号/间距/状态徽章颜色均按原 XML + AdvDetailActivity.showState() 复刻。
 *
 * @param onCopy 复制回调，参数为待复制的 ID 文本。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvDetailScreen(
    detail: AdvDetail,
    onBack: () -> Unit,
    onCopy: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = detail.title,
                        color = TextDark,
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back_common),
                            contentDescription = "返回",
                            tint = Color.Unspecified,
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White,
                ),
                // 标题栏底部分割线，对齐 TitleBar.lineView (#0C000000 / 0.3dp)
                modifier = Modifier.drawWithContent {
                    drawContent()
                    drawRect(
                        color = Color.Black.copy(alpha = 0.047f),
                        topLeft = Offset(x = 0f, y = size.height - 0.3.dp.toPx()),
                        size = Size(width = size.width, height = 0.3.dp.toPx()),
                    )
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding),
        ) {
            // 顶部 0.33dp 分割线
            Divider(thickness = 0.33.dp)
            // 名称区（所有 level 公共）
            NameSection(detail = detail, onCopy = onCopy)
            Divider(thickness = 0.5.dp)

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
            // 底部留白
            Spacer(Modifier.height(30.dp))
        }
    }
}

/** 全宽分割线。 */
@Composable
private fun Divider(thickness: androidx.compose.ui.unit.Dp) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(thickness)
            .background(DividerColor)
    )
}

/**
 * 名称区：Meta 图标 + 名称（加粗）+ ID + 复制 + 状态徽章。
 * 名称/ID/状态随当前维度变化。
 */
@Composable
private fun NameSection(detail: AdvDetail, onCopy: (String) -> Unit) {
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 90.dp)
            .padding(vertical = 10.dp, horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_meta),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(28.dp),
        )
        Spacer(Modifier.width(18.dp))
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = showName,
                color = TextDark,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(2.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = showId,
                    color = TextGray,
                    fontSize = 13.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill = false),
                )
                if (showId.isNotEmpty()) {
                    IconButton(
                        onClick = { onCopy(showId) },
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
            val badge = StateBadge.from(showState)
            if (badge != null) {
                Spacer(Modifier.height(4.dp))
                StateBadge(badge)
            }
        }
    }
}

/** 「所属用户」块：仅账户维度显示。 */
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
            modifier = Modifier.weight(1f),
        )
    }
}

/**
 * 「所属账户/系列/组」块：标签 + 名称 + ID（可复制）+ 状态徽章。
 * 用作账户块时 stateText 传 "normal"（静态，恒为绿色正常）。
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
        // 名称行：标签 + 名称
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = label,
                color = TextDark,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.width(14.dp))
            Text(
                text = name,
                color = Color.Black,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
            )
        }
        // ID 行
        Row(verticalAlignment = Alignment.CenterVertically) {
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
        val badge = StateBadge.from(stateText)
        if (badge != null) {
            Spacer(Modifier.height(4.dp))
            StateBadge(badge)
        }
    }
}

/**
 * 状态徽章，复刻原 showState() 逻辑：
 * ACTIVE→投放中(#16BD4F)，PAUSED→暂停(#F9983A)，CLOSE→删除(#FE5D5D)，
 * "normal"→正常(#16BD4F)，其它→不渲染。
 */
private data class StateBadgeData(val text: String, val color: Color)

private object StateBadge {
    fun from(originState: String): StateBadgeData? = when (originState) {
        "ACTIVE" -> StateBadgeData("投放中", Color(0xFF16BD4F))
        "PAUSED" -> StateBadgeData("暂停", Color(0xFFF9983A))
        "CLOSE" -> StateBadgeData("删除", Color(0xFFFE5D5D))
        "normal" -> StateBadgeData("正常", Color(0xFF16BD4F))
        else -> null
    }
}

@Composable
private fun StateBadge(data: StateBadgeData) {
    Surface(
        color = data.color,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.height(16.dp),
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 6.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = data.text,
                color = Color.White,
                fontSize = 10.sp,
            )
        }
    }
}

@Preview(showBackground = true, name = "AdvDetail Group Preview")
@Composable
private fun AdvDetailScreenPreview() {
    AdvDetailScreen(
        detail = AdvDetail(
            advLevel = AdvItemLevel.ADV_GROUP,
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
        onBack = {},
        onCopy = {},
    )
}
