package com.cunyn.android.financesystem.fragment


import android.content.Context
//import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.View
import cn.iwgang.countdownview.CountdownView
import com.cunyn.android.financesystem.R
import com.cunyn.android.financesystem.base.BaseApplication
import com.cunyn.android.financesystem.bean.*
import com.cunyn.android.financesystem.mvp.LoginPresenter
import com.cunyn.android.financesystem.util.GsonUtils
import com.cunyn.android.financesystem.util.KeybordUtils
import com.cunyn.android.financesystem.util.MobileUtils
import com.cunyn.android.financesystem.util.SPUtils
import com.cunyn.android.financesystem.viewmodel.UserViewModel
import com.cunyn.android.financesysten.util.DensityUtils
import com.facebook.drawee.view.SimpleDraweeView
import com.guoxintaiyi.android.missionwallet.base.BaseFragment
import com.guoxintaiyi.android.missionwallet.util.FrescoDraweeController
import com.guoxintaiyi.android.missionwallet.util.FrescoDraweeListener
import kotlinx.android.synthetic.main.fragment_login_register.*
import kotlinx.android.synthetic.main.layout_header.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginRegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class LoginRegisterFragment : BaseFragment<LoginContract.Presenter>()
        , CountdownView.OnCountdownEndListener
        ,FrescoDraweeListener.ImageCallback
,LoginContract.View{


    private var safeKey :String?=""
    private var delay = 60 * 1000L
    private var viewModel = LoginPresenter(this)
    private var isAgress = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        loginregister_countdown.setOnCountdownEndListener(null)

    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        loginregister_progress.visibility=View.VISIBLE
    }

    override fun hideProgress() {
        super.hideProgress()
        loginregister_progress.visibility=View.GONE
    }

    override fun initView() {

        header_left_image.setImageResource(R.mipmap.arrow_left)
        header_left_image.setOnClickListener(this)
        header_title.text="登录"


        loginregister_validatecode_picture.setOnClickListener(this)
        loginregister_login.setOnClickListener(this)
        loginregister_sendcode.setOnClickListener(this)
        loginregister_countdown.setOnCountdownEndListener(this)
        loginregister_protocal.setOnClickListener(this)
        loginregister_protocal_text.setOnClickListener(this)

        loginregister_markdown_lay.setOnClickListener(this)

        //dataBind = DataBindingUtil.bind(rootView!!)!!

        //var data = UserViewModel(8888)

        //dataBind!!.bean = data



        fetchData()
    }

    override fun fetchData() {
        viewModel.getPictureCode()
        viewModel.getLoginUIData(Constants.CUSTOMERID)

//        var url = "http://img.mukewang.com/5ba60788000150aa06860936.png"
//        var width = DensityUtils.getScreenWidth(context!!)
//        var height = resources.getDimension(R.dimen.dp_150).toInt()
//        FrescoDraweeController
//                .loadImage(loginregister_bg , width , height, url , this )
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_login_register
    }

    override fun interceptBackPressed(): Boolean {
        return false
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when(v!!.id){
            R.id.header_left_image->{
                closeActivity()
            }
            R.id.loginregister_sendcode->{
                sendCode()
            }
            R.id.loginregister_login->{
                login()
            }
            R.id.loginregister_validatecode_picture->{
                viewModel.getPictureCode()
            }
            R.id.loginregister_protocal->{
                isAgress =!isAgress
                loginregister_protocal.setImageResource( if(isAgress) R.mipmap.da_gou else R.mipmap.da_bugou)
            }
            R.id.loginregister_protocal_text->{
                seeProtocal()
            }
            R.id.loginregister_markdown_lay->{
                loginregister_markdown_lay.visibility=View.GONE
            }
        }
    }


    private fun seeProtocal(){
        //toast("totoddddd")
        viewModel.getRegisterContent()
    }

    private fun login(){
        var phone = loginRegister_phone.text.toString().trim()
        if(TextUtils.isEmpty(phone)){
            loginRegister_phone.requestFocus()
            KeybordUtils.openKeybord(context!!,loginRegister_phone)
            toast("请输入手机号码")
            return
        }
        var isphone =MobileUtils.isPhone(phone)
        if(!isphone){
            loginRegister_phone.requestFocus()
            KeybordUtils.openKeybord(context!!,loginRegister_phone)
            toast("请输入正确的手机号码")
            return
        }
        var smscode = loginregister_smscode.text.toString().trim()
        if(TextUtils.isEmpty(smscode)){
            loginregister_validatecode.requestFocus()
            KeybordUtils.openKeybord(context!!,loginregister_smscode)
            toast("请输入短信验证码")
            return
        }

        var validateCode = loginregister_validatecode.text.toString().trim()
        if(TextUtils.isEmpty(validateCode)){
            loginregister_validatecode.requestFocus()
            KeybordUtils.openKeybord(context!!,loginregister_validatecode)
            toast("请输入图文验证码")
            return
        }

        viewModel.login(phone, smscode , validateCode,Constants.CUSTOMERID)
    }

    private fun sendCode(){
        var phone = loginRegister_phone.text.toString().trim()
        if(TextUtils.isEmpty(phone)){
            loginRegister_phone.requestFocus()
            KeybordUtils.openKeybord(context!!,loginRegister_phone)
            toast("请输入手机号码")
            return
        }
        var isphone =MobileUtils.isPhone(phone)
        if(!isphone){
            loginRegister_phone.requestFocus()
            KeybordUtils.openKeybord(context!!,loginRegister_phone)
            toast("请输入正确的手机号码")
            return
        }
        var validateCode = loginregister_validatecode.text.toString().trim()
        if(TextUtils.isEmpty(validateCode)){
            loginregister_validatecode.requestFocus()
            KeybordUtils.openKeybord(context!!,loginregister_validatecode)
            toast("请输入图文验证码")
            return
        }

        viewModel.sendCode(phone, validateCode , Constants.CUSTOMERID , safeKey )
    }

    override fun onEnd(cv: CountdownView?) {
        loginregister_countdown_lay.visibility = View.GONE
        loginregister_sendcode.visibility = View.VISIBLE
    }

    override fun getLoginUIDataCallback(apiResult: ApiResult<LoginUIBean>) {
        if(apiResult.code!= ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.message)
            return
        }
        if( apiResult.data==null){
            return
        }


        var buttonColor= apiResult.data!!.ButtonColor
        var bannerColor = apiResult.data!!.BannerColor
        loginregister_login.setBackgroundColor( Color.parseColor(buttonColor) )
        loginregister_container.setBackgroundColor( Color.parseColor( bannerColor ) )

        var url = apiResult.data!!.BannerURL

        if( !url.toLowerCase().startsWith("http://")){
            url = "http://$url"
        }

        var width = DensityUtils.getScreenWidth(context!!)
        var height = resources.getDimension(R.dimen.dp_150).toInt()
        FrescoDraweeController
                .loadImage(loginregister_bg , width , height, url , this )
    }

    override fun sendCodeCallback(apiResult: ApiResult<String?>) {
        if(apiResult.code!= ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.message)
            return
        }

        loginregister_sendcode.visibility = View.GONE
        loginregister_countdown_lay.visibility = View.VISIBLE
        loginregister_countdown.start( delay )
    }

    override fun loginCallback(apiResult: ApiResult<UserBean>) {
        Variable.UserBean = null
        if( apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.message)
            return
        }
        if(apiResult.data==null){
            return
        }

        Variable.UserBean = apiResult.data
        var json = GsonUtils.toJsonString( Variable.UserBean as Any )
        SPUtils.getInstance(BaseApplication.instance!!,Constants.PREF_FILENAME)
                .writeString( Constants.PREF_USER , json )

        if(closeListener!=null){
            closeListener!!.onOpen( FragmentType.Index.code)
        }
    }

    override fun getPictureCodecallback(apiResult: ApiResult<VerifyCodeBean?>) {
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.message)
            return
        }
        if(apiResult.data==null) return

        safeKey = apiResult.data!!.SafeKey

        //var url = apiResult.data!!
        //var width = resources.getDimension(R.dimen.dp_100).toInt()
        //var height =resources.getDimension(R.dimen.dp_30).toInt()

        loginregister_validatecode_picture.setImageBitmap( apiResult.data!!.imageBitmap )

//        FrescoDraweeController.loadImage(
//                loginregister_validatecode_picture , width , height , url , this )

    }

    override fun imageFailure(width: Int, height: Int, simpleDraweeView: SimpleDraweeView) {

    }

    override fun imageCallback(width: Int, height: Int, simpleDraweeView: SimpleDraweeView) {
        simpleDraweeView.layoutParams.width=width
        simpleDraweeView.layoutParams.height = height
    }

    override fun getRegisterContentCallback(apiResult: ApiResult<ProtocalBean?>) {
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.message)
            return
        }
        if(apiResult.data==null) return

        loginregister_markdown_lay.visibility=View.VISIBLE
        loginregister_markdown.loadMarkdown( apiResult.data!!.AgreementContent )

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginRegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                LoginRegisterFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }
}
