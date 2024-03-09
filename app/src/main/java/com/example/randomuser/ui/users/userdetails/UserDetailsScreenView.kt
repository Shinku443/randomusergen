package com.example.randomuser.ui.users.userdetails

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.randomuser.data.remote.response.Id
import com.example.randomuser.ui.users.userlist.MainUser

@Composable
fun UserDetailsScreen(
    navController: NavController,
    id: Id,
) {
    Text("here i am")
    MainUser(navController = navController, id = id)
}