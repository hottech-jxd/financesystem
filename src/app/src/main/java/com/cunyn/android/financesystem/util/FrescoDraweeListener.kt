package com.guoxintaiyi.android.missionwallet.util

import android.graphics.drawable.Animatable
import android.view.ViewGroup
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.image.ImageInfo
import java.lang.ref.WeakReference

/**
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼在地图中查看
 *
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2017.year. All rights reserved.
 *
 *
 * Created by jinxiangdong on 2017/11/17.
 */
class FrescoDraweeListener(iv: SimpleDraweeView
                           , internal var width: Int
                           , internal var height: Int)
    : BaseControllerListener<ImageInfo>() {
    internal var ref: WeakReference<SimpleDraweeView>? = null
    internal var imageCallback: ImageCallback? = null

    interface ImageCallback {
        fun imageCallback(width: Int, height: Int, simpleDraweeView: SimpleDraweeView)
        fun imageFailure(width: Int, height: Int, simpleDraweeView: SimpleDraweeView)
    }

    fun setImageCallback(imageCallback: ImageCallback) {
        this.imageCallback = imageCallback
    }

    init {
        this.ref = WeakReference(iv)
    }

    override fun onFinalImageSet(id: String?, imageInfo: ImageInfo?, animatable: Animatable?) {
        super.onFinalImageSet(id, imageInfo, animatable)
        if (imageInfo == null) return
        if (ref!!.get() == null) return

        val h = imageInfo.height
        val w = imageInfo.width

        val ivw = width
        val ivh = h * ivw / w
        val layoutParams = ref!!.get()!!.getLayoutParams()
        layoutParams.width = ivw
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT

        ref!!.get()!!.setLayoutParams(layoutParams)

        val ratio = w * 1.0f / h
        ref!!.get()!!.setAspectRatio(ratio)

        if (imageCallback != null) {
            imageCallback!!.imageCallback(ivw, ivh, ref!!.get()!!)
        }

    }

    override fun onFailure(id: String?, throwable: Throwable?) {
        super.onFailure(id, throwable)
        if (ref!!.get() == null) return

        val layoutParams = ref!!.get()!!.getLayoutParams()
        layoutParams.width = width
        layoutParams.height = height
        ref!!.get()!!.setLayoutParams(layoutParams)
        //float ratio = 1.0f;
        //ref.get().setAspectRatio(ratio);

        if (imageCallback != null) {
            imageCallback!!.imageFailure(width, height, ref!!.get()!!)
        }

    }

}
