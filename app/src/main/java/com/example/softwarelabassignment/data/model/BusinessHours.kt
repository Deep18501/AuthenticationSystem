package com.example.softwarelabassignment.data.model

data class BusinessHours(
    var fri: List<String>?=null,
    var mon: List<String>?=null,
    var sat: List<String>?=null,
    var sun: List<String>?=null,
    var thu: List<String>?=null,
    var tue: List<String>?=null,
    var wed: List<String>?=null
)

