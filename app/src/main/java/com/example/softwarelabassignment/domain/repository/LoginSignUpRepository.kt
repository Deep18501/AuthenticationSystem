package com.example.softwarelabassignment.domain.repository

import com.example.softwarelabassignment.data.model.Results
import com.example.softwarelabassignment.Result
import com.example.softwarelabassignment.data.model.UserLoginDetails
import com.example.softwarelabassignment.data.model.UserRegisterDetails
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow

interface LoginSignUpRepository {


    suspend fun registrationPost(userRegisterDetails: UserRegisterDetails): Flow<Result<Results>>
    suspend fun loginPost(userLoginDetails: UserLoginDetails): Flow<Result<Results>>
    suspend fun forgotPassword(mobile:String): Flow<Result<Results>>
    suspend fun verifyPassword(otp:String): Flow<Result<Results>>
    suspend fun changePassword(token:String,password:String,cPassword:String): Flow<Result<Results>>

}