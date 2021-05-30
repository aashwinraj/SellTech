package com.example.selltech.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.selltech.R
import com.example.selltech.firestore.FirestoreClass
import com.example.selltech.models.CartItem
import com.example.selltech.models.Users
import com.example.selltech.util.Constants
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User


private val mfireStore = FirebaseFirestore.getInstance()
private lateinit var mRootView: View

class ProfileFragment :BaseFragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView=inflater.inflate(R.layout.fragment_profile,container,false)
        return mRootView
    }

    override fun onResume() {

        super.onResume()
         setUserDetails()
    }

fun setUserDetails() {
    var name: TextView = mRootView.findViewById(R.id.et_box_name)
    var Email: TextView = mRootView.findViewById(R.id.et_box_email)
    var Phone: TextView = mRootView.findViewById(R.id.et_box_phone)
    var user:Users?=Users()
    mfireStore.collection("users").whereEqualTo("id",FirestoreClass().getCurrentUserid())
        .get().addOnSuccessListener { document ->



            for (i in document.documents) {
                 user = i.toObject(Users::class.java)!!
            }
           // Toast.makeText( mRootView.context,user!!.id,Toast.LENGTH_LONG).show()
            name.text=user?.name
            Email.text=user?.email
             Phone.text=user?.mobile





        }.addOnFailureListener {

        }
    //name.text = user.name


            //Phone.text = user?.phone





}}