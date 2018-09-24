package com.guoxintaiyi.android.missionwallet.util

import android.net.Uri
import android.text.TextUtils

import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder

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
object FrescoDraweeController {

    fun loadImage(simpleDraweeView: SimpleDraweeView, width: Int, height: Int, url: String) {
        if (TextUtils.isEmpty(url)) return
        val resizeOptions = ResizeOptions(width, height)

        val imageRequest = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(url))
                .setResizeOptions(resizeOptions)
                .build()

        val draweeController = Fresco.newDraweeControllerBuilder()
                .setOldController(simpleDraweeView.controller)
                .setImageRequest(imageRequest)
                .setTapToRetryEnabled(true)
                .build()

        simpleDraweeView.controller = draweeController
    }

    fun loadImage(simpleDraweeView: SimpleDraweeView, width: Int, height: Int, url: String, imageCallback: FrescoDraweeListener.ImageCallback) {
        val listener = FrescoDraweeListener(simpleDraweeView, width, height)
        listener.setImageCallback(imageCallback)
        val draweeController = Fresco
                .newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                .setTapToRetryEnabled(false)
                .setUri(url)
                .setOldController(simpleDraweeView.controller)
                .setControllerListener(listener)
                .build()
        simpleDraweeView.controller = draweeController
    }

}
