package com.cunyn.android.financesystem.widget

import android.content.Context
import android.support.annotation.ColorInt
import com.yanyusong.y_divideritemdecoration.Y_Divider
import com.yanyusong.y_divideritemdecoration.Y_DividerBuilder
import com.yanyusong.y_divideritemdecoration.Y_DividerItemDecoration

class RecyclerViewDividerOneLine(var context: Context
                                 , @ColorInt var dividerColor: Int
                                 , var width:Float
                                 , var leftPaddingDp:Float=0f
                                 , var rightPaddingDp:Float=0f) : Y_DividerItemDecoration(context) {

    override fun getDivider(itemPosition: Int): Y_Divider {

        return Y_DividerBuilder()
                .setBottomSideLine(true, dividerColor, width, leftPaddingDp, rightPaddingDp)
                .create()

    }
}

