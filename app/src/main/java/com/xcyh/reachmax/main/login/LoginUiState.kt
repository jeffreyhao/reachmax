package com.xcyh.reachmax.main.login

/**
 * 登录页 UI 状态（驱动整个 Compose 界面）。
 */
data class LoginUiState(
    val userName: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val isLoggingIn: Boolean = false,
    val envSelection: Env = Env.ONLINE,
)

/** 环境选择，仅 DEBUG 包在 UI 中展示。 */
enum class Env { ONLINE, DEV }

/**
 * 一次性事件：Toast / 登录成功跳转 / 登录失败提示。
 * 用 SharedFlow(replay=0) 派发，避免塞进 StateFlow 导致重组后重复触发。
 */
sealed interface LoginEvent {
    data class Toast(val message: String) : LoginEvent
    data object LoginSuccess : LoginEvent
    data class LoginFail(val message: String) : LoginEvent
}
