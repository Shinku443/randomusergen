package com.example.randomuser.ui.users.userlist

import android.widget.ProgressBar
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.randomuser.data.remote.response.User

@Composable
fun UserListScreen(
    navController: NavController,
    viewModel: UserListScreenViewModel = hiltViewModel()
) {
    val listOfUsers by remember { viewModel.userList }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        Column {
            if (listOfUsers.isNotEmpty()) {
                MainUser(navController, listOfUsers.first())
            } else {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(
                    horizontal = 20.dp,
                    vertical = 20.dp
                ),
            onClick = {
                //TODO - refresh list
                Toast.makeText(context, "refresh the list logic will go here", Toast.LENGTH_LONG)
                    .show()
            },
        ) {
            Text("Generate new user & list")
        }
        LazyColumn(contentPadding = PaddingValues(16.dp)) {
            items(listOfUsers.drop(1)) {
                UserList(navController, it)
                Spacer(Modifier.height(16.dp))
            }
        }
    }

}

@Composable
fun MainUser(
    navController: NavController,
    user: User
) {

}

fun UserList(
    navController: NavController,
    user: User
) {

}