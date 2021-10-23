package com.aisier.bean

data class User(
        val admin: Boolean?,
        val chapterTops: List<Any>?,
        val email: String?,
        val icon: String?,
        val id: Int?,
        val nickname: String?,
        val publicName: String?,
        val username: String?
)