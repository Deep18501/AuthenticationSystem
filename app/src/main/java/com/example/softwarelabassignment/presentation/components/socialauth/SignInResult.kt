package com.example.softwarelabassignment.presentation.components.socialauth

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val userId: String,
    val username: String?,
    val userEmail:String
)