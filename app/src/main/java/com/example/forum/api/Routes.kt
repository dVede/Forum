package com.example.forum.api

import com.example.forum.DEFAULT_HOST
import com.example.forum.DEFAULT_PORT

object Routes {
    const val BASE_URL = "http://$DEFAULT_HOST:$DEFAULT_PORT/"
    private const val AUTH = "auth/"
    private const val USER = "forum/request/"
    const val LOGIN = "${AUTH}sign-in"
    const val REGISTER = "${AUTH}sign-up"
    const val HIERARCHY = "${USER}hierarchy"
    const val ACTIVE_USERS = "${USER}active-users"
    const val SEND_MESSAGE = "${USER}message"
    const val GET_MESSAGES = "${USER}message-list/{sub-theme}"
    const val LOGOUT = "${USER}logout"
}