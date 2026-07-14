package com.xcyh.reachmax.main.splash

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.baidu.AppConfig
import com.base.global.PreferencesUtil
import com.gyf.immersionbar.ImmersionBar
import com.xcyh.reachmax.main.MainTabActivity
import com.xcyh.reachmax.main.login.LoginActivity
import com.xcyh.reachmax.main.splash.compose.SplashScreen
import com.xcyh.reachmax.model.constant.SpKey
import com.xcyh.reachmax.model.manager.Pitcher
import com.xcyh.reachmax.model.request.Url

/**
 * 开屏页（Compose 实现）。
 *
 * 原类继承 PermissionActivity → BaseActivity，现改为 ComponentActivity，
 * 用 Compose 渲染（视觉仍由 AppTheme.SplashActivity 的 windowBackground 提供），
 * 类名/包路径保持不变，外部引用（MainConfig/AndroidManifest）无需改动。
 */
class LaunchActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppConfig.appStatus = 1

        if (TextUtils.isEmpty(Pitcher.getInstance().token)) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setupImmersive()
        setContent {
            SplashScreen()
        }

        gotoMain()
    }


    /**
     * 屏蔽物理返回按钮
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            true
        } else {
            super.onKeyDown(keyCode, event)
        }
    }

    /**
     * 跳转到 MainActivity
     */
    private fun gotoMain() {
        Url.BASE_URL = PreferencesUtil.get(SpKey.BASE_URL, Url.BASE_URL)
        Url.reset()

        startActivity(Intent(this, MainTabActivity::class.java))
        finish()
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
}
