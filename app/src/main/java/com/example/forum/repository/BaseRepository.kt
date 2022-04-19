package com.example.forum.repository

import com.example.forum.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.net.ssl.SSLHandshakeException

open class BaseRepository {
    //https://stackoverflow.com/questions/68917967/how-to-handle-error-response-return-from-server-api-in-kotlinretrofitcoroutine
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ) : Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (t: Throwable) {
                when (t) {
                    is HttpException -> Resource.Failure(false, t.code(), t.response()?.errorBody())
                    is SSLHandshakeException -> Resource.Success(apiCall.invoke())
                    else -> Resource.Failure(true, null, null)
                }
            }
        }
    }
}