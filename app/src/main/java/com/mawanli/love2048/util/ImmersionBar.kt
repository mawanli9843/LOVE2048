package com.mawanli.love2048.util

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.core.graphics.ColorUtils

/**
 * Created by wander on 2017/3/23.
 */

object ImmersionBar {
    private const val STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height"
    private const val NAV_BAR_HEIGHT_RES_NAME = "navigation_bar_height"
    private const val NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME = "navigation_bar_height_landscape"
    private const val NAV_BAR_WIDTH_RES_NAME = "navigation_bar_width"

    @ColorInt
    var statusBarColor = Color.TRANSPARENT //状态栏颜色

    fun getStatusBarHeight(resources: Resources): Int {
        return getInternalDimensionSize(resources, STATUS_BAR_HEIGHT_RES_NAME)
    }

    private fun getInternalDimensionSize(res: Resources, key: String): Int {
        var result = 0
        //key就是status_bar_height
        try {
            val resourceId = res.getIdentifier(key, "dimen", "android")
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId)
            }
        } catch (e: Exception) {

        }
        return result
    }

    /**
     * 隐藏悬浮的状态栏和导航栏
     */
    fun hideFloatBar(mActivity: Activity) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && SystemBarUtil.hasNotchAtVoio(mActivity)) {
//            initBar(mActivity)
//        } else {
//            SystemBarUtil.hideSystemBar(mActivity)
//        }
    }

    /**
     * 设置状态栏透明
     * 注意调整布局适应状态栏高度
     * @param isDarkFont 默认为白色字体
     */
    @JvmOverloads
    fun initBar(mActivity: Activity?, isDarkFont: Boolean = false) {
        val mWindow = mActivity?.window ?: return
        initBar(mWindow, isDarkFont)
    }

    /**
     * 设置状态栏透明
     * 注意调整布局适应状态栏高度
     * @param isDarkFont 默认为黑色字体
     */
    @JvmOverloads
    fun initBar(mWindow: Window, isDarkFont: Boolean = true) {
        try {
            initBarAboveLOLLIPOP(mWindow, isDarkFont)
        } catch (e: Exception) {
        }
    }

    /**
     * 初始化android 5.0以上状态栏和导航栏
     */
    private fun initBarAboveLOLLIPOP(mWindow: Window, isDarkFont: Boolean) {
        var uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE  //防止系统栏隐藏时内容区域大小发生变化
        uiFlags =
            uiFlags or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  //Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态栏遮住。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setBarColor(mWindow, statusBarColor, 0.0f)
        } else {
            setBarColor(mWindow, statusBarColor, 0.3f)
        }
        uiFlags = setStatusBarDarkFont(uiFlags, isDarkFont)
        uiFlags = uiFlags or View.SYSTEM_UI_FLAG_VISIBLE
        mWindow.decorView.systemUiVisibility = uiFlags
    }

    fun setBarColor(mWindow: Window, @ColorInt statusBarColor: Int, statusBarAlpha: Float) {
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)  //需要设置这个才能设置状态栏颜色
        mWindow.statusBarColor = ColorUtils.blendARGB(statusBarColor, Color.BLACK, statusBarAlpha)  //设置状态栏颜色
    }

    fun setBarColor(
        mWindow: Window,
        @ColorInt statusBarColor: Int,
        @ColorInt statusBarEndColor: Int,
        statusBarAlpha: Float
    ) {
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)  //需要设置这个才能设置状态栏颜色
        mWindow.statusBarColor = ColorUtils.blendARGB(statusBarColor, statusBarEndColor, statusBarAlpha)  //设置状态栏颜色
    }

    /**
     * Sets status bar dark font.
     * 设置状态栏字体颜色，android6.0以上
     *
     * @param isDarkFont 是否为暗色字体
     */
    private fun setStatusBarDarkFont(uiFlags: Int, isDarkFont: Boolean): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isDarkFont) {
            uiFlags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            uiFlags
        }
    }

    /**
     * 修改导航栏颜色 颜色渐变扩展
     *
     * @param statusBarAlpha 0 - 1 1为全黑
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun setNavBarColor(mWindow: Window, @ColorInt navigationBarColor: Int, statusBarAlpha: Float) {
        val mContext = mWindow.context
        if (getNavigationBarHeight(mContext) > 0) {
            mWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }
        mWindow.navigationBarColor = ColorUtils.blendARGB(navigationBarColor, Color.BLACK, statusBarAlpha)  //设置导航栏颜色
    }


    fun getNavigationBarHeight(context: Context): Int {
        val res = context.resources
        val result = 0
        if (hasNavBar(context as Activity)) {
            val key: String
            val mInPortrait = res.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
            if (mInPortrait) {
                key = NAV_BAR_HEIGHT_RES_NAME
            } else {
                key = NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME
            }
            return getInternalDimensionSize(res, key)
        }
        return result
    }

    private fun getNavigationBarWidth(context: Context): Int {
        val res = context.resources
        val result = 0
        return if (hasNavBar(context as Activity)) {
            getInternalDimensionSize(res, NAV_BAR_WIDTH_RES_NAME)
        } else result
    }

    private fun hasNavBar(activity: Activity): Boolean {
        val windowManager = activity.windowManager
        val d = windowManager.defaultDisplay

        val realDisplayMetrics = DisplayMetrics()
        d.getRealMetrics(realDisplayMetrics)

        val realHeight = realDisplayMetrics.heightPixels
        val realWidth = realDisplayMetrics.widthPixels

        val displayMetrics = DisplayMetrics()
        d.getMetrics(displayMetrics)

        val displayHeight = displayMetrics.heightPixels
        val displayWidth = displayMetrics.widthPixels

        return realWidth - displayWidth > 0 || realHeight - displayHeight > 0
    }
}
