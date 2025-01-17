package com.example.potel.ui.forumZone

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class ForumVM : ViewModel() {
    private val tag = "tag_ForumVM"

    // 文章相關狀態
    private val _postSelectedState = MutableStateFlow(Post())
    val postSelectedState: StateFlow<Post> = _postSelectedState.asStateFlow()

    private val _postSelectedCommentsList = MutableStateFlow(emptyList<Comment>())
    val postSelectedCommentsList: StateFlow<List<Comment>> = _postSelectedCommentsList.asStateFlow()

    private val _commentSelectedState = MutableStateFlow(Comment())
    val commentSelectedState: StateFlow<Comment> = _commentSelectedState.asStateFlow()

    private val _forumsState = MutableStateFlow(emptyList<Post>())
    val forumsState: StateFlow<List<Post>> = _forumsState.asStateFlow()

    private val _likeCountState = MutableStateFlow(emptyList<Like>())
//    val likeCountState: StateFlow<List<Like>> = _likeCountState.asStateFlow()

    private val _commentsState = MutableStateFlow(emptyList<Comment>())
//    val commentsState: StateFlow<List<Comment>> = _commentsState.asStateFlow()

    private val _isItemsVisible = MutableStateFlow(true)
    val isItemsVisible: StateFlow<Boolean> = _isItemsVisible

    var postSuccessMessage = mutableStateOf<String?>(null)
        private set

    init {
        // 初始化資料載入
        viewModelScope.launch {
            fetchCommentData()
            fetchLikeData()
            fetchForumData()
        }
    }

    // 更新選中貼文和其留言
    fun setSelectedPost(post: Post) {
        _postSelectedState.value = post
        _postSelectedCommentsList.value = _commentsState.value.filter { it.postId == post.postId }
    }

    fun setSelectedComment(comment: Comment){
        Log.d(tag, "Setting selected comment: $comment")
        _commentSelectedState.value = comment
    }
    // 獲取所有論壇資料
    private suspend fun fetchForumData() {
        try {
            val forums = RetrofitInstance.api.fetchForums()
            _forumsState.value = forums
            Log.d(tag, "Forums: $forums")
        } catch (e: Exception) {
            Log.e(tag, "Error fetching forum data: ${e.message}")
        }
    }

    // 獲取所有留言資料
    private suspend fun fetchCommentData() {
        try {
            val comments = RetrofitInstance.api.fetchComments()
            _commentsState.value = comments

            // 根據當前選中的貼文 ID 過濾留言
            _postSelectedCommentsList.value =
                comments.filter { it.postId == _postSelectedState.value.postId }
            Log.d(tag, "Comments: $comments")
        } catch (e: Exception) {
            Log.e(tag, "Error fetching comment data: ${e.message}")
        }
    }

    // 獲取所有按讚資料
    private suspend fun fetchLikeData() {
        try {
            val likes = RetrofitInstance.api.fetchAllLikes()
            _likeCountState.value = likes
            Log.d(tag, "Likes: $likes")
        } catch (e: Exception) {
            Log.e(tag, "Error fetching like data: ${e.message}")
        }
    }

    // 新增論壇貼文
    fun addPost(post: NewPost, imagePart: MultipartBody.Part?) {
        viewModelScope.launch {
            try {
                val memberIdPart =
                    post.memberId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val titlePart = post.title.toRequestBody("text/plain".toMediaTypeOrNull())
                val contentPart = post.content.toRequestBody("text/plain".toMediaTypeOrNull())

                val response = RetrofitInstance.api.addPost(
                    memberId = memberIdPart,
                    title = titlePart,
                    content = contentPart,
                    image = imagePart
                )

                if (response.isSuccessful) {
                    Log.d(tag, "Post added successfully: ${response.body()}")
                    fetchForumData()  // 重新加載論壇資料
                } else {
                    Log.e(tag, "Error adding post: Code ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(tag, "Error adding post: ${e.message}")
            }
        }
    }

    fun getCommentCountForPost(postId: Int): Int {
        return _commentsState.value.count { it.postId == postId }
    }

    // 新增留言
    fun addComment(newComment: NewComment) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.addComment(newComment)

                if (response.isSuccessful) {
                    Log.d(tag, "Comment added successfully: ${response.body()}")
                    fetchCommentData()  // 重新載入留言資料
                } else {
                    Log.e(tag, "Error adding comment: Code ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(tag, "Error adding comment: ${e.message}")
            }
        }
    }

    // 刪除貼文
    fun deletePost(postId: Int) {
        viewModelScope.launch {
            try {
                val deletePostResponse = RetrofitInstance.api.deletePost(postId)

                if (deletePostResponse.isSuccessful) {
                    // 刪除貼文和留言
                    _forumsState.update { forums -> forums.filterNot { it.postId == postId } }
                    _commentsState.update { comments -> comments.filterNot { it.postId == postId } }
                    Log.d(tag, "Post and its comments deleted successfully")
                } else {
                    Log.e(tag, "Error deleting post: Code ${deletePostResponse.code()}")
                }
            } catch (e: Exception) {
                Log.e(tag, "Error deleting post and comments: ${e.message}")
            }
        }
    }

    fun deleteComment(commentId: Int) {
        viewModelScope.launch {
            try {
                val deletePostResponse = RetrofitInstance.api.deleteComment(commentId)

                if (deletePostResponse.isSuccessful) {
                    // 刪除留言
                    _postSelectedCommentsList.value = _postSelectedCommentsList.value.filterNot { it.commentId == commentId }
                    Log.d(tag, "comment deleted successfully")
                } else {
                    Log.e(tag, "Error deleting comment: Code ${deletePostResponse.code()}")
                }
            } catch (e: Exception) {
                Log.e(tag, "Error deleting comment: ${e.message}")
            }
        }
    }

    fun updatePostWithImage(post: Post, imagePart: MultipartBody.Part?) {
        viewModelScope.launch {
            try {
                val postIdPart =
                    post.postId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val titlePart = post.title.toRequestBody("text/plain".toMediaTypeOrNull())
                val contentPart = post.content.toRequestBody("text/plain".toMediaTypeOrNull())

                val response = RetrofitInstance.api.updatePostWithImage(
                    postId = postIdPart,
                    title = titlePart,
                    content = contentPart,
                    image = imagePart
                )

                if (response.isSuccessful) {
                    Log.d(tag, "updatePostWithImage successfully: ${response.body()}")
                    fetchForumData()
                } else {
                    Log.e(tag, "Error updatePostWithImage Code ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(tag, "Error updatePostWithImage: ${e.message}")
            }
        }
    }

    fun updatePost(post: Post) {
        viewModelScope.launch {
            try {
                val postUpdateRequest = PostUpdateRequest(postId = post.postId, title = post.title, content = post.content)
                val response = RetrofitInstance.api.updatePost(postUpdateRequest)
                if (response.isSuccessful) {
                    Log.d(tag, "updatePost successfully: ${response.body()}")
                    fetchForumData()
                } else {
                    Log.e(tag, "Error updatePost: Code ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(tag, "Error updatePost: ${e.message}")
            }
        }
    }

    fun updateComment(comment: Comment) {
        viewModelScope.launch {
            try {
                val commentUpdateRequest = CommentUpdateRequest(commentId = comment.commentId, content = comment.content)
                val response = RetrofitInstance.api.updateComment(commentUpdateRequest)
                if (response.isSuccessful) {
                    Log.d(tag, "updateComment successfully: ${response.body()}")
                    fetchCommentData()  // 重新加載論壇資料
                } else {
                    Log.e(tag, "Error updateComment: Code ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(tag, "Error updateComment: ${e.message}")
            }
        }
    }

    fun getLikesCountForPost(postId: Int): Int {
        return _likeCountState.value.count { it.postId == postId }
    }

    fun isPostLikedByMember(postId: Int, memberId: Int): Boolean {
        return _likeCountState.value.any { it.postId == postId && it.memberId == memberId }
    }

    fun unLike(postId: Int, memberId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.unlikePost(postId, memberId)
                if (response.isSuccessful) {
                    Log.d(tag, "Post unliked successfully")
                    fetchLikeData()
                } else {
                    Log.e(tag, "Error unliking post: Code ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(tag, "Error unliking post: ${e.message}")
            }
        }
    }

    fun like(postId: Int, memberId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.likePost(postId, memberId)
                if (response.isSuccessful) {
                    Log.d(tag, "Post liked successfully")
                    fetchLikeData()
                } else {
                    Log.e(tag, "Error liking post: Code ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(tag, "Error liking post: ${e.message}")
            }
        }

    }

    fun refresh(){
        viewModelScope.launch {
            delay(1000)
            fetchCommentData()
            fetchLikeData()
            fetchForumData()
        }
    }

    fun setPostSuccessMessage(message: String?) {
        postSuccessMessage.value = message
    }

    fun setItemsVisibility(isVisible: Boolean) {
        _isItemsVisible.value = isVisible
    }
}





