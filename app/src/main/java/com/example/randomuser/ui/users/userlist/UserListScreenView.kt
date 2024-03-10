package com.example.randomuser.ui.users.userlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.randomuser.data.remote.response.Id
import com.example.randomuser.data.remote.response.Picture
import com.example.randomuser.data.remote.response.User
import timber.log.Timber

@Composable
fun UserListScreen(
    navController: NavController,
    viewModel: UserListViewModel = hiltViewModel()
) {
    val listOfUsers by remember { viewModel.userList }
    val isLoading by remember { viewModel.isLoading }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        if (isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Column {
                if (listOfUsers.isNotEmpty()) {
                    val firstUser = listOfUsers.first()
                    MainUser(
                        navController = navController,
                        firstName = firstUser.name.first,
                        lastName = firstUser.name.last,
                        dob = firstUser.dob.date
                    )
                }
            }

            if (listOfUsers.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .weight(1f),
                    contentPadding = PaddingValues(16.dp), userScrollEnabled = true
                ) {
                    items(listOfUsers.drop(1)) {
                        UserList(navController, it)
                        Spacer(Modifier.height(16.dp))
                    }
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
                    Timber.e("!!get user list")
                    viewModel.getUserList()
                },
            ) {
                Text("Generate new user & list")
            }

        }
    }
}

@Composable
fun MainUser(
    navController: NavController,
    firstName: String,
    lastName: String,
    dob: String,
    viewModel: UserListViewModel = hiltViewModel()
) {
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
                Timber.e("we r gewd")

                ProfilePicture(picture = user.picture)
                Text(user.name.last + ", " + user.name.first)
                Text(user.email)
                Text(user.phone)
                Text(user.location.city + ", " + user.location.state)
            }
        }
    }

}

@Composable
fun UserList(
    navController: NavController,
    user: User
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .clickable { navController.navigate("detail_user_screen/${user.name.first}/${user.name.last}/${user.dob.date}") },
        verticalAlignment = Alignment.CenterVertically
    ) {
        ProfilePicture(user.picture)
        Spacer(Modifier.padding(horizontal = 4.dp))
        Text(text = user.name.last + ", " + user.name.first)
    }

}


@Composable
fun ProfilePicture(picture: Picture) {
    Column {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(
                    picture.large
                )
                .build(),
            contentDescription = "profile_picture",
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            onError = {
                Timber.e("Failed to load image! : $it.result.throwable.message")
            }
        )
    }

}