package com.example.ferlinidemo

import com.example.ferlinidemo.responses.Stargazer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiEndpointInterface {
    @GET("repos/{owner}/{repo}/stargazers")
    fun getStargazers(@Path("owner") owner: String, @Path("repo") repo: String): Call<List<Stargazer>>
}