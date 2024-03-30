package com.example.softwarelabassignment.data.model

data class UserRegisterDetails(
    val address: String="",
    val business_hours: BusinessHours?=null,
    val business_name: String="",
    val city: String="",
    val device_token: String="0imfnc8mVLWwsAawjYr4Rx-Af50DDqtlx",
    val email: String,
    val full_name: String,
    val informal_name: String="",
    val password: String?=null,
    val phone: String="",
    val registration_proof: String="",
    val role: String="farmer",
    val social_id: String="0imfnc8mVLWwsAawjYr4Rx-Af50DDqtlx",
    val state: String="",
    val type: String="email",
    val zip_code: Int=0
)