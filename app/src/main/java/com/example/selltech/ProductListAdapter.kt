package com.example.selltech

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.selltech.models.Product
import com.example.selltech.ui.ProductDetailsActivity

open class ProductListAdapter(var productList: ArrayList<Product>) :
    RecyclerView.Adapter<ProductListAdapter.Viewolder>() {



    inner class Viewolder(view: View) : RecyclerView.ViewHolder(view)  {
        var productType: TextView = view.findViewById(R.id.textView_productType)
        var productBrand: TextView = view.findViewById(R.id.textView_brandName)
        var productPrice: TextView = view.findViewById(R.id.textView_price)
        var productUsedtime: TextView = view.findViewById(R.id.textView_usedFor)
        var productImage:ImageView = view.findViewById(R.id.imageView_product)

        // var productDescription:TextView=view.findViewById(R.id.textVIew_description)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.model_product, parent, false)

        return Viewolder(view)
    }

    override fun onBindViewHolder(holder: Viewolder, position: Int) {
        GlideLoader().loadProductPicture(holder.itemView.context,productList[position].image, holder.productImage)
        holder.productType.text = productList[position].product_type
        holder.productBrand.text = productList[position].brand
        holder.productPrice.text = " ${ productList[position].price}  Rs"
        holder.productUsedtime.text = productList[position].used_time

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,ProductDetailsActivity::class.java)
            intent.putExtra("Product_ID",productList[position].product_id)
            intent.putExtra("User_ID",productList[position].user_id)
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return productList.size
    }


}
