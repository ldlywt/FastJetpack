package com.aisier

/**
 * author : wutao
 * e-mail : wutao@himango.cn
 * time   : 2019/08/19
 * desc   :
 * version: 1.0
 */
class TestBean {
    /**
     * children : []
     * courseId : 13
     * id : 408
     * name : 鸿洋
     * order : 190000
     * parentChapterId : 407
     * userControlSetTop : false
     * visible : 1
     */
    var courseId = 0
    var id = 0
    var name: String? = null
    var order = 0
    var parentChapterId = 0
    var isUserControlSetTop = false
    var visible = 0
    var children: List<*>? = null

    override fun toString(): String {
        return "TestBean{" +
                "courseId=" + courseId +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", order=" + order +
                ", parentChapterId=" + parentChapterId +
                ", userControlSetTop=" + isUserControlSetTop +
                ", visible=" + visible +
                ", children=" + children +
                '}'
    }
}