package com.example.randomuser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.randomuser.ui.theme.RandomUserTheme
import com.example.randomuser.ui.users.userdetails.UserDetailsScreen
import com.example.randomuser.ui.users.userlist.UserListScreen
import com.example.randomuser.ui.users.userlist.UserListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: UserListViewModel by viewModels()
        viewModel.getNewUser()
        viewModel.getUserList()

        setContent {
            RandomUserTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val viewModel: UserListViewModel = hiltViewModel()
                    NavHost(
                        navController = navController,
                        startDestination = "main_user_list_screen"
                    ) {
                        composable("main_user_list_screen") {
                            UserListScreen(navController = navController, viewModel = viewModel)
                        }

                        composable("detail_user_screen/{firstName}/{lastName}/{dobDate}",
                            arguments = listOf(
                                navArgument("firstName") {
                                    type = NavType.StringType
                                },
                                navArgument("lastName") {
                                    type = NavType.StringType
                                },
                                navArgument("dobDate") {
                                    type = NavType.StringType
                                }
                            )
                        ) {
                            val firstName = it.arguments?.getString("firstName")
                            val lastName = it.arguments?.getString("lastName")
                            val dobDate = it.arguments?.getString("dobDate")
                            UserDetailsScreen(
                                navController = navController,
                                firstName = firstName ?: "test",
                                lastName = lastName ?: "test",
                                dob = dobDate ?: "",
                                viewModel = viewModel
                            )

                        }
                    }
                }
            }
        }
    }
}