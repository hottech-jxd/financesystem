package com.cunyn.android.financesystem.util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.guoxintaiyi.android.missionwallet.base.FragmentBackPressedListener


object BackHandlerHelper {
    /**
     * 将back事件分发给 FragmentManager 中管理的子Fragment，如果该 FragmentManager 中的所有Fragment都
     * 没有处理back事件，则尝试 FragmentManager.popBackStack()
     *
     * @return 如果处理了back键则返回 **true**
     * @see .handleBackPress
     * @see .handleBackPress
     */
    fun handleBackPress(fragmentManager: FragmentManager): Boolean {
        val fragments = fragmentManager.fragments ?: return false

        for (i in fragments.indices.reversed()) {
            val child = fragments[i]

            if (isFragmentBackHandled(child)) {
                return true
            }
        }

        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
            return true
        }
        return false
    }

    /**
     * 将back事件分发给Fragment中的子Fragment,
     * 该方法调用了 [.handleBackPress]
     *
     * @return 如果处理了back键则返回 **true**
     */
    fun handleBackPress(fragment: Fragment): Boolean {
        return handleBackPress(fragment.childFragmentManager )
    }

    /**
     * 将back事件分发给Activity中的子Fragment,
     * 该方法调用了 [.handleBackPress]
     *
     * @return 如果处理了back键则返回 **true**
     */
    fun handleBackPress(fragmentActivity: FragmentActivity): Boolean {
        return handleBackPress(fragmentActivity.supportFragmentManager)
    }

    /**
     * 将back事件分发给ViewPager中的Fragment,[.handleBackPress] 已经实现了对ViewPager的支持，所以自行决定是否使用该方法
     *
     * @return 如果处理了back键则返回 **true**
     * @see .handleBackPress
     * @see .handleBackPress
     * @see .handleBackPress
     */
    fun handleBackPress(viewPager: ViewPager?): Boolean {
        if (viewPager == null) return false

        val adapter = viewPager.adapter ?: return false

        val currentItem = viewPager.currentItem
        val fragment: Fragment?
        if (adapter is FragmentPagerAdapter) {
            fragment = (adapter as FragmentPagerAdapter).getItem(currentItem)
        } else if (adapter is FragmentStatePagerAdapter) {
            fragment = (adapter as FragmentStatePagerAdapter).getItem(currentItem)
        } else {
            fragment = null
        }
        return isFragmentBackHandled(fragment)
    }

    /**
     * 判断Fragment是否处理了Back键
     *
     * @return 如果处理了back键则返回 **true**
     */
    fun isFragmentBackHandled(fragment: Fragment?): Boolean {
        return (fragment != null
                && fragment.isVisible
                && fragment.userVisibleHint //for ViewPager

                && fragment is FragmentBackPressedListener
                && (fragment as FragmentBackPressedListener).onBackPressed())
    }
}