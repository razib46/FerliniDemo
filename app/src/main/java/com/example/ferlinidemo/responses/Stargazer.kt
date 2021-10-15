package com.example.ferlinidemo.responses

import java.io.Serializable
import com.google.gson.annotations.SerializedName

class Stargazer : Serializable {
    @SerializedName("login")
    private val login: String? = null

    @SerializedName("avatar_url")
    private val avatar: String? = null

    fun getLogin(): String? {
        return login
    }

    fun getAvatar(): String? {
        return avatar
    }
}