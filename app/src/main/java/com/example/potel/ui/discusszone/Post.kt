package com.example.potel.ui.discusszone

data class Post(
    val postId: Int = 0,
    val memberId: Int = 0,
    val title: String = "",
    val content: String = "",
    val createDate: String = "",
    val modifyDate: String? = null,
    val postImageId: Int? = null
)

data class Likes(
    val likeId: Int = 0,
    val memberId: Int = 0,
    val postId: Int = 0,
    val createDate: String = ""
)

data class Comments(
    val commentId: Int = 0,
    val postId: Int = 0,
    val memberId: Int = 0,
    val content: String = "",
    val createDate: String = "",
    val modifyDate: String? = null
)