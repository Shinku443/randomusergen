package com.example.randomuser.data.remote

import com.example.randomuser.data.remote.response.User
import retrofit2.http.GET

/**
 * Handles User API interfacing
 */
interface UserApi {
    @GET("/")
    suspend fun getUserList(): List<User>
}