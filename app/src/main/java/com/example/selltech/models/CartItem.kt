package com.example.selltech.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CartItem(
    val itemuserID:String="",
    val itemproductID:String="",
    val itemType:String="",
    val itemBrand:String="",
    val itemPrice:String="",
    val itemImage:String="",
    val cartQuantity:String="",
var cartId:String=""
):Parcelable