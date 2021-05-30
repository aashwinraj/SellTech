package com.example.selltech.util

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import java.util.jar.Attributes

class MPSTextviewBold(context: Context, attrs: AttributeSet) : AppCompatTextView(context , attrs) {
    init {
        applyfont()
    }



    private fun applyfont() {
        val typeface: Typeface =
            Typeface.createFromAsset(context.assets, "Montserrat-Bold.ttf")
        setTypeface(typeface)
    }
}