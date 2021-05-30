package com.example.selltech.util

import android.app.Activity
import android.net.Uri
import android.webkit.MimeTypeMap

object Constants {

    const val USERS: String = "users"
    const val PRODUCTS: String = "products"
    const val PREFERENCES: String = "SellTech"
    const val LOGGED_IN_USERNAME: String = "loggedInUserName"
    const val PRODUCT_IMAGE: String = "product_image"
    const val USER_ID: String = "user_id"
    const val USER_PROFILE_IMAGE: String = "user_profile_image"
    const val CART_ITEMS: String = "cart_items"
    const val ORDERS_ITEMS: String = "orders_items"
    fun getFileExtension(activity: Activity, uri: Uri?): String? {
        /*
         * MimeTypeMap: Two-way map that maps MIME-types to file extensions and vice versa.
         *
         * getSingleton(): Get the singleton instance of MimeTypeMap.
         *
         * getExtensionFromMimeType: Return the registered extension for the given MIME type.
         *
         * contentResolver.getType: Return the MIME type of the given content URL.
         */
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }

}