package com.example.randomuser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.randomuser.data.remote.response.Id
import com.example.randomuser.ui.theme.RandomUserTheme
import com.example.randomuser.ui.users.userdetails.UserDetailsScreen
import com.example.randomuser.ui.users.userlist.UserListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomUserTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController =
                        rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "main_user_list_screen"
                    ) {
                        composable("main_user_list_screen") {
                            UserListScreen(navController = navController)
                        }

                        composable("detail_user_screen/{name}/{value}",
                            arguments = listOf(
                                navArgument("name") {
                                    type = NavType.StringType
                                },
                                navArgument("value") {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            val name = it.arguments?.getString("name")
                            val value = it.arguments?.getString("value")

                            UserDetailsScreen(
                                navController = navController,
                                id = Id(name ?: "", value ?: "")
                            )
                        }
                    }
                }
            }
        }
    }
}