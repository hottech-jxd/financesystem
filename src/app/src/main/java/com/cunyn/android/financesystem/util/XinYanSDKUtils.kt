package com.cunyn.android.financesystem.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.cunyn.android.financesystem.ResultActivity
import com.cunyn.android.financesystem.bean.XinYanData
import com.xinyan.bigdata.XinYanSDK
import com.xinyan.bigdata.bean.StartParams
import com.xinyan.bigdata.bean.XinyanCallBackData
import com.xinyan.bigdata.callback.XYBDResultCallback


/**
 * Created by L
 */
object XinYanSDKUtils {
    private var mOrderEn: String? = null
    private var mMemberId: String? = null
    private var mTerminaId: String? = null
    private var mTxnType: String? = null//任务类型
    private var mActivity: Activity? = null


    fun startSDK(activity: Activity, mermberid: String, terminalid: String, txnType: String, orderInfo: String, orderEn: String) {
        mActivity = activity
        mMemberId = mermberid
        mTerminaId = terminalid
        mTxnType = txnType
        mOrderEn = orderEn

        getTask(mActivity, mTxnType, orderInfo, mOrderEn)
    }

    fun getTask(mActivity: Activity?, txnType: String?, tradeNo: String, orderEn: String?): StartParams {

        //转对象传参数
        val startParams = StartParams()
        startParams.environment = orderEn//标识测试环境;
        startParams.type = txnType
        startParams.setmMemberId(mMemberId)
        startParams.setmTerminaId(mTerminaId)
        startParams.user_id = tradeNo//TODO  自己提供
        startParams.tradeNo = tradeNo

        startParams.realname = XinYanData.realname
        startParams.idcard = XinYanData.idcard
        startParams.phoneNum = XinYanData.phoneNum
        startParams.phoneServerCode = XinYanData.phoneServerCode
        startParams.carrierCanInput = XinYanData.carrierCanInput
        startParams.carrierIDandNameShow = XinYanData.carrierIDandNameShow


        startParams.titleConfig = XinYanData.titleConfig

        XinYanData.tradeNo = tradeNo//缓存
        XinYanData.memberId = mMemberId//缓存
        XinYanData.type = txnType//缓存

        execuSDK(mActivity, startParams)
        return startParams
    }

    private fun execuSDK(mActivity: Activity?, startParams: StartParams) {

        try {
            XinYanSDK.getInstance().start(mActivity, startParams, object : XYBDResultCallback {
                override fun onCallBack(xinyanCallBackData: XinyanCallBackData) {
                    openResultActivity(mActivity, xinyanCallBackData)
                    XinYanData.titleConfig.setmTitle("")//这里是为了下次做任务不带之前设置的title，走默认的title
                }

                override fun onError(throwable: Throwable) {
                    Toast.makeText(mActivity, throwable.message, Toast.LENGTH_SHORT).show()
                    Log.i("aaa", "=======--==========" + throwable.message)
                    XinYanData.titleConfig.setmTitle("")
                }
            })
        }catch (ex:Exception){
            ex.printStackTrace()
        }
    }

    /**
     * 打开结果展示页
     */
    fun openResultActivity(context: Context?, data: XinyanCallBackData) {
        val resultBundle = Bundle()
        resultBundle.putParcelable("data", data)
        val intent = Intent(context, ResultActivity::class.java)
        intent.putExtras(resultBundle)
        context!!.startActivity(intent)
    }

}