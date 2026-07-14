package com.xcyh.reachmax.main.login

import androidx.lifecycle.ViewModel
import com.base.global.PreferencesUtil
import com.base.net.bean.ApiException
import com.base.net.callback.ResponseListener
import com.base.net.request.BaseRequest
import com.baidu.baselibrary.util.UserManager
import com.xcyh.reachmax.model.bean.LoginBean
import com.xcyh.reachmax.model.constant.SpKey
import com.xcyh.reachmax.model.manager.Pitcher
import com.xcyh.reachmax.model.request.RequestResult
import com.xcyh.reachmax.model.request.Url
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * 登录页 ViewModel：复用回调式的 [BaseRequest] 完成网络登录，
 * 并把结果桥接到 [StateFlow]/[SharedFlow] 供 Compose 消费。
 *
 * 行为对齐旧版 `LoginPresenter.doLogin`，原 Presenter 保留为死代码不再使用。
 */
class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<LoginEvent>(
        replay = 0,
        extraBufferCapacity = 4,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val events: SharedFlow<LoginEvent> = _events.asSharedFlow()

    fun onUserNameChange(value: String) {
        _uiState.update { it.copy(userName = value) }
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value) }
    }

    fun togglePassword() {
        _uiState.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

    fun selectEnv(env: Env) {
        when (env) {
            Env.DEV -> {
                Url.BASE_URL = "https://launch.novelsbd.com/stg_song/"
                Url.reset()
            }
            Env.ONLINE -> {
                Url.BASE_URL = "https://launch.novelsbd.com/"
                Url.reset()
            }
        }
        _uiState.update {
            it.copy(envSelection = env, userName = "haojiangfeng", password = "hjf@123")
        }
    }

    fun login() {
        val state = _uiState.value
        if (state.userName.isEmpty()) {
            emit(LoginEvent.Toast("请输入用户名"))
            return
        }
        if (state.password.isEmpty()) {
            emit(LoginEvent.Toast("请输入密码"))
            return
        }
        if (state.isLoggingIn) return
        _uiState.update { it.copy(isLoggingIn = true) }

        val paramMap = linkedMapOf<String, Any>(
            "username" to state.userName,
            "password" to state.password,
        )

        val listener = object : ResponseListener {
            override fun onSuccess(
                apiPath: String,
                paramMap: Map<String, Any>,
                content: String,
                response: Response<ResponseBody>,
                isCache: Boolean,
            ) {
                try {
                    val result = RequestResult.formatBody(content, "data", LoginBean::class.java)
                    if (result != null && result.code == 0 && result.data != null) {
                        saveLoginInfo(content, result.data)
                        emit(LoginEvent.LoginSuccess)
                    } else {
                        emit(LoginEvent.LoginFail("登录失败"))
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                    emit(LoginEvent.LoginFail("登录解析失败"))
                } finally {
                    _uiState.update { it.copy(isLoggingIn = false) }
                }
            }

            override fun onFail(
                apiPath: String,
                paramMap: Map<String, Any>,
                e: ApiException,
                isCache: Boolean,
            ) {
                emit(LoginEvent.LoginFail("登录失败"))
                _uiState.update { it.copy(isLoggingIn = false) }
            }

            override fun onCancel(apiPath: String, paramMap: Map<String, Any>) {
                _uiState.update { it.copy(isLoggingIn = false) }
            }
        }

        BaseRequest().loadNetOnly(Url.API_LOGIN, paramMap, false, listener)
    }

    private fun saveLoginInfo(content: String, loginBean: LoginBean) {
        Pitcher.getInstance().setData(loginBean)
        PreferencesUtil.put(SpKey.USER_LOGIN, content)
        PreferencesUtil.put(SpKey.TIME_LOGIN, System.currentTimeMillis())
        PreferencesUtil.put(SpKey.BASE_URL, Url.BASE_URL)
        PreferencesUtil.put(UserManager.KEY_USER_ID, loginBean.userInfo.userName)
    }

    private fun emit(event: LoginEvent) {
        if (!_events.tryEmit(event)) {
            android.util.Log.w("LoginViewModel", "event dropped: $event")
        }
    }
}
