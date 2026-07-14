package com.xcyh.reachmax.main.login.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.base.global.GlobalBuildConfig
import com.xcyh.reachmax.R
import com.xcyh.reachmax.main.login.Env
import com.xcyh.reachmax.main.login.LoginUiState

/**
 * 登录页顶层 Composable。视觉对应 activity_login.xml。
 */
@Composable
fun LoginScreen(
    state: LoginUiState,
    onUserNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTogglePassword: () -> Unit,
    onLogin: () -> Unit,
    onSelectEnv: (Env) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.bg_login),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
        ) {
            Text(
                text = "您好!",
                color = Color.Black,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 100.dp),
            )
            Text(
                text = "欢迎来到投放系统",
                color = Color.Black,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 60.dp),
            )

            Spacer(Modifier.height(30.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color.White,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                ) {
                    LoginTextField(
                        value = state.userName,
                        onValueChange = onUserNameChange,
                        hint = "请输入用户名",
                        modifier = Modifier.padding(top = 30.dp),
                    )
                    LoginTextField(
                        value = state.password,
                        onValueChange = onPasswordChange,
                        hint = "请输入密码",
                        passwordVisible = state.passwordVisible,
                        onTogglePassword = onTogglePassword,
                        modifier = Modifier.padding(top = 30.dp),
                    )
                    LoginButton(
                        text = "登录",
                        onClick = onLogin,
                        enabled = !state.isLoggingIn,
                        modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 40.dp, bottom = 30.dp),
                    )
                }
            }

            if (GlobalBuildConfig.DEBUG) {
                EnvSwitcher(
                    selection = state.envSelection,
                    onSelect = onSelectEnv,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp),
                )
            }
        }
    }
}

/** DEBUG 环境切换，对应原 RadioGroup（线上/开发）。 */
@Composable
fun EnvSwitcher(
    selection: Env,
    onSelect: (Env) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        EnvOption("线上环境", selected = selection == Env.ONLINE) { onSelect(Env.ONLINE) }
        Spacer(Modifier.width(40.dp))
        EnvOption("开发环境", selected = selection == Env.DEV) { onSelect(Env.DEV) }
    }
}

@Composable
private fun EnvOption(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(22.dp),
        color = if (selected) Color(0x33000000) else Color.Transparent,
        modifier = Modifier
            .width(120.dp)
            .height(50.dp)
            .clickable(onClick = onClick),
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(label, color = Color.Black, fontSize = 14.sp)
        }
    }
}

@Preview(showBackground = true, name = "Login Preview")
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        state = LoginUiState(),
        onUserNameChange = {},
        onPasswordChange = {},
        onTogglePassword = {},
        onLogin = {},
        onSelectEnv = {},
    )
}
