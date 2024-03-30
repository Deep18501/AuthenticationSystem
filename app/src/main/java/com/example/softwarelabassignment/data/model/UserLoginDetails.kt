package com.example.softwarelabassignment.data.model

data class UserLoginDetails(
    val email: String,
    val password: String?=null,
    val role: String="farmer",
    val type: String="email",
    val social_id: String?=null,
)