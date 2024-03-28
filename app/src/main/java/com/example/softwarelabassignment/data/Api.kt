package com.example.softwarelabassignment.data

import com.example.softwarelabassignment.data.model.Results
import com.example.softwarelabassignment.data.model.UserLoginDetails
import com.example.softwarelabassignment.data.model.UserRegisterDetails
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("register")
    suspend fun registrationPost(
        @Body userDetails:UserRegisterDetails
    ):Results

    @POST("login")
    suspend fun loginPost(
    @Body userLoginDetails: UserLoginDetails
    ):Results

    @POST("forgot-password")
    suspend fun forgotPassword(
        @Body mobile:  ForgotPasswordRequest
    ):Results

    @POST("verify-otp")
    suspend fun verifyPassword(
        @Body otp: OtpRequest
    ):Results

    @POST("reset-password")
    suspend fun changePassword(
        @Body passwordChange: ChangePasswordRequest
    ):Map<String, Any>


    companion object{
        const val BASE_URL="https://sowlab.com/assignment/user/"
    }
}

