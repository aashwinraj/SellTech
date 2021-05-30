package com.example.selltech.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.FragmentTransaction
import com.example.selltech.GlideLoader
import com.example.selltech.R
import com.example.selltech.SnackbarActivity
import com.example.selltech.firestore.FirestoreClass
import com.example.selltech.models.CartItem
import com.example.selltech.models.Product
import com.example.selltech.ui.home.SlideshowFragment

class ProductDetailsActivity : SnackbarActivity(), View.OnClickListener {
    var myProductId: String? = null
    var productOwnerId: String? = null
    private lateinit var mProductDetails: Product
    var myUserId: String? = null
    val DEFAULT_CART_QUANTITY: String = "1"

    var productDetailsImage: ImageView? = null
    var productDetailsType: TextView? = null
    var productdetailsbrandname: TextView? = null
    var productDetailsPrice: TextView? = null
    var productDetailsUsedfor: TextView? = null
    var productDetailsDescription: TextView? = null
    var buyButton: Button? = null
    private var goToCartButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        productDetailsImage = findViewById(R.id.productDetailsImage)
        productDetailsType = findViewById(R.id.pd_productType)
        productdetailsbrandname = findViewById(R.id.pd_brandName)
        productDetailsPrice = findViewById(R.id.pd_price)
        productDetailsUsedfor = findViewById(R.id.pd_usedFor)
        productDetailsDescription = findViewById(R.id.pd_description)
        buyButton = findViewById(R.id.buyButton)
        goToCartButton = findViewById(R.id.goToCartButton)

        if (intent.hasExtra("Product_ID")) {
            showProressDialogue()
            myProductId = intent.getStringExtra("Product_ID")
            //Toast.makeText(this,myProductId,Toast.LENGTH_LONG).show()

            FirestoreClass().getProductDetails(this, myProductId!!)
        }
        if (intent.hasExtra("User_ID")) {

            myUserId = intent.getStringExtra("User_ID")
            //Toast.makeText(this,myProductId,Toast.LENGTH_LONG).show()


        }
        if (FirestoreClass().getCurrentUserid() == myUserId) {
            buyButton?.visibility = View.GONE
            goToCartButton?.visibility = View.GONE
        } else {
            buyButton?.visibility = View.VISIBLE
        }

        buyButton?.setOnClickListener(this)
        goToCartButton?.setOnClickListener(this)
    }


    fun addToCart() {

        val additem = CartItem(
            FirestoreClass().getCurrentUserid(),
            myProductId!!,
            mProductDetails.product_type,
            mProductDetails.brand,
            mProductDetails.price,
            mProductDetails.image,
            DEFAULT_CART_QUANTITY
        )
        showProressDialogue()
        FirestoreClass().addCartItemToCloud(this, additem)
    }


    fun productDetailsSuccess(product: Product) {

        mProductDetails = product
        GlideLoader().loadProductPicture(this, product.image, productDetailsImage!!)
        productDetailsType?.text = product.product_type
        productdetailsbrandname?.text = product.brand
        productDetailsPrice?.text = "${product.price} Rs"
        productDetailsUsedfor?.text = product.used_time
        productDetailsDescription?.text = product.description

        if(FirestoreClass().getCurrentUserid()==product.user_id){
            hideProgressDialogue()}
            else{
                FirestoreClass().checkItemInCart(this@ProductDetailsActivity, product.product_id)
          }
     }


    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.buyButton -> {

                    addToCart()

                }
                R.id.goToCartButton->{
                    var intent = Intent(this@ProductDetailsActivity,CartActivity::class.java)
                    startActivity(intent)
                    finish()


                }
            }
        }
    }

    fun addtoCartSuccess() {
        hideProgressDialogue()
       // Toast.makeText(this, "Itme added to cart successfully", Toast.LENGTH_LONG).show()
        buyButton?.visibility=View.GONE
        goToCartButton?.visibility=View.VISIBLE
    }

    fun productDoExistInCart() {
        hideProgressDialogue()
        buyButton?.visibility=View.GONE
        goToCartButton?.visibility=View.VISIBLE
    }
}