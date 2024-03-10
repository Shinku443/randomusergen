package com.example.randomuser.ui.users.userlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.randomuser.data.remote.response.Picture
import com.example.randomuser.data.remote.response.User
import timber.log.Timber

@Composable
fun UserListScreen(
    navController: NavController,
    viewModel: UserListViewModel
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

        Column {
            if (isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                if (viewModel.mainUser.value.isNotEmpty()) {
                    val mainUser = viewModel.mainUser.value[0]
                    MainUser(
                        navController = navController,
                        user = mainUser,
                        viewModel = viewModel,
                        onClick = {
                            navController.navigate(
                                "detail_user_screen/${mainUser.name.first}/${mainUser.name.last}/${mainUser.dob.date}"
                            )
                        }
                    )
                } else {
                    Text("Something went wrong on our end, try again!")
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
                    viewModel.getNewUser()
                },
            ) {
                Text("Generate new user")
            }

        }
    }
}

@Composable
fun MainUser(
    navController: NavController,
    user: User,
    onClick: () -> Unit,
    viewModel: UserListViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfilePicture(picture = user.picture)
        Text(user.name.last + ", " + user.name.first)
        Text(user.email)
        Text(user.phone)
        Text(user.location.city + ", " + user.location.state)
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