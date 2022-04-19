package com.example.forum.api

import android.content.Context
import com.example.forum.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(context: Context): Interceptor {
    private val userPreferences: UserPreferences = UserPreferences(context)
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("Authorization", "Bearer " + runBlocking {
                    userPreferences.authToken.first()
                }).build()
        )
    }
}