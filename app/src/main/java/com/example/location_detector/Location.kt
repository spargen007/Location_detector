package com.example.location_detector

import com.google.gson.annotations.SerializedName

data class Location(
@SerializedName("id")
val id: Int = 0,
@SerializedName("name")
val name: String = "",
@SerializedName("state")
val state: String="")


//data class Location(
//    @SerializedName("id")
//    val id: Int = 0,
//    @SerializedName("title")
//    val name: String = "",
//    @SerializedName("userId")
//    val state: Int =0 )