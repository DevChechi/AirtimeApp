package com.perpetua.eazytopup.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val buyFor = args.buyFor
        if(buyFor.equals("self")){
            binding.buyForSelfLayout.visibility = View.GONE
            binding.buyAirtimeText.text = ""
        }
        setupUI(view)
        binding.buyForOthers.setOnClickListener {

        }
    }
    fun View.hideSoftKeyboard(){
        val imm = context.getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager

        if(imm.isAcceptingText){
            imm.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
        }

    }
    fun setupUI(view: View) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener { v, event ->
                v.hideSoftKeyboard()
                false
            }
        }

        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(innerView)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}