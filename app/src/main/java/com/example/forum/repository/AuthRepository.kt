package com.example.forum.repository

import com.example.forum.api.Api
import com.example.forum.model.AuthModel

class AuthRepository(private val api: Api): BaseRepository() {
    suspend fun login(username: String, pwd: String) =
        safeApiCall {
            api.login(AuthModel(username, pwd))
        }

    suspend fun register(username: String, pwd: String) =
        safeApiCall {
            api.register(AuthModel(username, pwd))
        }
}