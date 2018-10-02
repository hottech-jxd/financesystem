package com.cunyn.android.financesystem.bean


/**
 * BannerColor:背景图颜色 "#fff
 * ButtonColor:按钮颜色 "#fff
 * BannerURL:背景图地址
 */
data class LoginUIBean(var BannerColor:String, var ButtonColor :String,var BannerURL:String)
{}



data class UserBean(var UserId:Long=0, var UserName:String?="")