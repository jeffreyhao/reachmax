package com.xcyh.reachmax.main.mine

import androidx.lifecycle.ViewModel
import com.base.global.GlobalBuildConfig
import com.base.global.PreferencesUtil
import com.base.util.AppUtil
import com.xcyh.reachmax.model.constant.SpKey
import com.xcyh.reachmax.model.manager.Pitcher
import com.xcyh.reachmax.model.request.Url
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * 我的页 ViewModel：展示数据全部本地读取（Pitcher / PreferencesUtil），
 * 无需协程；登出逻辑复刻自原 Flutter 宿主 doLogout()。
 */
class MineInfoViewModel : ViewModel() {

    private val _profile = MutableStateFlow(buildProfile())
    val profile: StateFlow<MineInfoProfile> = _profile.asStateFlow()

    private val _events = MutableSharedFlow<MineInfoEvent>(
        replay = 0,
        extraBufferCapacity = 4,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val events: SharedFlow<MineInfoEvent> = _events.asSharedFlow()

    /** 用户点击退出按钮，请求 Activity 弹确认框。 */
    fun requestLogout() {
        emit(MineInfoEvent.ShowLogoutConfirm)
    }

    /** 确认登出：清数据，通知 Activity 跳登录页。 */
    fun logout() {
        Pitcher.getInstance().clear()
        emit(MineInfoEvent.LogoutDone)
    }

    /** 组装展示数据，字段对齐 Flutter 侧 Profile.fromMap。 */
    private fun buildProfile(): MineInfoProfile {
        val pitcher = Pitcher.getInstance()
        return MineInfoProfile(
            userName = pitcher.userName.orEmpty(),
            dashboard = pitcher.dashboard,
            loginTime = PreferencesUtil.get(SpKey.TIME_LOGIN, 0L),
            versionName = AppUtil.getVersionName(),
            versionCode = AppUtil.getVersionCode(),
            debug = GlobalBuildConfig.DEBUG,
            baseUrl = Url.BASE_URL,
        )
    }

    private fun emit(event: MineInfoEvent) {
        if (!_events.tryEmit(event)) {
            android.util.Log.w("MineInfoViewModel", "event dropped: $event")
        }
    }
}
