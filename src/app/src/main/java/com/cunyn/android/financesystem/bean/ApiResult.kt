/*
 *  版权所有:浙江国信泰一数据科技有限公司
 *  地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 *  (c) Copyright ZheJiang GuoXinTaiYi Data Technology Co., Ltd.
 *  Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 *  2018-2018. All rights reserved.
 */

package com.cunyn.android.financesystem.bean

data class ApiResult<T> (
    var code:Int=0,
    var message:String="",
    var result:T?=null
    )