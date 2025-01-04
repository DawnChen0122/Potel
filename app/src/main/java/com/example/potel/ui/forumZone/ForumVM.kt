package com.example.potel.ui.forumZone

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ForumVM : ViewModel(){
    private val TAG = "tag_ForumVM"
    private val _postSelectedState = MutableStateFlow(Post())
    val postSelectedState: StateFlow<Post> = _postSelectedState.asStateFlow()

    fun setSelectedPost(post: Post) {
        _postSelectedState.value = post
    }

    // 用來監控所有論壇資料，當資料變動時通知 UI 更新
    private var _forumsState = MutableStateFlow(emptyList<Post>())
    val forumsState: StateFlow<List<Post>> = _forumsState.asStateFlow()

    private val _likeCountState = MutableStateFlow(emptyList<Like>())
    val likeCountState: StateFlow<List<Like>> = _likeCountState.asStateFlow()

    private val _commentsState = MutableStateFlow(emptyList<Comment>())
    val commentsState: StateFlow<List<Comment>> = _commentsState.asStateFlow()


    init {
        // 在 viewModelScope 中啟動協程以呼叫 suspend 函式
        viewModelScope.launch {
            fetchForumData()
        }
    }

    private suspend fun fetchForumData() {
        try {
            val forums = RetrofitInstance.api.fetchForums()
            _forumsState.value = forums

            val likes = RetrofitInstance.api.fetchAllLikes()
            _likeCountState.value = likes

            Log.d(TAG, "Forums: $forums")
            Log.d(TAG, "Likes: $likes")
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching forum data: ${e.message}")
        }
    }

    /* 計算每個帖子對應的點讚數量 */
    fun getLikesCountForPost(postId: Int): Int {
        return _likeCountState.value.count { it.postId == postId }
    }
    // 新增一個新的論壇貼文
    fun addPost(post: Post) {
        viewModelScope.launch {
            try {
                // 使用 Retrofit 發送新增貼文的 API 請求
                val response = RetrofitInstance.api.addPost(post)
                if (response.isSuccessful) {
                    Log.d(TAG, "Post added successfully: ${response.body()}")
                    // 在新增成功後更新 _forumsState，將新貼文加入現有的列表
                    _forumsState.value += response.body()!!
                } else {
                    Log.e(TAG, "Error adding post: Code ${response.code()}, Body: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error adding post: ${e.message}")
            }
        }
    }
}


