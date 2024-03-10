package com.example.randomuser.data.remote.response

//TODO - I believe the API has a way of just getting the data and dropping the info, would be useful for ease of use in future
data class Response(
    val info: Info?,
    val results: List<User>
)