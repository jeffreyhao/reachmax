package com.xcyh.reachmax.main.splash.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

/**
 * 开屏页 Composable。
 *
 * 开屏视觉由 AppTheme.SplashActivity 的 windowBackground（@drawable/bg_launch）提供，
 * 这里只占满屏幕作为 Compose 根容器，避免 XML 布局。
 */
@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize())
}

@Preview(showBackground = true, name = "Splash Preview")
@Composable
private fun SplashScreenPreview() {
    SplashScreen()
}
