package com.example.randomuser.data.remote

import com.example.randomuser.data.remote.response.Response
import retrofit2.http.GET

/**
 * Handles User API interfacing
 */
interface UserApi {
    @GET("api/?results=10")
    suspend fun getUserList(): Response
}