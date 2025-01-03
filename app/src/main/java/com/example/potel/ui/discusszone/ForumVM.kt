package com.example.potel.ui.discusszone

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

    private val _likesCountState = MutableStateFlow(emptyList<Likes>())
    val likesCountState: StateFlow<List<Likes>> = _likesCountState.asStateFlow()

    init {
        // 在 viewModelScope 中啟動協程以呼叫 suspend 函式
        viewModelScope.launch {
            fetchForumData()
        }
    }

    /* 取得所有論壇資訊 */
    private suspend fun fetchForumData() {
        try {
            val forums = RetrofitInstance.api.fetchForums()
            _forumsState.value = forums

            val likes = RetrofitInstance.api.fetchAllLikes()
            _likesCountState.value = likes

            Log.d(TAG, "Forums: $forums")
            Log.d(TAG, "Likes: $likes")
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching forum data: ${e.message}")
        }
    }
    /* 計算每個帖子對應的點讚數量 */
    fun getLikesCountForPost(postId: Int): Int {
        return _likesCountState.value.count { it.postId == postId }
    }
    // 新增一個新的論壇貼文
    fun addPost(post: Post) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.addPost(post)
                if (response.isSuccessful) {
                    Log.d(TAG, "Post added successfully: ${response.body()}")
                    // 可以在這裡處理成功後的邏輯，比如刷新論壇列表
                    fetchForumData()
                } else {
                    Log.e(TAG, "Error adding post: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error adding post: ${e.message}")
            }
        }
    }
}


