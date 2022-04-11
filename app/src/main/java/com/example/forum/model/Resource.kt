package com.example.forum.model

import okhttp3.ResponseBody

sealed class Resource<out T> {
    //https://stackoverflow.com/questions/68917967/how-to-handle-error-response-return-from-server-api-in-kotlinretrofitcoroutine
    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : Resource<Nothing>()
}