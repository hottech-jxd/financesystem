package com.cunyn.android.financesystem.bean


/**
 * BannerColor:背景图颜色
 * ButtonColor:按钮颜色
 * BannerURL:背景图地址
 */
data class LoginUIBean(var BannerColor:String, var ButtonColor :String,var BannerURL:String)
{}

// class UserBean{
//    constructor(){ this.UserId=0}
//    constructor( UserId:Long=0){
//        this.UserId = UserId
//    }
//    private var UserId:Long=0
//
//
//}


data class UserBean(var UserId:Long=0, var UserName:String?="")