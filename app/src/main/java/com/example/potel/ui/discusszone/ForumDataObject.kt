package com.example.potel.ui.discusszone

data class Forum(
    val postId: Int=0,
    val memberId: Int=0,
    val title: String="",
    val content: String="",
    val createDate: String="",
    val modifyDate: String?=null,
    val postImageId: Int?=null
)