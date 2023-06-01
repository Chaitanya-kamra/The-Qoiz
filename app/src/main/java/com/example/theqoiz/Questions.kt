package com.example.theqoiz

data class Questions(
    val id:Int,
    val Question:String,
    val Image: Int,
    val OptOne:String,
    val OptTwo:String,
    val OptThree:String,
    val OptFour:String,
    val correctAns :Int
)
