package com.example.potel.ui.forumZone

data class Post(
    val postId: Int=0,
    val memberId: Int = 0,
    val title: String = "",
    val content: String = "",
    val createDate: String = "",
    val modifyDate: String? = null,
    val imageId: Int? = null,
    val memberName: String =""
)
data class NewPost(
    val memberId: Int = 0,
    val title: String = "",
    val content: String = "",
)

data class Like(
    val likeId: Int = 0,
    val memberId: Int = 0,
    val postId: Int = 0
)

data class Comment(
    val commentId: Int = 0,
    val postId: Int = 0,
    val memberId: Int = 0,
    val content: String = "",
    val createDate: String = "",
    val modifyDate: String? = null,
    val memberName: String =""
)

data class NewComment(
    val postId: Int = 0,
    val memberId: Int = 0,
    val content: String = ""
)

data class PostUpdateRequest(
    val postId: Int,
    val title: String,
    val content: String
)

data class CommentUpdateRequest(
    val commentId: Int,
    val content: String
)


