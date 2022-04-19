package com.example.forum.api

import android.content.Context
import com.example.forum.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    private lateinit var apiService: Api
    fun getApi(context: Context): Api {
        if (!::apiService.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClient(context))
                .build()
            apiService = retrofit.create(Api::class.java)
        }
        return apiService
    }

    private fun okhttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()
    }
}