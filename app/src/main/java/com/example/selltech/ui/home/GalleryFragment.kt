package com.example.selltech.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.example.selltech.AddProductActivity
import com.example.selltech.MyProductListAdapter
import com.example.selltech.R
import com.example.selltech.firestore.FirestoreClass
import com.example.selltech.models.Product
import com.example.selltech.ui.CartActivity
import java.util.ArrayList

class GalleryFragment: BaseFragment()  {


  private lateinit var mRootView: View

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    mRootView = inflater.inflate(R.layout.fragment_gallery, container, false)
    return mRootView
  }

  override fun onResume() {
    super.onResume()
    getMYProductListFromFireStore()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setHasOptionsMenu(true)
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.add_product_menu, menu)
    inflater.inflate(R.menu.cart_menu,menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {


    val id = item.itemId
    when (id) {
      R.id.add_product_menu -> {
        startActivity(Intent(activity, AddProductActivity::class.java))
      }
      R.id.cart_menu-> {
        startActivity(Intent(activity, CartActivity::class.java))
      }
    }

    return super.onOptionsItemSelected(item)
  }

  private fun getMYProductListFromFireStore() {
    // Show the progress dialog.
    showProressDialogue()

    // Call the function of Firestore class.
    FirestoreClass().getMyProductsList(this@GalleryFragment)
  }

  fun successProductsListFromFireStore(productsList: ArrayList<Product>) {

    hideProgressDialogue()
    var recyclerView: RecyclerView = mRootView.findViewById(R.id.recyclerView_framentgallery)
    var adapter = MyProductListAdapter(productsList)
    recyclerView.adapter = adapter


  }


}