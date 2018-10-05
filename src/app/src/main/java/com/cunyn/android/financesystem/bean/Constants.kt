package com.cunyn.android.financesystem.bean

import android.os.Environment

object Constants {
    val OS_TYPE:Int=3  //系统类型 unknown->0 , miniprogram->1；ios->2；android->3；h5->4
    val APP_SECRET:String="3ab0d4e6a8bf9b5b1cb1d38d00bcf339"
    val CUSTOMERID:Long=4886

    val READ_TIMEOUT :Long= 15
    val CONNECT_TIMEOUT :Long= 15
    val WRITE_TIMEOUT :Long= 15
    val PAGE_SIZE=10

    //MOCK 地址
    val BASE_URL:String ="http://api.mingshz.com/mock/104/"

    //测试地址
    //val BASE_URL:String= "http://mfapi.mifangtest.com/"
    //正式地址
    //val BASE_URL :String="http://cyfp.yhunba.cn/api/XYData/"

    /**
     * app版本检测地址
     */
    //var app_check_url = BASE_URL + "recycle/sys/checkUpdate"


    var MESSAGE_TOKEN_LOST = "你的账号在其他设备登录，请重新登录"
    var MESSAGE_ERROR = "发生错误,请重试"
    var TIP_LOADING = "加载中..."
    var TIP_PROCESSING = "处理中..."



    val INTENT_URL="url"
    val INTENT_FRAGMENT = "fragment"
    val INTENT_PHONE="phone"

    val INTENT_OPERATE_TYPE="operate_type"
    val INTENT_BEAN="bean"
    val INTENT_PUSH_KEY = "intent_push_key"
    val INTENT_PAY_ACCOUNT="pay_account"
    val INTENT_PAY_ACCOUNT_ID="pay_account_id"
    val INTENT_ORDER_SOURCE="order_source"
    val INTENT_SETTING_TYPE = "setting_type"
    val INTENT_TRANSACTIONID="transactionid"
    val INTENT_TITLE = "title"
    val INTENT_RECEIVE_ADDRESS = "receive_address"
    val INTENT_PAY_ADDRESS="pay_address"
    val INTENT_URL1="url1"
    val INTENT_URL2="url2"
    val INTENT_STATUS="status"

    val PREF_FILENAME="pref_filename"
    val PREF_WECHAT_USER="pref_wechat_user"
    var PREF_USER = "pref_user"
    val PREF_SEARCH_FILENAME="pref_filename_search"
    var PREF_KEY="pref_key"
    val PREF_PLATTYPE="pref_plattype"
    val PREF_WECHAT_ACCESSTOKEN="pref_wechat_accesstoken"
    val PREF_FIRST="pref_first"
    val PREF_LANGUAGE="pref_language"
    val PREF_CURRENCY="pref_currency"
    val PREF_SHOW_GUIDE="pref_show_guide"

    val ACTION_AUTH_FINISH="com.huotu.android.phonerecycle.action_auth_finish"
    val ACTION_AUTH_CHANGE="com.huotu.android.phonerecycle.action_auth_change"
    val ACTION_LOGOUT="com.huotu.android.phonerecycle.action_logout"
    val ACTION_ORDER_CHANGE="com.huotu.android.phonerecycle.action_order_change"
    val ACTION_WECHAT_LOGIN="com.huoto.android.mifang.wechatlogin"


    val ImageDirPath= Environment.getExternalStorageDirectory().toString()+"/missionwallet/images/"
    val VideoDirPath = Environment.getExternalStorageDirectory().toString()+"/missionwallet/videos/"
    val CrashDirPath = Environment.getExternalStorageDirectory().toString()+"/financesystem/crash/"

}