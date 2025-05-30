package com.example.testinicial.data.remote.session

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class SessionCookieJar : CookieJar {
    private val cookieStore: HashMap<String, List<Cookie>> = HashMap()

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookieStore[url.host] = cookies
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookieStore[url.host] ?: listOf()
    }
}
