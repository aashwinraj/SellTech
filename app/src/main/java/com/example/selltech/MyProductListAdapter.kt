package com.example.selltech

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.selltech.models.Product

open class MyProductListAdapter(var productList: ArrayList<Product> ) :
    RecyclerView.Adapter<MyProductListAdapter.Viewolder>() {



    inner class Viewolder(view: View) : RecyclerView.ViewHolder(view)  {
        var productType: TextView = view.findViewById(R.id.textView_productType)
        var productBrand: TextView = view.findViewById(R.id.textView_brandName)
        var productPrice: TextView = view.findViewById(R.id.textView_price)
        var productUsedtime: TextView = view.findViewById(R.id.textView_usedFor)
        var productImage:ImageView = view.findViewById(R.id.imageView_product)



    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.model_my_product, parent, false)

        return Viewolder(view)
    }



    override fun getItemCount(): Int {
        return productList.size
    }



    override fun onBindViewHolder(holder: Viewolder, position: Int) {
        GlideLoader().loadProductPicture(holder.itemView.context,productList[position].image, holder.productImage)
        holder.productType.text = productList[position].product_type
        holder.productBrand.text = productList[position].brand
        holder.productPrice.text = " ${ productList[position].price}  Rs"
        holder.productUsedtime.text = productList[position].used_time







    }


}


