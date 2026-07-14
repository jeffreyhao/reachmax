package com.xcyh.reachmax.adv.detail

import com.xcyh.reachmax.model.bean.ItemData
import com.xcyh.reachmax.model.constant.AdvConst
import com.xcyh.reachmax.model.constant.AdvItemLevel

/**
 * 广告详情展示数据类（UI State）
 * 💡【data class 的好处】：Kotlin 的数据类会自动帮你生成 toString(), equals(), hashCode() 等方法，非常适合纯装数据的对象。
 * * @param advLevel 当前维度（账户 4 / 系列 3 / 组 2 / 计划 1）
 */
data class AdvDetail(
    val advLevel: Int,
    val launchName: String,
    val adAccountName: String,
    val adAccount: String,
    val campaignName: String,
    val campaignId: String,
    val campaignStatus: String,
    val adsetName: String,
    val adsetId: String,
    val adsetStatus: String,
    val adName: String,
    val adId: String,
    val adStatus: String,
) {
    /** * 💡【Kotlin 衍生属性（Custom Getter）】
     * `title` 字段并不需要后台传给我们。它是一个动态属性，只要 `advLevel` 发生变化，
     * 它就会自动调用 `AdvConst.getLevelText(advLevel)` 去拼接出诸如 "广告账户详情"、"广告组详情" 这样的标题字串。
     * 在 Compose 顶栏中直接调用 `detail.title` 即可拿到结果。
     */
    val title: String
        get() = AdvConst.getLevelText(advLevel) + "详情"

    companion object {
        /** * 💡【核心工厂函数】：从原始的复杂的 `ItemData` 映射、清洗、转换成一个干净的 `AdvDetail` 对象。
         */
        fun from(level: Int, d: ItemData?): AdvDetail {
            // 🛡️【防空安全保护 1】：如果上个页面传过来的核心数据是空的，UI 不能崩，返回一个全空字符串的默认对象
            if (d == null) {
                return AdvDetail(
                    advLevel = level,
                    launchName = "",
                    adAccountName = "",
                    adAccount = "",
                    campaignName = "",
                    campaignId = "",
                    campaignStatus = "",
                    adsetName = "",
                    adsetId = "",
                    adsetStatus = "",
                    adName = "",
                    adId = "",
                    adStatus = "",
                )
            }

            // 🛡️【防空安全保护 2】：利用自定义的 str() 函数，将可能为 null 的字段一律变成 ""（空字符串）
            // 这样 Compose 在读取例如 `detail.adAccount` 时，就绝对不需要担心 NPE（空指针异常）了。
            return AdvDetail(
                advLevel = level,
                launchName = str(d.launch_name),
                adAccountName = str(d.ad_account_name),
                adAccount = str(d.ad_account),
                campaignName = str(d.campaign_name),
                campaignId = str(d.campaign_id),
                campaignStatus = str(d.campaign_status),
                adsetName = str(d.adset_name),
                adsetId = str(d.adset_id),
                adsetStatus = str(d.adset_status),
                adName = str(d.ad_name),
                adId = str(d.ad_id),
                adStatus = str(d.ad_status),
            )
        }

        /** * 💡【Kotlin 简写单行函数】
         * 如果传入的 `v` 是 null，就返回 `""`；如果不为 null，就返回其本身。
         * `?:` 叫做 Elvis 操作符（空值合并操作符）。
         */
        private fun str(v: String?): String = v ?: ""
    }
}