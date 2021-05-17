package com.aisier.bean

class UserBean(val name: String, val userId: Long){
    override fun toString(): String {
        return "UserBean(name='$name', userId=$userId)"
    }
}