package com.example.selltech.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class Product(
    var user_id : String="",
    var user_name : String="",
    var image:String="",
    var product_type : String="",
    var brand : String="",
    var price: String="",
    var used_time: String="",
    var description: String="",
    var product_id : String=" "
    ):Parcelable