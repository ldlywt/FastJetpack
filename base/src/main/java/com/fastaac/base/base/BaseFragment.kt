package com.fastaac.base.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.ToastUtils

/**
 * author : wutao
 * e-mail : 670831931@qq.com
 * time   : 2019/05/17
 * desc   :
 * version: 1.0
 */
abstract class BaseFragment : Fragment {
    protected var mActivity: FragmentActivity? = null
    protected var mIsFirstVisible = true

    constructor()
    constructor(contentLayoutId: Int) : super(contentLayoutId)

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            onVisible()
        } else {
            onInVisible()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            onVisible()
        } else {
            onInVisible()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as FragmentActivity
    }

    protected fun retryClick() {
        ToastUtils.showShort("重新请求")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mActivity = null
    }

    override fun onDetach() {
        super.onDetach()
        mActivity = null
    }

    private fun needLazy() {
        val isVis = isHidden || userVisibleHint
        if (isVis && mIsFirstVisible) {
            lazyLoad()
            mIsFirstVisible = false
        }
    }

    /**
     * 当界面可见时的操作
     */
    protected fun onVisible() {
        if (mIsFirstVisible && isResumed) {
            lazyLoad()
            mIsFirstVisible = false
        }
    }

    /**
     * 当界面不可见时的操作
     */
    protected fun onInVisible() {}

    /**
     * 数据懒加载
     */
    protected fun lazyLoad() {}

    /**
     * 跳转到其他 Activity 并销毁当前 Activity
     *
     * @param cls 目标Activity的Class
     */
    fun startActivityFinish(cls: Class<out Activity?>?) {
        startActivity(cls)
        mActivity!!.finish()
    }

    /**
     * 跳转到其他Activity
     *
     * @param cls 目标Activity的Class
     */
    fun startActivity(cls: Class<out Activity?>?) {
        startActivity(Intent(context, cls))
    }
}