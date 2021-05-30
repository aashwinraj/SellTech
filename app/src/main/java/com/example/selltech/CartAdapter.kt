package com.example.selltech

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.selltech.firestore.FirestoreClass
import com.example.selltech.models.CartItem
import com.example.selltech.ui.CartActivity

class CartAdapter(var cartList: ArrayList<CartItem>) :
    RecyclerView.Adapter<CartAdapter.Viewholder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.Viewholder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.model_cart, parent, false)

        return Viewholder(view)
    }


    inner class Viewholder(view: View) : RecyclerView.ViewHolder(view) {

        var CartType: TextView = view.findViewById(R.id.tv_cartItemType)
        var CartBrand: TextView = view.findViewById(R.id.tv_cartItemBrand)
        var CartPrice: TextView = view.findViewById(R.id.tv_cartItemPrice)
        var CartQuantity: TextView = view.findViewById(R.id.tv_cartItemQuantity)
        var deleteItem: ImageView = view.findViewById(R.id.deleteCartItem)
        var CartImage: ImageView = view.findViewById(R.id.iv_cart_image)

    }

    override fun onBindViewHolder(holder: CartAdapter.Viewholder, position: Int) {

        GlideLoader().loadProductPicture(
            holder.itemView.context,
            cartList[position].itemImage,
            holder.CartImage
        )

        holder.CartType.text = cartList[position].itemType
        holder.CartBrand.text = cartList[position].itemBrand
        holder.CartPrice.text = "${cartList[position].itemPrice} Rs"
        holder.CartQuantity.text = cartList[position].cartQuantity

        holder.deleteItem.setOnClickListener {
          //  Toast.makeText(holder.itemView.context, cartList[position].cartId, Toast.LENGTH_LONG).show()
            when(holder.itemView.context){
                is CartActivity ->
                {
                    (holder.itemView.context as CartActivity).showProressDialogue()
                }
            }
 FirestoreClass().removeItemfromCart(holder.itemView.context,cartList[position].cartId)
        }

    }

    override fun getItemCount(): Int {
        return cartList.size
    }
}


