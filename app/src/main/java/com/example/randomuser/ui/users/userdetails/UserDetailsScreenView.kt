package com.example.randomuser.ui.users.userdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.randomuser.data.remote.response.User
import com.example.randomuser.ui.users.userlist.MainUser
import com.example.randomuser.ui.users.userlist.ProfilePicture
import com.example.randomuser.ui.users.userlist.UserListViewModel
import timber.log.Timber

@Composable
fun UserDetailsScreen(
    navController: NavController,
    firstName: String,
    lastName: String,
    dob: String,
    viewModel:UserListViewModel = hiltViewModel()
) {
    //TODO - remove timber/logs/text
    Timber.e("first Name:: $firstName")
    Column(   modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text("here i am")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Cyan)
                .padding(horizontal = 12.dp)
                .clickable { navController.navigate("detail_user_screen/${firstName}/${lastName}/${dob}") },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (user: User in viewModel.userList.value) {
                if (user.name.first == firstName && user.name.last == lastName && dob == user.dob.date) {
                    Timber.e("names and dob match")

                    ProfilePicture(picture = user.picture)
                    Text(user.name.last + ", " + user.name.first)
                    Text(user.email)
                    Text(user.phone)
                    Text(user.location.city + ", " + user.location.state)
                }
            }
        }

    }
}