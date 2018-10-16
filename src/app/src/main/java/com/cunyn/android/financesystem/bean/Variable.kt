package com.cunyn.android.financesystem.bean


object Variable {

    /**
     * 是否显示首页使用引导图
     */
    var FirstUse =false

    /**
     * 是否显示首次使用APP的引导页
     */
    var SHOW_GUIDE=false

    /**
     * 用户信息
     */
    var UserBean:UserBean?=null

    /**
     * http cookies
     */
    var CookiesSet:Set<String>?=null
    /**
     * 是否已经上传了通讯录
     */
    var IsUploadContract=false

    /**
     * 是否授权了运营商数据
     */
    var IsUploadCarrier=false
}