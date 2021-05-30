package com.example.selltech.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.example.selltech.ProductListAdapter
import com.example.selltech.R
import com.example.selltech.firestore.FirestoreClass
import com.example.selltech.models.Product
import com.example.selltech.ui.CartActivity
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : BaseFragment() {

  private lateinit var mRootView: View



  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

    mRootView=inflater.inflate(R.layout.fragment_home,container,false)
    return mRootView
  }

  override fun onResume() {
    super.onResume()
    getProductListFromFireStore()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setHasOptionsMenu(true)
//        var navButton:ImageButton=mRootView.findViewById(R.id.navDrawerbutton)
//
//        navButton.setOnClickListener {
//
//            startActivity(Intent(this@ShopFragment,NaviationDrawerActivity::class.java))
//        }

  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


    super.onViewCreated(view, savedInstanceState)
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    //inflater.inflate(R.menu.search_products_menu, menu)
    inflater.inflate(R.menu.cart_menu,menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {


    val id = item.itemId
    when (id) {
      R.id.cart_menu -> {
   startActivity(Intent(activity, CartActivity::class.java))
      }

    }

    return super.onOptionsItemSelected(item)
  }

  private fun getProductListFromFireStore() {
    // Show the progress dialog.
    showProressDialogue()

    // Call the function of Firestore class.
    FirestoreClass().getProductsList(this@HomeFragment)
  }

  fun successProductsListFromFireStore(productsList: ArrayList<Product>){

    hideProgressDialogue()
    var recyclerView: RecyclerView = mRootView.findViewById(R.id.recyclerView_framentProduct)
    var adapter = ProductListAdapter(productsList)
    recyclerView.adapter=adapter

  }

}