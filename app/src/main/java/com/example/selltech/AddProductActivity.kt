package com.example.selltech

import android.app.ActionBar
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.selltech.firestore.FirestoreClass
import com.example.selltech.models.Product
import com.example.selltech.util.Constants
import java.util.*

class AddProductActivity : SnackbarActivity() {
    private val PICK_IMAGE = 100
    var imageUri: Uri? = null
    var imageURL: String? = null
    private val imageName = UUID.randomUUID().toString() + ".jpg"
    var chooseImageIcon: ImageView? = null
    var productImageView: ImageView? = null
    var productType: EditText? = null
    var productBrand: EditText? = null
    var productPrice: EditText? = null
    var productUsedTime: EditText? = null
    var productDescription: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        chooseImageIcon = findViewById(R.id.chooseProductImage)
        productImageView = findViewById(R.id.imageView_addProduct)
        productType = findViewById(R.id.et_addProductType)
        productBrand = findViewById(R.id.et_addProductBrand)
        productPrice = findViewById(R.id.et_addProductPrice)
        productUsedTime = findViewById(R.id.et_addProductUsedTime)
        productDescription = findViewById(R.id.et_addProductDesCription)
        var submitButton: Button = findViewById(R.id.submit_product_button)

        submitButton.setOnClickListener {
            if (validateDetails()) {
                upLoadImage()
            }
        }

        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar?.hide()
        chooseImageIcon?.setOnClickListener {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

            startActivityForResult(galleryIntent, PICK_IMAGE)
        }


    }

    fun upLoadImage() {
        showProressDialogue()
        FirestoreClass().uploadImageToCloudStorage(this, imageUri, "product_image")
    }

         fun imageUploadSuccess(image_url: String) {
        hideProgressDialogue()
        Toast.makeText(this, imageURL, Toast.LENGTH_LONG).show()
        imageURL = image_url
          uploadProduct_details()
    }

    fun productUploadSuccess()
    {
        hideProgressDialogue()
        Toast.makeText(this, "product uploaded successfully", Toast.LENGTH_LONG).show()
        finish()
    }


    private fun validateDetails(): Boolean {
        return when {

            imageUri == null -> {
                showSnackBar("Select an image of product", true)
                false
            }

            TextUtils.isEmpty(productType?.text.toString().trim() { it <= ' ' }) -> {
                showSnackBar("Enter item type", true)
                false

            }

            TextUtils.isEmpty(productBrand?.text.toString().trim() { it <= ' ' }) -> {
                showSnackBar("Enter Product Brand", true)
                false

            }

            TextUtils.isEmpty(productPrice?.text.toString().trim() { it <= ' ' }) -> {
                showSnackBar("Enter Product Price", true)
                false

            }

            TextUtils.isEmpty(productUsedTime?.text.toString().trim() { it <= ' ' }) -> {
                showSnackBar("Enter Product Used Time", true)
                false

            }

            TextUtils.isEmpty(productDescription?.text.toString().trim() { it <= ' ' }) -> {
                showSnackBar("Enter Description", true)
                false

            }


            else -> {
                //showSnackBar("all good", false)
                true
            }


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {


        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {

            if (data != null) {
                showProressDialogue()
                imageUri = data.data
            }

            productImageView?.setImageURI(imageUri);
            hideProgressDialogue()


        } else {
            hideProgressDialogue()
        }


    }

   fun uploadProduct_details() {

        var userName = this.getSharedPreferences(
            Constants.PREFERENCES,
            Context.MODE_PRIVATE
        ).getString(Constants.LOGGED_IN_USERNAME, "")
        var product = Product(FirestoreClass().getCurrentUserid(),
    userName!!,
    imageURL!!,
            productType?.text.toString().trim() { it <= ' ' },
            productBrand?.text.toString().trim() { it <= ' ' },
            productPrice?.text.toString().trim() { it <= ' ' },
            productUsedTime?.text.toString().trim() { it <= ' ' },
            productDescription?.text.toString().trim() { it <= ' ' }

     )
          FirestoreClass().uploadProductToCloud(this,product)


    }

}