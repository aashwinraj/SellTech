package com.example.selltech.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.selltech.*
import com.example.selltech.databinding.FragmentSlideshowBinding
import com.example.selltech.firestore.FirestoreClass
import com.example.selltech.models.CartItem
import com.example.selltech.models.Order
import com.example.selltech.ui.CartActivity
import com.example.selltech.ui.slideshow.SlideshowViewModel
import com.google.firebase.firestore.FirebaseFirestore


class MyOrdersFragment :BaseFragment() {
    private val mfireStore = FirebaseFirestore.getInstance()
    private lateinit var mRootView: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        mRootView=inflater.inflate(R.layout.fragment_my_orders,container,false)
        return mRootView
    }


fun populateOrdersList(orderList:ArrayList<CartItem>){
    hideProgressDialogue()
    var recyclerView: RecyclerView = mRootView.findViewById(R.id.recyclerView_orders)
    var  adapter = MyordersAdapter(orderList)
    recyclerView.adapter = adapter


}

    fun getMyOrderList(){
        showProressDialogue()
        FirestoreClass().getMyOrderList(this)
    }


    override fun onResume() {
        getMyOrderList()
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

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

}