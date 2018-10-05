package com.cunyn.android.financesystem.fragment


import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cunyn.android.financesysetm.widget.TipAlertDialog
import com.cunyn.android.financesystem.BuildConfig

import com.cunyn.android.financesystem.R
import com.cunyn.android.financesystem.bean.*
import com.cunyn.android.financesystem.mvp.AuditionPresenter
import com.cunyn.android.financesystem.mvp.IPresenter
import com.cunyn.android.financesystem.util.IDCardUtils
import com.cunyn.android.financesystem.util.KeybordUtils
import com.cunyn.android.financesystem.util.MobileUtils
import com.cunyn.android.financesystem.util.XinYanSDKUtils
import com.cunyn.android.financesystem.widget.CarrierDialog
import com.cunyn.android.financesysten.util.DensityUtils
import com.facebook.drawee.view.SimpleDraweeView
import com.guoxintaiyi.android.missionwallet.base.BaseFragment
import com.guoxintaiyi.android.missionwallet.util.FrescoDraweeController
import com.guoxintaiyi.android.missionwallet.util.FrescoDraweeListener
import com.xinyan.bigdata.XinYanSDK
import kotlinx.android.synthetic.main.fragment_info_audition.*
import kotlinx.android.synthetic.main.layout_header.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InfoAuditionFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class InfoAuditionFragment : BaseFragment<AuditionContract.Presenter>()
        ,AuditionContract.View
        ,FrescoDraweeListener.ImageCallback{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var isAgree :Boolean=false
    //private var orderInfo :String=""
    private var iPresenter = AuditionPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun initView() {
        header_title.text="填写资料"
        header_left_image.setImageResource(R.mipmap.arrow_left)
        header_left_image.setOnClickListener(this)

        info_bank_mobile.setText( if(Variable.UserBean!=null) Variable.UserBean!!.UserName else "")

        info_contract_lay.setOnClickListener(this)
        info_carrier_lay.setOnClickListener(this)
        info_taobao.setOnClickListener(this)
        info_jd.setOnClickListener(this)
        info_gjj.setOnClickListener(this)
        info_agree.setOnClickListener(this)
        info_submit.setOnClickListener(this)

        info_bank_type_1.setOnCheckedChangeListener { _, isChecked -> info_bank_type_2.isChecked = !isChecked }
        info_bank_type_2.setOnCheckedChangeListener { _, isChecked -> info_bank_type_1.isChecked= !isChecked }

        info_bank_validate_type_1.setOnCheckedChangeListener { _, isChecked -> info_bank_validate_type_2.isChecked=!isChecked }
        info_bank_validate_type_2.setOnCheckedChangeListener { buttonView, isChecked -> info_bank_validate_type_1.isChecked=!isChecked  }

        var width = DensityUtils.getScreenWidth(context!!)
        var height = resources.getDimension(R.dimen.dp_150).toInt()
        var url = "res://"+ context!!.packageName +"/"+R.mipmap.dabg
        FrescoDraweeController.loadImage(info_banner, width,height , url , this )

    }

    override fun fetchData() {
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_info_audition
    }

    override fun interceptBackPressed(): Boolean {
        return false
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when(v!!.id){
            R.id.header_left_image->{
                closeFragment()
            }
            R.id.info_contract_lay->{
               contract()
            }
            R.id.info_carrier_lay->{
                //carrier()
                iPresenter.createPreOrder( XinYan_CHANNEL.FUNCTION_CARRIER.channelName ,Constants.CUSTOMERID)
            }
            R.id.info_taobao->{
//                XinYanSDKUtils.startSDK(activity!!
//                        , BuildConfig.MEMBER_ID
//                        , BuildConfig.TERMINAL_ID
//                        , XinYan_CHANNEL.FUNCTION_TAOBAO.channelName
//                        , orderInfo , BuildConfig.ENVIRONMENT )
                iPresenter.createPreOrder(XinYan_CHANNEL.FUNCTION_TAOBAO.channelName , Constants.CUSTOMERID)

            }
            R.id.info_jd->{
//                XinYanSDKUtils.startSDK(activity!!
//                        , BuildConfig.MEMBER_ID, BuildConfig.TERMINAL_ID
//                        , XinYan_CHANNEL.FUNCTION_JINGDONG.channelName
//                        , orderInfo, BuildConfig.ENVIRONMENT)
                iPresenter.createPreOrder(XinYan_CHANNEL.FUNCTION_JINGDONG.channelName , Constants.CUSTOMERID)

            }
            R.id.info_gjj->{
//                XinYanSDKUtils.startSDK(activity!!
//                        , BuildConfig.MEMBER_ID , BuildConfig.TERMINAL_ID
//                        , XinYan_CHANNEL.FUNCTION_FUND.channelName
//                        , orderInfo, BuildConfig.ENVIRONMENT)
                iPresenter.createPreOrder(XinYan_CHANNEL.FUNCTION_FUND.channelName,Constants.CUSTOMERID)

            }
            R.id.info_agree->{
                isAgree=!isAgree
                var draw = if(isAgree) ContextCompat.getDrawable(context!!,R.mipmap.da_gou) else ContextCompat.getDrawable(context!!,R.mipmap.da_bugou)
                draw!!.setBounds(0,0,draw!!.intrinsicWidth,draw!!.intrinsicHeight)
                info_agree.setCompoundDrawables(draw,null,null,null)
            }
            R.id.info_submit->{
                submit()
            }
        }
    }



    private fun submit() {
        if (!isAgree) {
            toast("请勾选")
            return
        }

        var realname = info_name.text.trim().toString()
        var idcard = info_idcard.text.trim().toString()
        var bankno = info_bank.text.trim().toString()
        var mobiel = info_bank_mobile.text.trim().toString()// Variable.UserBean!!.UserName
        var bankType = if (info_bank_type_1.isChecked) "101" else "102"
        var bankYear = info_bank_year.text.trim().toString()
        var bankMonth = info_bank_month.text.trim().toString()
        var validateType = if (info_bank_validate_type_1.isChecked) "123" else "1234"
        var safeCode = info_bank_safecode.text.trim().toString()

        if (TextUtils.isEmpty(realname)) {
            info_name.requestFocus()
            KeybordUtils.openKeybord(context!!, info_name)
            toast("请输入姓名")
            return
        }
        if (TextUtils.isEmpty(idcard)) {
            info_idcard.requestFocus()
            KeybordUtils.openKeybord(context!!, info_idcard)
            toast("请输入身份证号码")
            return
        }
        var validate = IDCardUtils.validate_effective(idcard)
        if (validate != idcard) {
            info_idcard.requestFocus()
            KeybordUtils.openKeybord(context!!, info_idcard)
            toast(validate)
            return
        }

        if (TextUtils.isEmpty(bankno)) {
            info_bank.requestFocus()
            KeybordUtils.openKeybord(context!!, info_bank)
            toast("请输入银行卡号")
            return
        }

        if (TextUtils.isEmpty(mobiel)) {
            info_bank_mobile.requestFocus()
            KeybordUtils.openKeybord(context!!, info_bank_mobile)
            toast("请输入银行预留手机号")
            return
        }
        if (MobileUtils.isPhone(mobiel)) {
            info_bank_mobile.requestFocus()
            KeybordUtils.openKeybord(context!!, info_bank_mobile)
            toast("请输入正确的手机号")
            return
        }

        if (TextUtils.isEmpty(bankYear)) {
            info_bank_year.requestFocus()
            KeybordUtils.openKeybord(context!!, info_bank_year)
            toast("请输入卡有效期年份")
            return
        }
        if (TextUtils.isEmpty(bankMonth)) {
            info_bank_month.requestFocus()
            KeybordUtils.openKeybord(context!!, info_bank_month)
            toast("请输入卡有效期月份")
            return
        }
        if (TextUtils.isEmpty(safeCode)) {
            info_bank_safecode.requestFocus()
            KeybordUtils.openKeybord(context!!, info_bank_safecode)
            toast("请输入信用卡安全码")
        }


        iPresenter.submit(realname
                , idcard
                , mobiel
                , bankno,
                bankType,
                bankYear,
                bankMonth,
                safeCode,
                validateType)
    }

    private fun contract(){
        var tipAlertDialog = TipAlertDialog(context!!,false)

        tipAlertDialog.show("询问"
                ,"是否允许App获得通讯录数据？"
                , R.color.text_color_2B3041 , true, true
                , object: View.OnClickListener{
                    override fun onClick(v: View?) {
                        tipAlertDialog.dismiss()
                    }
        }
        , object :View.OnClickListener {
            override fun onClick(v: View?) {
                tipAlertDialog.dismiss()

                iPresenter.uploadContracts(Constants.CUSTOMERID,
                        Variable.UserBean!!.UserName!!  )

            }
        }
        )

    }

    //private fun getContract(){}

    private fun carrier(orderInfo:String){
        val mFragmentManager = activity!!.supportFragmentManager
        var ft = mFragmentManager.beginTransaction()
        val prev = mFragmentManager.findFragmentByTag("carrier")
        if (prev != null) {
            ft.remove(prev).commit()
            ft = mFragmentManager.beginTransaction()
        }
        val fragment = CarrierDialog()
        fragment.setmOnNextListener(object : CarrierDialog.OnNextListener {
            override fun onClick() {
                XinYanSDKUtils.startSDK(activity!!
                        , BuildConfig.MEMBER_ID, BuildConfig.TERMINAL_ID
                        , XinYan_CHANNEL.FUNCTION_CARRIER.channelName
                        , Variable.UserBean!!.UserId.toString()
                        , orderInfo , BuildConfig.ENVIRONMENT)
            }
        })
        fragment.isCancelable=false
        fragment.show(ft, "carrier")

    }


    override fun imageFailure(width: Int, height: Int, simpleDraweeView: SimpleDraweeView) {

    }

    override fun imageCallback(width: Int, height: Int, simpleDraweeView: SimpleDraweeView) {
        simpleDraweeView.layoutParams.width = width
        simpleDraweeView.layoutParams.height =height
    }

    override fun uploadContractsCallback(apiResult: ApiResult<Any?>) {
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.message)
            return
        }
        toast(apiResult.message)
    }

    override fun submitCallback(apiResult: ApiResult<Any?>) {
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.message)
            return
        }
        if(activity!=null) {
            KeybordUtils.closeKeyboard(activity!!)
        }
        closeFragment()
    }

    override fun createPreOrderCallback(apiResult: ApiResult<PreOrderBean?> , txType:String ) {
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.message)
            return
        }
        if(apiResult.data==null) return
        if(activity==null)return

        var orderId = apiResult.data!!.DataId!!

        if(txType== XinYan_CHANNEL.FUNCTION_CARRIER.channelName ){
            carrier(orderId)
        }else {
            XinYanSDKUtils.startSDK(activity!!, BuildConfig.TERMINAL_ID
                    , BuildConfig.MEMBER_ID, txType
                    , Variable.UserBean!!.UserId.toString()
                    ,orderId, BuildConfig.ENVIRONMENT)
        }
    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        info_progress.visibility=View.VISIBLE
    }

    override fun hideProgress() {
        super.hideProgress()
        info_progress.visibility=View.GONE
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InfoAuditionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                InfoAuditionFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
