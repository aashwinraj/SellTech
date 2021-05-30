package com.example.selltech

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.RecyclerView
import com.example.selltech.firestore.FirestoreClass
import com.example.selltech.models.Product
import com.example.selltech.util.Constants
import com.google.firebase.firestore.FirebaseFirestore

class HomeScreenActivity : SnackbarActivity() {

    var productList: ArrayList<Product> = ArrayList()
    private val mfireStore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()
        FirestoreClass().getUserDetails(this)

        val sharedpreferences = getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE)
        val username = sharedpreferences.getString(Constants.LOGGED_IN_USERNAME, "")

        showProressDialogue()
        var recyclerView: RecyclerView = findViewById(R.id.recyclerView_products)
        var adapter = ProductListAdapter(productList)
        recyclerView.adapter=adapter
        mfireStore.collection(Constants.PRODUCTS)
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->



                for (i in document.documents) {

                    val product = i.toObject(Product::class.java)!!
                    product.product_id = i.id
                    //Toast.makeText(this, product.image , Toast.LENGTH_LONG).show()


                    productList.add(product)
                    adapter.notifyDataSetChanged()
                    hideProgressDialogue()
                }


                // Pass the success result to the base fragment.
                //fragment.successDashboardItemsList(productsList)
            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is any error which getting the dashboard items list.
                hideProgressDialogue()
                //Log.e(fragment.javaClass.simpleName, "Error while getting dashboard items list.", e)
            }

    }








}