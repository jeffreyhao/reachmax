package com.xcyh.reachmax.adv.detail

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.baidu.baselibrary.util.ui.AnimationUtil
import com.gyf.immersionbar.ImmersionBar
import com.tencent.common.util.ToastUtils
import com.xcyh.reachmax.R
import com.xcyh.reachmax.adv.detail.compose.AdvDetailScreen
import com.xcyh.reachmax.model.bean.ItemData

/**
 * 广告详情页 — Compose 实现。
 *
 * 原类继承 FlutterActivity，通过 MethodChannel 与 Flutter 通信；现改为 ComponentActivity，
 * 用 Compose 渲染。入参 advLevel + ItemData 通过静态持有者传递（详情页为瞬时页面），
 * onCreate 读取后立即清空释放引用。
 */
class AdvDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupImmersive()
        // 读取入参后立即清空静态引用释放内存
        val detail = AdvDetail.from(sPendingLevel, sPendingData)
        sPendingLevel = 0
        sPendingData = null

        setContent {
            AdvDetailScreen(
                detail = detail,
                onBack = { finish() },
                onCopy = { text -> copyText(text) },
            )
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

    /** 复制到剪贴板并 Toast 提示。文案对齐 Flutter 版。 */
    private fun copyText(text: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboard.setPrimaryClip(ClipData.newPlainText(null, text))
        ToastUtils.showCenterShort("已复制到剪贴板")
    }

    override fun finish() {
        super.finish()
        AnimationUtil.overridePendingTransition(this, R.anim.anim_none, R.anim.push_right_out)
    }

    companion object {
        /** 传递给详情页的入参；详情页为瞬时页面，读取后立即清空释放引用。 */
        private var sPendingLevel = 0
        private var sPendingData: ItemData? = null

        @JvmStatic
        fun start(context: Activity, advLevel: Int, itemData: ItemData?) {
            sPendingLevel = advLevel
            sPendingData = itemData
            context.startActivity(
                android.content.Intent(context, AdvDetailActivity::class.java)
            )
            AnimationUtil.overridePendingTransition(
                context, R.anim.slide_right_in_slow, R.anim.anim_none
            )
        }
    }
}
