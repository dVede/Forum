package com.example.forum.api

import com.example.forum.model.*
import okhttp3.ResponseBody
import retrofit2.http.*

interface Api {
    @POST(Routes.LOGIN)
    suspend fun login(
        @Body authModel: AuthModel,
    ) : OkAuth

    @POST(Routes.REGISTER)
    suspend fun register(
        @Body authModel: AuthModel,
    ) : ResponseBody

    @GET(Routes.HIERARCHY)
    suspend fun getHierarchy() : OkHierarchy

    @POST(Routes.ACTIVE_USERS)
    suspend fun getActiveUsers() : OKActivityUsers

    @POST(Routes.SEND_MESSAGE)
    suspend fun sendMessage(
        @Body message: MessageModel
    ) : ResponseBody

    @GET(Routes.GET_MESSAGES)
    suspend fun getMessages(
        @Path("sub-theme") subTheme: String
    ) : OkListOfMessage

    @DELETE(Routes.LOGOUT)
    suspend fun logout() : ResponseBody
}