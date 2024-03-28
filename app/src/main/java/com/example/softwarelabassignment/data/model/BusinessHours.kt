package com.example.softwarelabassignment.data.model

data class BusinessHours(
    val fri: List<String>?=null,
    val mon: List<String>?=null,
    val sat: List<String>?=null,
    val sun: List<String>?=null,
    val thu: List<String>?=null,
    val tue: List<String>?=null,
    val wed: List<String>?=null
)