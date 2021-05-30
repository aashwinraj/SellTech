package com.example.selltech.models


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
class Users(
    val id: String? ="",
    val name: String? ="",
    val email: String? ="",
    val mobile:String?="",
    val image: String? ="",
    val profileCompleted:Int=0):Parcelable