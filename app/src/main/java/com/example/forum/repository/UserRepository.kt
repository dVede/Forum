package com.example.forum.repository

import com.example.forum.api.Api
import com.example.forum.model.MessageModel

class UserRepository(private val api: Api) : BaseRepository() {
    suspend fun logout() =
        safeApiCall {
            api.logout()
        }

    suspend fun getUsers() =
        safeApiCall {
            api.getActiveUsers()
        }

    suspend fun getHierarchy() =
        safeApiCall {
            api.getHierarchy()
        }

    suspend fun getMessages(threadName: String) =
        safeApiCall {
            api.getMessages(threadName)
        }

    suspend fun sendMessage(message: MessageModel) =
        safeApiCall {
            api.sendMessage(message)
        }
}