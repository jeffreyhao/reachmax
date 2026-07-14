package com.xcyh.reachmax.main.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.baidu.baselibrary.util.App
import com.baidu.baselibrary.util.ui.AnimationUtil
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.tencent.common.util.ToastUtils
import com.xcyh.reachmax.R
import com.xcyh.reachmax.main.MainTabActivity
import com.xcyh.reachmax.main.login.compose.LoginScreen

/**
 * 登录页（Compose 实现）。
 *
 * 原类继承 BaseActivity<DataBinding, LoginPresenter>，现改为 ComponentActivity，
 * 用 Compose 渲染、ViewModel 管理状态、BaseRequest 复用网络层。
 * 类名/包路径保持不变，外部引用（MainConfig/Pitcher/LaunchActivity/MineInfoActivity）无需改动。
 */
class LoginActivity : ComponentActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupImmersive()
        setContent {
            val state by viewModel.uiState.collectAsState()

            LaunchedEffect(Unit) {
                viewModel.events.collect { event ->
                    when (event) {
                        is LoginEvent.Toast -> ToastUtils.showCenterShort(event.message)
                        is LoginEvent.LoginFail -> ToastUtils.showCenterShort(event.message)
                        is LoginEvent.LoginSuccess -> gotoMain()
                    }
                }
            }

            LoginScreen(
                state = state,
                onUserNameChange = viewModel::onUserNameChange,
                onPasswordChange = viewModel::onPasswordChange,
                onTogglePassword = viewModel::togglePassword,
                onLogin = viewModel::login,
                onSelectEnv = viewModel::selectEnv,
            )
        }
    }

    private fun setupImmersive() {
        ImmersionBar.with(this)
            .reset()
            .transparentBar()
            .fullScreen(true)
            .hideBar(BarHide.FLAG_HIDE_BAR)
            .init()
    }

    /** 登录成功跳转，与原 gotoMain() 一致。 */
    private fun gotoMain() {
        startActivity(Intent(this, MainTabActivity::class.java))
        AnimationUtil.overridePendingTransition(this, R.anim.dialog_fade_in, R.anim.anim_none)
        App.postDelayed({ finish() }, 500)
    }
}
