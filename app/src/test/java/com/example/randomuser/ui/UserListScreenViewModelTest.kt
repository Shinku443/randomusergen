package com.example.randomuser.ui

import androidx.lifecycle.SavedStateHandle
import com.example.randomuser.MainDispatcherRule
import com.example.randomuser.data.remote.response.User
import com.example.randomuser.data.repository.UserRepository
import com.example.randomuser.ui.users.userlist.UserListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import com.example.randomuser.data.Result
import com.example.randomuser.data.remote.response.Coordinates
import com.example.randomuser.data.remote.response.Dob
import com.example.randomuser.data.remote.response.Id
import com.example.randomuser.data.remote.response.Location
import com.example.randomuser.data.remote.response.Login
import com.example.randomuser.data.remote.response.Name
import com.example.randomuser.data.remote.response.Picture
import com.example.randomuser.data.remote.response.Registered
import com.example.randomuser.data.remote.response.Response
import com.example.randomuser.data.remote.response.Street
import com.example.randomuser.data.remote.response.Timezone


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
internal class UserListScreenViewModelTest {
    private val mockRepo: UserRepository = mock(UserRepository::class.java)
    private lateinit var userViewModel: UserListViewModel
    private val defaultUser = User(
        "cell", Dob(1, "111"), "email", "gender", Id("name","age"), Location(
            "city",
            Coordinates("lat", "long"),
            "country", "post", "state", Street("name", 1), Timezone("description", "offset"),
        ),
        Login("md5", "pass", "salt", "sha1", "sha256", "username", "uuid"),
        Name("first", "last", "title"),
        "nat", "phone", Picture("large", "medium", "thumbnail"),
        Registered(1, "date")
    )

    private var response: Response = Response(info = null, results = listOf(defaultUser))
    private val stateHandle = mock(SavedStateHandle::class.java)

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainDispatcherRule()

    private fun setupDataForViewModel() {
        userViewModel = UserListViewModel(mockRepo, stateHandle)
    }

    @Test
    fun getValidUserList_expectEquals() = runTest {
        `when`(mockRepo.getUserList()).thenReturn(
            Result.Success(response)
        )
        setupDataForViewModel()
        userViewModel.getUserList()
        advanceUntilIdle()

        Assert.assertEquals(response.results, userViewModel.userList.value)
    }

        @Test
        fun getNullUserListOnSuccessfulCall_expectError() = runTest {
            `when`(mockRepo.getUserList()).thenReturn(
                Result.Success(Response(null, listOf()))
            )
            setupDataForViewModel()
            userViewModel.getUserList()

            advanceUntilIdle()

            Assert.assertEquals("Error loading user list :: isNullOrEmpty", userViewModel.loadError.value)
        }

        @Test
        fun getInvalidUserList_expectNotEquals() = runTest {
            val expectedList = listOf(null)
            `when`(mockRepo.getUserList()).thenReturn(
                Result.Success(response)
            )
            setupDataForViewModel()
            userViewModel.getUserList()

            advanceUntilIdle()
            Assert.assertNotEquals(expectedList, mockRepo.getUserList().data)
        }

        @Test
        fun getErrorWhenCallingRepo() = runTest {
            val expectedError = "Error occurred"
            `when`(mockRepo.getUserList()).thenReturn(
                Result.Error("Error occurred", response)
            )
            setupDataForViewModel()
            userViewModel.getUserList()

            advanceUntilIdle()

            Assert.assertEquals(expectedError, userViewModel.loadError.value)
        }
}