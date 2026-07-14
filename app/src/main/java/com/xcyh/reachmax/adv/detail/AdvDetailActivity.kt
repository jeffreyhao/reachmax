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
 * 广告详情页 — 承载 Compose 的 Activity 容器。
 * * 💡【混合开发背景小知识】：
 * 注释里提到“原类继承 FlutterActivity...现改为 ComponentActivity”，说明这个项目以前部分页面是 Flutter 写的，
 * 现在的重构目标是用原生的 Jetpack Compose 替换掉 Flutter 页面。
 */
class AdvDetailActivity : ComponentActivity() { // 💡【ComponentActivity】：Compose 页面的标准容器基类

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupImmersive()

        // 2. 💡【核心考点：读取并释放临时数据】
        // 从伴生对象（静态变量）中取出上个页面传过来的参数，转成本页面的 AdvDetail 数据模型
        val detail = AdvDetail.from(sPendingLevel, sPendingData)

        // 🔴【非常重要】：取完数据立即置空！
        // 因为静态变量（sPendingData）会一直存活在内存中，如果不手动置空，它引用的复杂数据对象（ItemData）就无法被垃圾回收（GC），从而导致内存泄漏。
        sPendingLevel = 0
        sPendingData = null

        // 3. 💡【setContent：将 Compose 注入 Activity】
        // 传统 Android 用 setContentView(R.layout.xxx) 加载 XML。
        // Compose 用 setContent { ... } 告诉 Activity 开始渲染花括号内部的 Compose 代码。
        setContent {
            // 调用我们之前加满注释的那个 UI 界面
            AdvDetailScreen(
                detail = detail, // 传入刚刚构建好的干净数据

                // 💡【Lambda 回调落地实现 1】：当用户在 UI 里点击返回键时，Activity 执行 finish() 销毁自己
                onBack = { finish() },

                // 💡【Lambda 回调落地实现 2】：当用户在 UI 里点击复制时，调用本类的 copyText() 处理系统剪贴板
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

    /** * 💡【系统能力调用】：复制文本到剪贴板并弹出 Toast 提示。
     * 这种涉及底层 Android 硬件/系统服务的代码，最适合写在 Activity 或 ViewModel 里，而不是写在纯 UI 的 Compose 中。
     */
    private fun copyText(text: String) {
        // 获取系统的剪贴板服务
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        // 创建一条剪贴板数据
        val clip = ClipData.newPlainText(null, text)
        // 将数据塞入剪贴板
        clipboard.setPrimaryClip(clip)
        // 弹出居中的 Toast 提示用户
        ToastUtils.showCenterShort("已复制到剪贴板")
    }

    /** * 重写 finish 方法，在退出页面时加入一个“向右滑出屏幕”的动画效果
     */
    override fun finish() {
        super.finish()
        AnimationUtil.overridePendingTransition(this, R.anim.anim_none, R.anim.push_right_out)
    }

    // 💡【companion object：伴生对象（相当于 Java 中的 static 静态块）】
    companion object {
        // 用来临时存放跨页面传递的参数
        private var sPendingLevel = 0
        private var sPendingData: ItemData? = null

        /**
         * 💡【标准页面跳转函数】
         * 外部（比如列表页）想打开本详情页时，直接调用 `AdvDetailActivity.start(context, level, data)` 即可。
         *
         * 问：为什么不直接把 data 放进 Intent 传过去？
         * 答：Android 的 Intent 传递大数据时有 1MB 限制（过多会报 TransactionTooLargeException 崩溃）。
         * 对于广告大文本、长列表等复杂实体类，采用这种“短时间内静态持有，用完立删”的临时传输方案更安全。
         */
        @JvmStatic
        fun start(context: Activity, advLevel: Int, itemData: ItemData?) {
            // 1. 将参数存入临时静态池
            sPendingLevel = advLevel
            sPendingData = itemData

            // 2. 正常跳转 Activity
            context.startActivity(
                android.content.Intent(context, AdvDetailActivity::class.java)
            )

            // 3. 执行“从右往左滑入”的炫酷转场动画
            AnimationUtil.overridePendingTransition(
                context, R.anim.slide_right_in_slow, R.anim.anim_none
            )
        }
    }
}