package com.cunyn.android.financesystem

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.cunyn.android.financesystem.bean.XinYanData
import com.cunyn.android.financesystem.bean.XinYan_CHANNEL
import com.xinyan.bigdata.bean.XinyanCallBackData
import kotlinx.android.synthetic.main.layout_header.*

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by creedon.dong on 2016/10/27.
 */
class ResultActivity : Activity() {
    private var tvResult: TextView? = null
    private var stringBuffer: StringBuffer? = null
    private var progressBar: ProgressBar? = null
    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            LoadTask().execute()
        }
    }
    private var url = ""
    private var baseResulturl: String? = null

    private//https://api.xinyan.com/data/carrier/v2/mobile/201805222136590131070693?mobile=
    //https://api.xinyan.com/data/qq/v1/alldata/{tradeNo}
    //data/chsi/v1/all/{tradeNO}
    ///data/chsi/v1/all/{tradeNO}
    ///data/chsi/v1/all/{tradeNO}
    val resulturl: String
        get() {
            if ( XinYan_CHANNEL.FUNCTION_TAOBAO.equals(XinYanData.type)) {
                url = baseResulturl!! + XinYanData.UrlManager.TAOBAO_RESULT_URL
            } else if ( XinYan_CHANNEL.FUNCTION_ALIPAY.equals(XinYanData.type)) {
                url = baseResulturl!! + XinYanData.UrlManager.ALIPAY_RESULT_URL
            } else if ( XinYan_CHANNEL.FUNCTION_JINGDONG.equals(XinYanData.type)) {
                url = baseResulturl!! + XinYanData.UrlManager.JINGDONG_RESULT_URL
            } else if (XinYan_CHANNEL.FUNCTION_CARRIER.equals(XinYanData.type)) {
                url = baseResulturl!! + XinYanData.UrlManager.CARRIER_RESULT_URL
            } else if (XinYan_CHANNEL.FUNCTION_QQ.equals(XinYanData.type)) {
                url = baseResulturl!! + XinYanData.UrlManager.QQ_RESULT_URL
            } else if (XinYan_CHANNEL.FUNCTION_FUND.equals(XinYanData.type)) {
                url = baseResulturl!! + XinYanData.UrlManager.FUND_RESULT_URL
            } else if (XinYan_CHANNEL.FUNCTION_CHSI.equals(XinYanData.type)) {
                url = baseResulturl!! + XinYanData.UrlManager.CHIS_RESULT_URL
            } else if (XinYan_CHANNEL.FUNCTION_DIDI.equals(XinYanData.type)) {
                url = baseResulturl!! + XinYanData.UrlManager.DIDI_RESULT_URL
            } else if (XinYan_CHANNEL.FUNCTION_MAIL.equals(XinYanData.type)) {
                url = baseResulturl!! + XinYanData.UrlManager.MAIL_RESULT_URL
            } else if (XinYan_CHANNEL.FUNCTION_SECURITY.equals(XinYanData.type)) {
                url = baseResulturl!! + XinYanData.UrlManager.SECURITY_RESULT_URL
            } else if (XinYan_CHANNEL.FUNCTION_ONLINE_BANK.equals(XinYanData.type)) {
                url = baseResulturl!! + XinYanData.UrlManager.BANK_RESULT_URL
            } else if (XinYan_CHANNEL.FUNCTION_TAOBAOPAY.equals(XinYanData.type)) {
                url = baseResulturl!! + XinYanData.UrlManager.TAOBAOPAY_RESULT_URL
            }
            url = url.replace("\\{[^}]*\\}".toRegex(), XinYanData.tradeNo!!)
            return url

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        tvResult = findViewById(R.id.tvResult)
        progressBar = findViewById(R.id.pb)
        tvResult!!.movementMethod = ScrollingMovementMethod()
        header_left_image.setImageResource(R.mipmap.arrow_left)
        header_left_image.setOnClickListener(View.OnClickListener { finish() })
        initData()

    }

    private fun initData() {
        try {
            if ("test" == XinYanData.environment) {
                baseResulturl = "https://test.xinyan.com/data/"
            }
            if ("product" == XinYanData.environment) {
                baseResulturl = "https://api.xinyan.com/data/"
            }
            val xinyanCallBackData = intent.extras!!.get("data") as XinyanCallBackData
            stringBuffer = StringBuffer()
            stringBuffer!!.append("订单ID:" + XinYanData.tradeNo + "\n")
            stringBuffer!!.append("任务ID:" + xinyanCallBackData.taskId + "\n")
            stringBuffer!!.append("任务消息:" + xinyanCallBackData.message + "\n")
            tvResult!!.text = stringBuffer!!.toString()

            if ("YES" == XinYanData.titleConfig.getLoginSuccessQuit()) {//登录退出模式，结果需要自己轮询解析的状态，然后再获取结果
                handler.sendEmptyMessageDelayed(1, 5000)
            } else {
                handler.sendEmptyMessageDelayed(1, 500)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    internal inner class LoadTask : AsyncTask<String, String, String>() {


        override fun onPreExecute() {}

        //        支付宝：
        //                1、查询用户支付宝基本信息
        ///data/alipay/v3/info/{orderNo}
        //
        //2、查询用户的支付宝交易记录信息
        ///data/alipay/v3/tradeInfo/{orderNo}
        //
        //3、查询用户的支付宝资产状况
        ///data/alipay/v3/wealth/{orderNo}
        //
        //4、查询用户花呗消费记录和银行卡消费记录
        ///data/alipay/v3/assetinfo/{orderNo}
        //
        //5、查询用户所有数据
        ///data/alipay/v3/data/{orderNo}
        //
        //6、查询支付宝芝麻信用分信息
        ///data/alipay/v3/zmscore/{orderNo}
        //
        //        京东：
        //                1、查询用户的基本信息
        ///data/jingdong/v3/userinfo/{orderNo}
        //2、查询用户的收货地址信息
        ///data/jingdong/v2/deliverAddress/{orderNo}
        //3. 查询用户的资产信息
        ///data/jingdong/v3/wealth/{orderNo}
        //4、查询用户的交易信息(不含商品)
        ///data/jingdong/v3/trade/info/{orderNo}？page=&pageSize=
        //                5、根据订单号分页查询用户交易记录(含商品信息)
        ///data/jingdong/v3/trade/details/{orderNo}？page=&pageSize=
        //                6、查询用户的全部信息
        ///data/jingdong/v3/userdata/{orderNo}
        //7、查询用户的白条账单信息
        ///data/jingdong/v3/btdata/{orderNo}
        //8、查询用户的金条借款记录
        ///data/jingdong/v3/jtdata/{orderNo}
        //
        //
        //        淘宝：
        //                1、 查询用户的基本信息
        ///data/taobao/v3/user/{orderNo}
        //2、查询用户的淘宝收货地址
        ///data/taobao/v3/deliveraddress/{orderNo}
        //3、查询用户的最近几笔订单的收货地址
        override fun doInBackground(params: Array<String>): String {
            resulturl
            Log.i("tag", "url == $url")
            val orderinfo = loadOrderInFo(url)
            Log.i("tag", "orderinfo == $orderinfo")
            return orderinfo
        }

        override fun onPostExecute(orderinfo: String) {
            stringBuffer!!.append(orderinfo)
            runOnUiThread {
                if (!isFinishing) {
                    tvResult!!.text = stringBuffer!!.toString()
                    progressBar!!.visibility = View.GONE
                }
            }

        }
    }

    fun loadOrderInFo(url: String): String {
        Log.i(ResultActivity::class.simpleName ,"loadOrderInFo url = $url")
        val json = StringBuilder()
        val inReader : BufferedReader
        try {
            val urlObject = URL(url)
            val uc = urlObject.openConnection() as HttpURLConnection
            uc.addRequestProperty("memberId", XinYanData.memberId)
            inReader = BufferedReader(InputStreamReader(uc.inputStream))
            var inputLine: String? = null

            inputLine = inReader.readLine()
            while (inputLine  != null) {
                json.append(inputLine)
                inputLine = inReader.readLine()
            }
            inReader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return json.toString()

    }

}