package com.aisier.bean

class WrapperTestBean(bean: TestBean) : SDKBean<TestBean>(bean) {

    val showName = "名字：" + bean.name

    val isShow: String = if (bean.visible == 1) "展示" else "隐藏"

    override fun toString(): String {
        return "WrapperTestBean(showName='$showName', isShow='$isShow')"
    }
}