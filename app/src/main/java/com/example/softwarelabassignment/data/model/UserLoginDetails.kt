package com.example.softwarelabassignment.data.model

data class UserLoginDetails(
    val email: String,
    val password: String,
    val role: String="farmer",
    val type: String="email"
)