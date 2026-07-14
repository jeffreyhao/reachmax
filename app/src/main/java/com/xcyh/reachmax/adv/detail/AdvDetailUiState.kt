package com.xcyh.reachmax.adv.detail

import com.xcyh.reachmax.model.bean.ItemData
import com.xcyh.reachmax.model.constant.AdvConst
import com.xcyh.reachmax.model.constant.AdvItemLevel

/**
 * 广告详情展示数据，字段对齐 Flutter 版 AdvDetail。
 *
 * @param advLevel 当前维度，见 [AdvItemLevel]（账户 4 / 系列 3 / 组 2 / 计划 1）
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
    /** 标题：广告账户/系列/组/计划 + "详情"，复用 [AdvConst.getLevelText]。 */
    val title: String
        get() = AdvConst.getLevelText(advLevel) + "详情"

    companion object {
        /** 从 ItemData 构建详情数据，字段名对齐 Flutter 侧 AdvDetail.fromMap。 */
        fun from(level: Int, d: ItemData?): AdvDetail {
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

        private fun str(v: String?): String = v ?: ""
    }
}
