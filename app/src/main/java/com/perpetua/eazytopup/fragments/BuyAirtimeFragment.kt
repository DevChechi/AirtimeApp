package com.perpetua.eazytopup.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.navigation.fragment.navArgs
import com.google.android.material.appbar.MaterialToolbar
import com.perpetua.eazytopup.databinding.FragmentBuyAirtimeBinding

class BuyAirtimeFragment : Fragment() {
    private var _binding: FragmentBuyAirtimeBinding? = null
    private val binding get() = _binding!!
    val args: BuyAirtimeFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentBuyAirtimeBinding.inflate(inflater, container, false)
        val toolbar: MaterialToolbar = binding.toolbar
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.setDisplayShowHomeEnabled(true)
        val buyFor = args.buyFor
        if(buyFor.equals("self")){
            binding.buyForSelfLayout.visibility = View.GONE
            binding.buyAirtimeText.text = ""
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}