package com.xcyh.reachmax.main.mine

import android.content.Intent
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
import com.baidu.baselibrary.util.App
import com.baidu.baselibrary.util.ui.AnimationUtil
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.tencent.common.util.ToastUtils
import com.xcyh.reachmax.R
import com.xcyh.reachmax.main.login.LoginActivity
import com.xcyh.reachmax.main.mine.compose.MineInfoScreen
import com.xcyh.reachmax.model.manager.Pitcher

/**
 * 我的（信息页）— Compose 实现。
 *
 * 原类继承 FlutterActivity，通过 MethodChannel 与 Flutter 通信；现改为 ComponentActivity，
 * 用 Compose 渲染、ViewModel 管理状态。登出逻辑与原 Flutter 宿主 doLogout() 一致。
 */
class MineInfoActivity : ComponentActivity() {

    private val viewModel: MineInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupImmersive()
        setContent {
            val profile by viewModel.profile.collectAsState()
            var showLogoutDialog by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                viewModel.events.collect { event ->
                    when (event) {
                        is MineInfoEvent.ShowLogoutConfirm -> showLogoutDialog = true
                        is MineInfoEvent.LogoutDone -> gotoLogin()
                    }
                }
            }

            MineInfoScreen(
                profile = profile,
                onBack = { finish() },
                onLogoutClick = viewModel::requestLogout,
            )

            if (showLogoutDialog) {
                AlertDialog(
                    onDismissRequest = { showLogoutDialog = false },
                    title = { Text("提示") },
                    text = { Text("确定要退出当前账号吗?") },
                    confirmButton = {
                        TextButton(onClick = {
                            showLogoutDialog = false
                            viewModel.logout()
                        }) { Text("确定") }
                    },
                    dismissButton = {
                        TextButton(onClick = { showLogoutDialog = false }) {
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

    /** 登出：清数据 + 跳登录 + Toast。逻辑对齐原 Flutter 宿主 doLogout()。 */
    private fun gotoLogin() {
        // Pitcher.clear() 已在 ViewModel.logout() 中执行
        startActivity(Intent(this, LoginActivity::class.java))
        AnimationUtil.overridePendingTransition(this, R.anim.dialog_fade_in, R.anim.anim_none)
        App.postDelayed({ finish() }, 500)
        ToastUtils.showCenterShort("已退出账号")
    }

    override fun finish() {
        super.finish()
        AnimationUtil.overridePendingTransition(this, R.anim.anim_none, R.anim.push_right_out)
    }

    companion object {
        @Suppress("unused")
        private const val TAG = "MineInfoActivity"
    }
}
