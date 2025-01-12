package com.example.potel.ui.forumZone
import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

fun String.toFormattedDate(): String {
    return try {
        val originalFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        originalFormatter.timeZone = TimeZone.getTimeZone("Asia/Taipei")
        val postDate = originalFormatter.parse(this)
        if (postDate != null) {
            val currentDateTime = System.currentTimeMillis()
            val timeDifference = currentDateTime - postDate.time

            if (timeDifference < DateUtils.DAY_IN_MILLIS) {
                DateUtils.getRelativeTimeSpanString(postDate.time, currentDateTime, DateUtils.MINUTE_IN_MILLIS).toString()
            } else {
                val dateOnlyFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                dateOnlyFormatter.timeZone = TimeZone.getTimeZone("Asia/Taipei")  // 確保時間格式化使用相同時區
                return dateOnlyFormatter.format(postDate)
            }
        } else {
            "日期格式錯誤"
        }
    } catch (e: Exception) {
        "日期格式錯誤"
    }
}