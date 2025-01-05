package com.example.potel.ui.myorders.utils

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

// 將Cookie存在記憶體, app結束就清除
class MyCookieJar : CookieJar {
    private val cookieStore: MutableMap<String, MutableList<Cookie>> = mutableMapOf()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        // 将 Cookie 存储到 Map 中，以域名为 key
        val domain = url.host
        if (cookieStore[domain] == null) {
            cookieStore[domain] = mutableListOf()
        }
        cookieStore[domain]?.addAll(cookies)
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        // 根据 URL 加载相应的 Cookie
        return cookieStore[url.host]?.filter { cookie ->
            // 过滤掉过期的 Cookie
            !cookie.hasExpired()
        } ?: emptyList()
    }

    // 判断 Cookie 是否已过期
    private fun Cookie.hasExpired(): Boolean {
        return this.expiresAt < System.currentTimeMillis()
    }
}