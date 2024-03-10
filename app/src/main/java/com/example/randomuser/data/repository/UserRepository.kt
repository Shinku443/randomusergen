package com.example.randomuser.data.repository

import com.example.randomuser.data.Result
import com.example.randomuser.data.remote.UserApi
import com.example.randomuser.data.remote.response.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi){
    suspend fun getUserList(): Result<Response> {
        val response = try {
            userApi.getUserList()
        } catch (e: Exception){
            return Result.Error("Error when attempting to get user list: $e")
        }
        return Result.Success(response)
    }

    suspend fun getNewUser(): Result<Response> {
        val response = try {
            userApi.getUser()
        } catch (e: Exception){
            return Result.Error("Error when attempting to get user list: $e")
        }
        return Result.Success(response)
    }
}