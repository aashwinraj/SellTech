package com.example.selltech.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.selltech.R
import com.example.selltech.databinding.FragmentSlideshowBinding
import com.example.selltech.ui.slideshow.SlideshowViewModel


class ContactFragment : Fragment() {
    private lateinit var mRootView: View


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView=inflater.inflate(R.layout.fragment_contact,container,false)
return mRootView
    }


}