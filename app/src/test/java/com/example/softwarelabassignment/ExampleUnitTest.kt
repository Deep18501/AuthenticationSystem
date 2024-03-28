package com.example.softwarelabassignment

import android.util.Log
import com.example.softwarelabassignment.data.RetrofitInstance
import com.example.softwarelabassignment.data.model.UserLoginDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testLogin() = runTest {

        val result = RetrofitInstance.api.loginPost(
            UserLoginDetails(
                "johndoe561210@mail.com", "12345678"
            )
        )
        println(result)
    }
}