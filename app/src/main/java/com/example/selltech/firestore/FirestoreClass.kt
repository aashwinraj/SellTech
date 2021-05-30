package com.example.selltech.firestore

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.selltech.AddProductActivity
import com.example.selltech.RegisterActivity
import com.example.selltech.UserProfileActivity
import com.example.selltech.models.CartItem
import com.example.selltech.models.Order
import com.example.selltech.models.Product
import com.example.selltech.models.Users
import com.example.selltech.ui.CartActivity
import com.example.selltech.ui.ProductDetailsActivity
import com.example.selltech.ui.home.GalleryFragment
import com.example.selltech.ui.home.HomeFragment
import com.example.selltech.ui.home.MyOrdersFragment
import com.example.selltech.ui.home.ProfileFragment
import com.example.selltech.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirestoreClass {

    private val mfireStore = FirebaseFirestore.getInstance()
    var productsList: ArrayList<Product> = ArrayList()
    var cartList: ArrayList<CartItem> = ArrayList()
    var subtotal: Int = 0
    var Price: Int = 0



    fun getMyOrderList(fragment:MyOrdersFragment){
        mfireStore.collection(Constants.ORDERS_ITEMS).whereEqualTo("userID",getCurrentUserid())
            .get().addOnSuccessListener {document->
                var list:ArrayList<CartItem> = ArrayList()
                for(i in document.documents)
                {

                    val orderItem = i.toObject(Order::class.java)
                    orderItem?.id =i.id
                    list=orderItem!!.items



                }
                fragment.populateOrdersList(list)

            }.addOnFailureListener {
                fragment.hideProgressDialogue()
            }
    }



fun addOrdertoCloud(activity: CartActivity,order: Order){
    mfireStore.collection(Constants.ORDERS_ITEMS).document().set(order, SetOptions.merge())
        .addOnSuccessListener {
activity.orderPlacedSuccess()
        }
        .addOnFailureListener {
activity.hideProgressDialogue()
        }
}

    fun removeItemfromCart(context: Context,cartId:String){
        mfireStore.collection(Constants.CART_ITEMS)
            .document(cartId)
            .delete()
            .addOnSuccessListener {

when(context){
    is CartActivity ->
    {
        context.cartItemRemovedSuccess()
    }
}


            }.addOnFailureListener {

                when(context){
                    is CartActivity ->
                    {
                        context.hideProgressDialogue()
                    }
                }

            }
    }
    fun getCartListfromCloud(activity: CartActivity) {
        mfireStore.collection(Constants.CART_ITEMS)
            .whereEqualTo("itemuserID", FirestoreClass().getCurrentUserid())
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->
                for (i in document.documents) {
                    var cartproduct = i.toObject(CartItem::class.java)!!
                    cartproduct.cartId = i.id
                    cartList.add(cartproduct)

                    Price = cartproduct.itemPrice.toInt()
                    subtotal = subtotal + Price


                }
                activity.CartListfromCloudSuccess(cartList)



                activity.cartBill(subtotal)


            }
            .addOnFailureListener { e ->
                activity.hideProgressDialogue()
            }
    }

    fun checkItemInCart(activity: ProductDetailsActivity, productId: String) {

        mfireStore.collection(Constants.CART_ITEMS)
            .whereEqualTo(Constants.USER_ID, getCurrentUserid())
            .whereEqualTo("itemproductID", productId).get().addOnSuccessListener { document ->
                if (document.documents.size > 0) {
                    val productsList: ArrayList<Product> = ArrayList()

                    // A for loop as per the list of documents to convert them into Products ArrayList.
                    for (i in document.documents) {

                        val product = i.toObject(Product::class.java)
                        product!!.product_id = i.id

                        productsList.add(product)
                    }
                    Toast.makeText(activity, productsList[0].toString(), Toast.LENGTH_LONG).show()


                    activity.productDoExistInCart()
                } else {
                    activity.hideProgressDialogue()

                }

            }.addOnFailureListener {
                activity.hideProgressDialogue()
                Toast.makeText(activity, "Someting went wrongggggggggggggg", Toast.LENGTH_LONG)
                    .show()
            }

    }

    fun addCartItemToCloud(activity: ProductDetailsActivity, cartItem: CartItem) {

        mfireStore.collection(Constants.CART_ITEMS).document().set(cartItem, SetOptions.merge())
            .addOnSuccessListener {
                activity.addtoCartSuccess()
            }
            .addOnFailureListener {
                activity.hideProgressDialogue()
            }


    }


    fun getProductDetails(activity: ProductDetailsActivity, productId: String) {

        mfireStore.collection(Constants.PRODUCTS).document(productId)
            .get()
            .addOnSuccessListener { document ->
                val product: Product = document.toObject(Product::class.java)!!
                activity.productDetailsSuccess(product)
            }
            .addOnFailureListener {
                activity.hideProgressDialogue()
            }


    }

    fun registerUser(activity: RegisterActivity, userInfo: Users) {
        userInfo.id?.let {
            mfireStore.collection(Constants.USERS).document().set(userInfo, SetOptions.merge())
                .addOnSuccessListener {
                    activity.userRegistrationSuccess()
                }
                .addOnFailureListener {
                    activity.hideProgressDialogue()
                    Toast.makeText(activity, " registration failed", Toast.LENGTH_LONG).show()

                }
        }
    }


    fun getMyProductsList(fragment: Fragment) {
        // The collection name for PRODUCTS
        mfireStore.collection(Constants.PRODUCTS)
            .whereEqualTo(Constants.USER_ID, getCurrentUserid())
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->

                // Here we get the list of boards in the form of documents.
                Log.e("Products List", document.documents.toString())

                // Here we have created a new instance for Products ArrayList.
                val productsList: ArrayList<Product> = ArrayList()

                // A for loop as per the list of documents to convert them into Products ArrayList.
                for (i in document.documents) {

                    val product = i.toObject(Product::class.java)
                    product!!.product_id = i.id

                    productsList.add(product)
                }

                when (fragment) {
                    is GalleryFragment -> {
                        fragment.successProductsListFromFireStore(productsList)
                    }
                }
            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is any error based on the base class instance.
                when (fragment) {
                    is GalleryFragment -> {
                        fragment.hideProgressDialogue()
                    }
                }
                Log.e("Get Product List", "Error while getting product list.", e)
            }
    }

    fun getCurrentUserid(): String {
        val currentuser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""
        if (currentuser != null) {
            currentUserID = currentuser.uid
        }
        return currentUserID
    }

    fun getUserDetails(activity: Activity) {

        mfireStore.collection(Constants.USERS).document(getCurrentUserid())
            .get().addOnSuccessListener { document ->
                //Toast.makeText(activity,document.toString(),Toast.LENGTH_LONG).show()

                val user = document.toObject(Users::class.java)

                val sharedPreferneces = activity.getSharedPreferences(
                    Constants.PREFERENCES,
                    Context.MODE_PRIVATE
                )

                val editor: SharedPreferences.Editor = sharedPreferneces.edit()
                //key:Value loed_IN_username:abdul
                editor.putString(
                    Constants.LOGGED_IN_USERNAME, "${user?.name}"
                )

                editor.apply()

            }


    }


    fun uploadImageToCloudStorage(activity: Activity, imageFileURI: Uri?, imageType: String) {

        // getting the storage reference
        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            imageType + System.currentTimeMillis() + "."
                    + "jpg"

        )

        // adding the file to reference
        sRef.putFile(imageFileURI!!)
            .addOnSuccessListener { taskSnapshot ->
                // The image upload is success


                // Get the downloadable url from the task snapshot
                taskSnapshot.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { uri ->


                        // Here call a function of base activity for transferring the result to it.
                        when (activity) {
                            is UserProfileActivity -> {
                                //activity.imageUploadSuccess(uri.toString())
                            }
                            is AddProductActivity -> {
                                activity.imageUploadSuccess(uri.toString())

                            }
                        }
                    }
            }
            .addOnFailureListener { exception ->

                // Hide the progress dialog if there is any error. And print the error in log.
                when (activity) {
                    is UserProfileActivity -> {
                        activity.hideProgressDialogue()
                    }
                    is AddProductActivity -> {
                        activity.hideProgressDialogue()
                    }
                }

                Log.e(
                    activity.javaClass.simpleName,
                    exception.message,
                    exception
                )
            }
    }

    fun uploadProductToCloud(activity: AddProductActivity, productInfo: Product) {

        mfireStore.collection(Constants.PRODUCTS).document().set(productInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.productUploadSuccess()
            }
            .addOnFailureListener {
                activity.hideProgressDialogue()
            }
    }


    fun getProductsList(fragment: Fragment) {
        // The collection name for PRODUCTS
        mfireStore.collection(Constants.PRODUCTS)

            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->

                // Here we get the list of boards in the form of documents.
                Log.e("Products List", document.documents.toString())

                // Here we have created a new instance for Products ArrayList.
                val productsList: ArrayList<Product> = ArrayList()

                // A for loop as per the list of documents to convert them into Products ArrayList.
                for (i in document.documents) {

                    val product = i.toObject(Product::class.java)
                    product!!.product_id = i.id

                    productsList.add(product)
                }

                when (fragment) {
                    is HomeFragment -> {
                        fragment.successProductsListFromFireStore(productsList)
                    }
                }
            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is any error based on the base class instance.
                when (fragment) {
                    is HomeFragment -> {
                        fragment.hideProgressDialogue()
                    }
                }
                Log.e("Get Product List", "Error while getting product list.", e)
            }
    }

    fun getProductItemsList(fragment: HomeFragment) {
        // The collection name for PRODUCTS
        mfireStore.collection(Constants.PRODUCTS)
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->

                // Here we get the list of boards in the form of documents.
                // Log.e(fragment.javaClass.simpleName, document.documents.toString())

                // Here we have created a new instance for Products ArrayList.
                val productsList: ArrayList<Product> = ArrayList()

                // A for loop as per the list of documents to convert them into Products ArrayList.
                for (i in document.documents) {

                    val product = i.toObject(Product::class.java)!!
                    product.product_id = i.id
                    productsList.add(product)
                }

                // Pass the success result to the base fragment.
                fragment.successProductsListFromFireStore(productsList)
            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is any error which getting the dashboard items list.
                fragment.hideProgressDialogue()
                Log.e(fragment.javaClass.simpleName, "Error while getting dashboard items list.", e)
            }
    }


}