package com.cunyn.android.financesystem.fragment


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.cunyn.android.financesystem.R
import com.cunyn.android.financesystem.bean.ApiResult
import com.cunyn.android.financesystem.bean.ApiResultCodeEnum
import com.cunyn.android.financesystem.bean.Constants
import com.cunyn.android.financesystem.bean.FeedbackBean
import com.cunyn.android.financesystem.mvp.FaqPresenter
import com.cunyn.android.financesystem.mvp.IPresenter
import com.guoxintaiyi.android.missionwallet.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_faq.*
import kotlinx.android.synthetic.main.layout_header.*
import java.security.Permission
import java.security.Permissions
import java.util.*
import java.util.jar.Manifest
import android.Manifest.permission
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import FaqContract
import android.content.pm.PackageManager
import android.os.Build


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FaqFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FaqFragment : BaseFragment<FaqContract.Presenter>()
,FaqContract.View{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var iPresenter=FaqPresenter(this)
    private var qq:String?=""
    private var phone :String?=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)

        }
    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        faq_progress.visibility=View.VISIBLE
    }

    override fun hideProgress() {
        super.hideProgress()
        faq_progress.visibility=View.GONE
    }

    override fun initView() {
        header_title.text="常见问题及反馈"
        header_left_image.setImageResource(R.mipmap.arrow_left)
        header_left_image.setOnClickListener(this)
        faq_qq.setOnClickListener(this)
        faq_phone.setOnClickListener(this)

        iPresenter.getFaqData(Constants.CUSTOMERID)
    }

    override fun fetchData() {
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_faq
    }

    override fun interceptBackPressed(): Boolean {
        return false
    }

    private fun checkPermission() {
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            if (ContextCompat.checkSelfPermission(context!!, android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                callphone()
            } else {
                var permissions = arrayOf<String>(android.Manifest.permission.CALL_PHONE)

                requestPermissions( permissions, 1100)

            }
        }else{
            callphone()
        }
    }

    private fun callphone(){
        var intent= Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:${phone}")
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if( requestCode== 1100){
            if( grantResults!=null && grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                callphone()
            }else {
                toast("无法使用电话功能")
            }
        }
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when(v!!.id){
            R.id.header_left_image->{
                closeFragment()
            }
            R.id.faq_qq->{

                var url="mqqwpa://im/chat?chat_type=wpa&uin=${qq}"
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                if(intent.resolveActivity( context!!.packageManager)==null){
                    toast("请先安装QQ客户端,再使用此功能")
                    return
                }else{
                    startActivity(intent)
                }

            }
            R.id.faq_phone->{
                checkPermission()
            }
        }
    }

    override fun getFagDataCallback(apiResult: ApiResult<FeedbackBean?>) {
        if(apiResult.code!= ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.message)
            return
        }
        if(apiResult.data==null) return

        var htmltext = apiResult.data!!.QuestionText
        var encode = "utf-8"
        var mimetype =""
        faq_webview.loadMarkdown(htmltext  )
        //faq_qq.text= apiResult.result!!.CustomerQQ
        //faq_phone.text =apiResult.result!!.CustomerMobile
        qq = apiResult.data!!.CustomerQQ
        phone=apiResult.data!!.CustomerMobile
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FaqFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                FaqFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
