package com.example.softwarelabassignment.data

import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.softwarelabassignment.data.model.Results
import com.example.softwarelabassignment.data.model.UserRegisterDetails
import com.example.softwarelabassignment.domain.repository.LoginSignUpRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.softwarelabassignment.Result
import com.example.softwarelabassignment.data.model.UserLoginDetails
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.HttpException

import java.io.IOException


class LoginSignUpRepositoryImpl(
    private val api: Api
) : LoginSignUpRepository {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun registrationPost(userRegisterDetails: UserRegisterDetails): Flow<Result<Results>> {
        return flow {
            val results = try {
                api.registrationPost(
                    userRegisterDetails
                )
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(data = null, "Error IOException"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(data = null, message = "Error HttpException"))
                return@flow
            }
            emit(Result.Success(results))
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun loginPost(userLoginDetails: UserLoginDetails): Flow<Result<Results>> {
        return flow {
            val results = try {
                api.loginPost(userLoginDetails)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(data = null, "Error IOException"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(data = null, message = "Error HttpException"))
                return@flow
            }
            emit(Result.Success(results))
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun forgotPassword(mobile: String): Flow<Result<Results>> {
        return flow {
            val results = try {
                api.forgotPassword(
                    ForgotPasswordRequest(mobile)
                )
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(data = null, "Error IOException"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(data = null, message = "Error HttpException"))
                return@flow
            }
            emit(Result.Success(results))
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun verifyPassword(otp: String): Flow<Result<Results>> {

        return flow {
            val results = try {
                api.verifyPassword(
                    OtpRequest(otp)
                )
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(data = null, "Error IOException"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(data = null, message = "Error HttpException"))
                return@flow
            }
            emit(Result.Success(results))
        }

    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun changePassword(
        token: String,
        password: String,
        cPassword: String
    ): Flow<Result<Results>> {

        return flow {
            val resultsVerify = try {
                api.changePassword(
                    ChangePasswordRequest(token,password, cPassword)
                )
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(data = null, "Error IOException"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Result.Error(data = null, message = "Error HttpException"))
                return@flow
            }
            val results = Results(
                success = resultsVerify["success"].toString(),
                message = resultsVerify["message"].toString()
            )
            emit(Result.Success(results))
        }

    }

}

data class ForgotPasswordRequest(val mobile: String)
data class OtpRequest(val otp: String)
data class ChangePasswordRequest(
    val token: String,
    val password: String,
    val cPassword: String
)
