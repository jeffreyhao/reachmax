package com.xcyh.reachmax.main.mine

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * 我的页展示数据，字段与原 Flutter 版 Profile 一致。
 *
 * @param dashboard 0 = 正常, 非 0 = 异常
 * @param loginTime 毫秒时间戳
 */
data class MineInfoProfile(
    val userName: String,
    val dashboard: Int,
    val loginTime: Long,
    val versionName: String,
    val versionCode: Int,
    val debug: Boolean,
    val baseUrl: String,
) {
    val stateText: String
        get() = if (dashboard == 0) "正常" else "异常"

    val loginTimeText: String
        get() = if (loginTime == 0L) {
            "—"
        } else {
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                .format(Date(loginTime))
        }

    val versionText: String
        get() = "V$versionName ($versionCode)"

    companion object {
        val EMPTY = MineInfoProfile(
            userName = "",
            dashboard = 0,
            loginTime = 0L,
            versionName = "",
            versionCode = 0,
            debug = false,
            baseUrl = "",
        )
    }
}

/** 我的页一次性事件。 */
sealed interface MineInfoEvent {
    /** 用户点了退出按钮，需 Activity 弹确认框。 */
    object ShowLogoutConfirm : MineInfoEvent
    /** 登出逻辑已执行完毕（清数据），需 Activity 跳登录页 + Toast。 */
    object LogoutDone : MineInfoEvent
}
