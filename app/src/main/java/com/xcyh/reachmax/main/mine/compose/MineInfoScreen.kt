package com.xcyh.reachmax.main.mine.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xcyh.reachmax.main.mine.MineInfoProfile

private val LabelColor = Color(0xFF333333)
private val ValueColor = Color(0xFF999999)
private val DividerColor = Color(0xFFE0E0E0)

/**
 * 我的页顶层 Composable，视觉对齐 Flutter 版 MineInfoPage。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MineInfoScreen(
    profile: MineInfoProfile,
    onBack: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "我的",
                        color = LabelColor,
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                            contentDescription = "返回",
                            tint = LabelColor,
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
            // 顶部 0.33dp 分割线
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(0.33.dp)
                    .background(DividerColor)
            )
            InfoRow(label = "用户名", value = profile.userName.ifEmpty { "—" })
            InfoRow(label = "状态", value = profile.stateText)
            InfoRow(label = "最后登录时间", value = profile.loginTimeText)
            InfoRow(label = "版本号", value = profile.versionText)
            if (profile.debug) {
                InfoRow(label = "Host", value = profile.baseUrl)
            }

            Spacer(Modifier.weight(1f))

            // 退出按钮
            LogoutButton(
                onClick = onLogoutClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, bottom = 90.dp),
            )
        }
    }
}

/** 单行信息：label 左、value 右。 */
@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(start = 30.dp, end = 30.dp, bottom = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(label, color = LabelColor, fontSize = 15.sp)
        Spacer(Modifier.width(8.dp))
        Text(
            text = value,
            color = ValueColor,
            fontSize = 15.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )
    }
}

/**
 * 退出按钮：默认 #FE5D5D，按下 #EE4D4D（复刻 bg_mine_logout selector）。
 */
@Composable
private fun LogoutButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val bg = if (pressed) Color(0xFFEE4D4D) else Color(0xFFFE5D5D)

    Surface(
        shape = RoundedCornerShape(5.dp),
        color = bg,
        interactionSource = interaction,
        onClick = onClick,
        modifier = modifier.height(40.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
        ) {
            Text(
                text = "退出",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
            )
        }
    }
}

@Preview(showBackground = true, name = "MineInfo Preview")
@Composable
private fun MineInfoScreenPreview() {
    MineInfoScreen(
        profile = MineInfoProfile(
            userName = "张三",
            dashboard = 0,
            loginTime = System.currentTimeMillis(),
            versionName = "1.1.0",
            versionCode = 11,
            debug = true,
            baseUrl = "https://launch.novelsbd.com/",
        ),
        onBack = {},
        onLogoutClick = {},
    )
}
