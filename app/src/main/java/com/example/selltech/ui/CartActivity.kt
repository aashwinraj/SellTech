package com.example.selltech.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.RecyclerView
import com.example.selltech.CartAdapter
import com.example.selltech.NaviationDrawerActivity
import com.example.selltech.R
import com.example.selltech.SnackbarActivity
import com.example.selltech.firestore.FirestoreClass
import com.example.selltech.models.CartItem
import com.example.selltech.models.Order
import com.google.firebase.firestore.FirebaseFirestore

class CartActivity : SnackbarActivity() {


    private val mfireStore = FirebaseFirestore.getInstance()
    var toataltext: TextView? = null
    var netText: TextView? = null
    var sippingText: TextView? = null
    var netTag: TextView? = null
    var netRs: TextView? = null
    var cartEmpty: TextView? = null
    var purcaseButton: Button? = null
    var ordersList: ArrayList<CartItem> = ArrayList()
    var CartList: ArrayList<CartItem> = ArrayList()
    var adapter: CartAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        toataltext = findViewById(R.id.tv_total)
        netText = findViewById(R.id.tv_netAmount)
        sippingText = findViewById(R.id.shippin60)
        netTag = findViewById(R.id.netTag)
        netRs = findViewById(R.id.netRs)
        cartEmpty = findViewById(R.id.tv_cartEmpty)
        purcaseButton = findViewById(R.id.purchaseButton)


        showProressDialogue()
        FirestoreClass().getCartListfromCloud(this)

        purcaseButton?.setOnClickListener {

showProressDialogue()
            placeOrder()
        }


    }


    fun orderPlacedSuccess(){

        hideProgressDialogue()
        Toast.makeText(this,"order placed Successfully",Toast.LENGTH_LONG).show()
        startActivity(Intent(this@CartActivity,NaviationDrawerActivity:: class.java ))
        finish()
    }

    fun placeOrder() {

        val order = Order(
            FirestoreClass().getCurrentUserid(),
            CartList,
            "My order ${System.currentTimeMillis()}",
            netText?.text.toString().trim(),
            CartList[0].itemImage,
        )
        FirestoreClass().addOrdertoCloud(this, order)
    }

    fun cartItemRemovedSuccess() {
        hideProgressDialogue()
        FirestoreClass().getCartListfromCloud(this)


    }

    fun cartBill(subtotal: Int) {
        toataltext?.text = "$subtotal"
        netText?.text = (subtotal + 60).toString()
    }

    fun CartListfromCloudSuccess(cartlist: ArrayList<CartItem>) {
        hideProgressDialogue()
        ordersList = cartlist
        CartList = cartlist
        var recyclerView: RecyclerView = findViewById(R.id.recyclerView_cart)
        adapter = CartAdapter(CartList)
        recyclerView.adapter = adapter
        if (cartlist.size == 0) {
            toataltext?.text = "0"
            netText?.text = "0"
            sippingText?.text = "0"
            netText?.visibility = View.GONE
            netTag?.visibility = View.GONE
            netRs?.visibility = View.GONE
            cartEmpty?.visibility = View.VISIBLE
            purcaseButton?.visibility = View.GONE
        }

    }


}