package com.example.selltech

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class UserProfileActivity :SnackbarActivity() {

    var spinner: Spinner? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        spinner = findViewById(R.id.spinner)
        val languages = resources.getStringArray(R.array.Languages)

        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, languages
            )
            spinner?.adapter = adapter
        }
    }
}