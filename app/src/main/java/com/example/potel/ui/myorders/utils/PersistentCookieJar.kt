package com.example.potel.ui.myorders.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

// 將取得的cookie存入Shared-Preference, 這樣可以在app重啟後繼續使用
class PersistentCookieJar(context: Context) : CookieJar {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("cookie_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        val domain = url.host
        val existingCookies = loadCookiesForDomain(domain).toMutableList()
        existingCookies.addAll(cookies)
        saveCookiesForDomain(domain, existingCookies)
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return loadCookiesForDomain(url.host).filter { !it.hasExpired() }
    }

    private fun Cookie.hasExpired(): Boolean {
        return this.expiresAt < System.currentTimeMillis()
    }

    private fun saveCookiesForDomain(domain: String, cookies: List<Cookie>) {
        val cookiesJson = gson.toJson(cookies)
        sharedPreferences.edit().putString(domain, cookiesJson).apply()
    }

    private fun loadCookiesForDomain(domain: String): List<Cookie> {
        val cookiesJson = sharedPreferences.getString(domain, null) ?: return emptyList()
        val type = object : TypeToken<List<Cookie>>() {}.type
        return gson.fromJson(cookiesJson, type)
    }
}