package com.example.randomuser.ui.users.userlist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomuser.data.Result
import com.example.randomuser.data.remote.response.User
import com.example.randomuser.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserListScreenViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    var userList =
        mutableStateOf<List<User>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    init {
        getUserList()
    }

    private fun getUserList() {
        viewModelScope.launch {
            isLoading.value = true

            val result = userRepository.getUserList()
            Timber.e("response: ${result.message}")
            when (result) {
                is Result.Success -> {
                    val entries = result?.data?.results?.map { userItem ->
                       User(
                            cell = userItem.cell,
                            dob = userItem.dob,
                            email = userItem.email,
                            gender = userItem.gender,
                            id = userItem.id,
                            name = userItem.name,
                            login = userItem.login,
                            location = userItem.location,
                            nat = userItem.nat,
                            phone = userItem.phone,
                            picture = userItem.picture,
                            registered = userItem.registered
                        )
                    }
                    isLoading.value = false
                    if (!entries.isNullOrEmpty()) {
                        loadError.value = ""
                        userList.value = entries
                    } else {
                        loadError.value = "Error loading user list :: isNullOrEmpty:: $entries"
                        Timber.e("Successful API call but issue mapping")
                    }
                }

                is Result.Error -> {
                    loadError.value = result.message ?: "Uknown Error"
                    Timber.e("Error with API call")
                    isLoading.value = false
                }
            }
        }
    }
}
