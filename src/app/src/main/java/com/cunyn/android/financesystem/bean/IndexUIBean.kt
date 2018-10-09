package com.cunyn.android.financesystem.bean

/**
 * AdImgSrc :广告地址（如果数组大于1，则左右滚动）
 * AdLinkURL:如果为null或者’’,则不跳转，否则跳转
 */
data class IndexUIBean(var AdImgSrc:String? , var AdLinkURL:String?){

}

/**
 * QuestionText:富文本输出，页面直接展示
 * CustomerMobile:
 * CustomerQQ:
 */
data class FeedbackBean(var QuestionText:String? , var CustomerMobile:String?,var CustomerQQ:String?)

/**
 * ApplyTime:申请时间
 * ApplyStatusStr:申请状态
 */
data class TradeRecordBean(var ApplyTime:String?,var ApplyStatusStr:String?)


data class ProtocalBean(var AgreementContent:String?)

data class PreOrderBean(var DataId:String?)