package com.example.randomuser.ui.users.userdetails

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.navigation.NavController
import com.example.randomuser.data.remote.response.User
import com.example.randomuser.ui.users.userlist.MainUser
import com.example.randomuser.ui.users.userlist.UserListViewModel
import timber.log.Timber

@Composable
fun UserDetailsScreen(
    navController: NavController,
    firstName: String,
    lastName: String,
    dob: String,
    viewModel: UserListViewModel
) {
    val context = LocalContext.current
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val mainUser = viewModel.mainUser.value[0]
        lateinit var user: User

        if (firstName == mainUser.name.first && lastName == mainUser.name.last && dob == mainUser.dob.date) {
            user = mainUser
        } else {
            for (checkUser: User in viewModel.userList.value) {
                if (firstName == checkUser.name.first && lastName == checkUser.name.last && dob == checkUser.dob.date) {
                    user = checkUser
                }
            }
        }
        MainUser(
            navController = navController,
            user = user,
            viewModel = viewModel,
            onClick = {
                clipboardManager.setText(AnnotatedString((text)))
                Toast.makeText(context, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
            }
        )


    }
}