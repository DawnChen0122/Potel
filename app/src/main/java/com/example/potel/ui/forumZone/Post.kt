package com.example.potel.ui.forumZone

data class Post(
    val postId: Int=0, // 設為可選，默認為 null
    val memberId: Int = 0,
    val title: String = "",
    val content: String = "",
    val createDate: String = "",
    val modifyDate: String? = null,
    val ImageId: Int? = null
)
data class NewPost(
    val memberId: Int = 0,
    val title: String = "",
    val content: String = "",
)

data class Like(
    val likeId: Int = 0,
    val memberId: Int = 0,
    val postId: Int = 0,
    val createDate: String = ""
)

data class Comment(
    val commentId: Int = 0,
    val postId: Int = 0,
    val memberId: Int = 0,
    val content: String = "",
    val createDate: String = "",
    val modifyDate: String? = null
)

data class NewComment(
    val postId: Int = 0,
    val memberId: Int = 0,
    val content: String = ""
)



