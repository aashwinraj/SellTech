package com.example.selltech

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.selltech.firestore.FirestoreClass
import com.example.selltech.models.CartItem
import com.example.selltech.ui.CartActivity
import com.example.selltech.ui.home.MyOrdersFragment

class MyordersAdapter(var orderList: ArrayList<CartItem>) :
    RecyclerView.Adapter<MyordersAdapter.Viewholder>() {

var context:Context? = null


    inner class Viewholder(view:View) :RecyclerView.ViewHolder(view){
        var CartType: TextView = view.findViewById(R.id.tv_cartItemType)
        var CartBrand: TextView = view.findViewById(R.id.tv_cartItemBrand)
        var CartPrice: TextView = view.findViewById(R.id.tv_cartItemPrice)
        var CartQuantity: TextView = view.findViewById(R.id.tv_cartItemQuantity)
        var deleteItem: ImageView = view.findViewById(R.id.deleteCartItem)
        var CartImage: ImageView = view.findViewById(R.id.iv_cart_image)
        var Cartstatus:TextView = view.findViewById(R.id.status)
        var CartCancel:TextView = view.findViewById(R.id.cancelOrder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyordersAdapter.Viewholder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.model_cart, parent, false)

        return Viewholder(view)
    }

    override fun onBindViewHolder(holder: MyordersAdapter.Viewholder, position: Int) {
        GlideLoader().loadProductPicture(
            holder.itemView.context,
            orderList[position].itemImage,
            holder.CartImage
        )

        holder.CartType.text = orderList[position].itemType
        holder.CartBrand.text = orderList[position].itemBrand
        holder.CartPrice.text = "${orderList[position].itemPrice} Rupees"
        holder.CartQuantity.text = orderList[position].cartQuantity
        holder.deleteItem.visibility=View.GONE
        holder.Cartstatus.visibility=View.VISIBLE
        holder.CartCancel.visibility=View.GONE


    }

    override fun getItemCount(): Int {
        return orderList.size
    }


}


