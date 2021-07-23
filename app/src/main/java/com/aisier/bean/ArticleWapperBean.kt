package com.aisier.bean

class ArticleWrapperBean(bean: WxArticleBean) : SDKBean<WxArticleBean>(bean) {

    val showName = "名字：" + bean.name

    val isShow: String = if (bean.visible == 1) "展示" else "隐藏"

    override fun toString(): String {
        return "ArticleWrapperBean(showName='$showName', isShow='$isShow')"
    }
}