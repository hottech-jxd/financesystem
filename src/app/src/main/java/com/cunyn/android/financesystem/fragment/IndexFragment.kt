package com.cunyn.android.financesystem.fragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import com.cunyn.android.financesystem.R
import com.cunyn.android.financesystem.bean.*
import com.cunyn.android.financesystem.mvp.IPresenter
import com.cunyn.android.financesystem.mvp.IndexPresenter
import com.cunyn.android.financesysten.util.DensityUtils
import com.facebook.drawee.view.SimpleDraweeView
import com.guoxintaiyi.android.missionwallet.base.BaseFragment
import com.guoxintaiyi.android.missionwallet.base.OnFragmentEventListener
import com.guoxintaiyi.android.missionwallet.util.FrescoDraweeController
import com.guoxintaiyi.android.missionwallet.util.FrescoDraweeListener
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.fragment_index.*
import kotlinx.android.synthetic.main.layout_header.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [IndexFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class IndexFragment : BaseFragment<IndexContract.Presenter>()
        ,OnFragmentEventListener
,IndexContract.View{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var iPresenter = IndexPresenter(this)
    private var bannerImages =ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onStart() {
        super.onStart()

        if(bannerImages.size>1){
            index_banner.startAutoPlay()
        }else{
            index_banner.stopAutoPlay()
        }
    }

    override fun onStop() {
        super.onStop()
        index_banner.stopAutoPlay()
    }

    override fun initView() {
        header_title.text="首页"

        index_menu1.setOnClickListener(this)
        index_menu2.setOnClickListener(this)
        index_menu3.setOnClickListener(this)
        index_banner.setOnClickListener(this)
        index_apply.setOnClickListener(this)

        iPresenter.getIndexUIData(Constants.CUSTOMERID)

        //index_banner.setImageLoader(FrescoImageLoader(index_banner))
    }

    override fun fetchData() {
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_index
    }

    override fun interceptBackPressed(): Boolean {
        return false
    }

    override fun getIndexUIDataCallback(apiResult: ApiResult<ArrayList<IndexUIBean>>) {
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.message)
            return
        }
        if(apiResult.result==null || apiResult.result!!.size<1)return

        bannerImages.clear()
        for(bean in apiResult.result!!) {
            bannerImages.add(bean.AdImgSrc!!)
        }



        if(bannerImages.size>1){
            index_banner.setIndicatorGravity(BannerConfig.RIGHT)
            index_banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
            index_banner.setImages(bannerImages).setImageLoader(FrescoImageLoader(index_banner))
            index_banner.isAutoPlay(true)
            index_banner.start()
        }else{
            index_banner.setBannerStyle(BannerConfig.NOT_INDICATOR)
            index_banner.setImages(bannerImages).setImageLoader(FrescoImageLoader(index_banner))
            index_banner.isAutoPlay(false)
            index_banner.start()

        }
    }

    override fun showProgress(msg: String) {
        super.showProgress(msg)
        index_progress.visibility=View.VISIBLE
    }


    override fun hideProgress() {
        super.hideProgress()
        index_progress.visibility=View.GONE
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when(v!!.id){
            R.id.index_menu1->{
                var infoAuditionFragment = InfoAuditionFragment.newInstance("","")
                infoAuditionFragment.closeListener=this
                childFragmentManager.beginTransaction()
                        .replace(R.id.index_container , infoAuditionFragment)
                        .addToBackStack(null)
                        .commit()
            }
            R.id.index_menu2->{
                var faqFragment = FaqFragment.newInstance("","")
                faqFragment.closeListener=this
                childFragmentManager.beginTransaction()
                        .replace(R.id.index_container , faqFragment)
                        .addToBackStack(null)
                        .commit()
            }
            R.id.index_menu3->{
                var myFragment = MyFragment.newInstance("","")
                myFragment.closeListener=this
                childFragmentManager.beginTransaction()
                        .replace(R.id.index_container , myFragment)
                        .addToBackStack(null)
                        .commit()
            }
            R.id.index_apply->{
                iPresenter.apply(Variable.UserBean!!.UserId , Constants.CUSTOMERID)
            }
        }
    }

    override fun onOpen(type: Int) {

    }

    override fun onClose() {
        childFragmentManager.popBackStack()
    }

    override fun onClsoeActivity() {

    }

    override fun applyCallback(apiResult: ApiResult<Any?>) {
        if(apiResult.code != ApiResultCodeEnum.SUCCESS.code){
            toast(apiResult.message)
            return
        }

        toast(apiResult.message)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment IndexFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                IndexFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}

class FrescoImageLoader(var banner: Banner): ImageLoader() , FrescoDraweeListener.ImageCallback{

    override fun createImageView(context: Context?): ImageView {
        //return super.createImageView(context)

        var simpleDraweeView = SimpleDraweeView(context)

        return simpleDraweeView
    }

    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        var simpleDraweeView = imageView as SimpleDraweeView

        //simpleDraweeView.setImageURI( path.toString() )
        var width = DensityUtils.getScreenWidth(context!!)
        var height = context!!.resources.getDimension(R.dimen.dp_150).toInt()
        FrescoDraweeController.loadImage(simpleDraweeView
        ,width , height , path.toString() , this    )
    }

    override fun imageFailure(width: Int, height: Int, simpleDraweeView: SimpleDraweeView) {

    }

    override fun imageCallback(width: Int, height: Int, simpleDraweeView: SimpleDraweeView) {
        simpleDraweeView.layoutParams.width = width
        simpleDraweeView.layoutParams.height = height
        banner.layoutParams.height = height
    }
}