package com.example.selltech.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order (
    val  userID:String="",
    val items:ArrayList<CartItem> =ArrayList(),
    val title:String="",
    val total:String="",
    val image:String="",
    var id:String =""
): Parcelable