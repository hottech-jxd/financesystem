package com.guoxintaiyi.android.missionwallet.base

import android.content.Context
import android.os.Bundle
import android.provider.SyncStateContract
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.cunyn.android.financesystem.bean.Constants
import com.cunyn.android.financesystem.mvp.IView
import com.cunyn.android.financesystem.util.BackHandlerHelper

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [BaseFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [BaseFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
abstract class BaseFragment<T> : Fragment()
        , IView<T>
        , FragmentBackPressedListener
        , View.OnClickListener {

    var isViewPrepared :Boolean = false
    var isVisibleToUser = false
    var isDataInited = false
    var rootView :View?=null
    var closeListener:OnFragmentEventListener?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDestroyView() {
        super.onDestroyView()

        //isViewPrepared = false
        //rootView = null

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()

        closeListener = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)

        if(rootView !=null){
            if(rootView!!.parent!=null) {
                var parent: ViewGroup? = rootView!!.parent as ViewGroup
                if (parent != null) {
                    parent.removeView(rootView)
                }
            }
            return rootView
        }


        rootView = inflater.inflate(getLayoutResourceId(), container , false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(isViewPrepared) return
        initView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewPrepared=true
        lazyLoadData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        lazyLoadData()
    }

    fun lazyLoadData(){

        if(isViewPrepared && isVisibleToUser && !isDataInited){
            fetchData()
            isDataInited=true
        }
    }

    abstract fun initView()

    abstract fun fetchData()

    abstract fun getLayoutResourceId():Int

    //abstract fun getPageTitle():String?

    override fun showProgress(msg: String) {

    }

    override fun hideProgress() {

    }

    override fun toast(msg: String) {
        Toast.makeText(context ,msg,Toast.LENGTH_LONG).show()
    }

    override fun error(err: String) {
        toast(err)
    }

    override fun showProgress() {
        showProgress(Constants.TIP_LOADING)
    }

    override fun error() {
        toast(Constants.MESSAGE_ERROR)
    }

//   protected fun processCommonErrorCode(apiResult: ApiResult<*>): Boolean {
//        if ( apiResult.code == ApiResultCodeEnum.USER_NO_LOGIN.code
//            || apiResult.code == ApiResultCodeEnum.USER_FREEZE.code
//            || apiResult.code == ApiResultCodeEnum.USER_ILLEGAL.code ) {
//
//            toast( apiResult.msg )
//
//            //newIntent<SplashActivity>()
//            return true
//        }
//        return false
//    }


    override fun onClick(v: View?) {

    }



    override fun onBackPressed(): Boolean {
        return interceptBackPressed() || if (getBackHandleViewPager() == null)
            BackHandlerHelper.handleBackPress(this)
        else
            BackHandlerHelper.handleBackPress(getBackHandleViewPager())
    }

    abstract fun interceptBackPressed(): Boolean

    /**
     * 2.1 版本已经不在需要单独对ViewPager处理
     *
     */
    @Deprecated("")
    fun getBackHandleViewPager(): ViewPager? {
        return null
    }


    fun closeFragment(){
        if(closeListener!=null) closeListener!!.onClose()
    }

    fun closeActivity(){
        if(closeListener!=null) closeListener!!.onClsoeActivity()
    }

}

interface OnStatusBarColorListener{
    fun onStatusBarColor( color:Int)
}

interface OnFragmentEventListener{
    fun onOpen( type:Int )
    fun onClose()
    fun onClsoeActivity()
}

/**
 * 监听 返回 键
 */
interface FragmentBackPressedListener{
    fun onBackPressed():Boolean
}
