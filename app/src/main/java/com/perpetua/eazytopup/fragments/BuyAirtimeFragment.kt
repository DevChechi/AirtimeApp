package com.perpetua.eazytopup.fragments

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log.d
import android.util.Log.i
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputLayout
import com.perpetua.eazytopup.databinding.FragmentBuyAirtimeBinding
import java.lang.StringBuilder

class BuyAirtimeFragment : Fragment() {
    private var _binding: FragmentBuyAirtimeBinding? = null
    private val binding get() = _binding!!
    private lateinit var myPhoneNumber: String
    private lateinit var phoneNumberToTopup: String
    private lateinit var amount: String
    val args: BuyAirtimeFragmentArgs by navArgs()
    val phoneNumberPattern =
        "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$"
    val amountRegex = "^0+(?!\$)"

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
        setupUI(view)

        if(buyFor.equals("others")){
            binding.buyForOthers.setOnClickListener {
                myPhoneNumber = binding.myPhoneNumber.text.toString().trim()
                phoneNumberToTopup = binding.phoneNumberToTopup.text.toString().trim()
                amount =binding.amount.text.toString().trim()
                val transactionCost = 32
                if(!validateAmount(amount, binding.amountField) or
                    !validatePhoneNumber(myPhoneNumber, binding.phoneNumberField) or
                    !validatePhoneNumber(phoneNumberToTopup, binding.phoneNumberTopupField) ){
                    return@setOnClickListener
                }
                val stringBuilder = StringBuilder(amount)
                while(stringBuilder.isNotEmpty() && stringBuilder[0] == '0'){
                    stringBuilder.deleteCharAt(0)
                }
                showRationaleDialog(
                    "Confirm",
                    "Buy airtime for $myPhoneNumber \n Amount: $stringBuilder \t Transaction cost: $transactionCost",
                    "Ok"){ dialog, which ->
                    Toast.makeText(requireContext(), "Successful, wait for Mpesa prompt", Toast.LENGTH_LONG).show()
                }

            }
        }
        if(buyFor.equals("self")){
            binding.buyForOthersLayout.visibility = View.GONE
            binding.buyAirtimeText.text = ""
            val transactionCost = 32
            binding.buyForOthers.setOnClickListener {
                myPhoneNumber = binding.myPhoneNumber.text.toString().trim()
                amount =binding.amount.text.toString().trim()
                if(!validateAmount(amount, binding.amountField) or
                    !validatePhoneNumber(myPhoneNumber, binding.phoneNumberField) ){
                    return@setOnClickListener
                }
                val stringBuilder = StringBuilder(amount)
                while(stringBuilder.isNotEmpty() && stringBuilder[0] == '0'){
                    stringBuilder.deleteCharAt(0)
                }

                showRationaleDialog(
                    "Confirm",
                    "Buy airtime for  0$myPhoneNumber \n Amount: ${stringBuilder.toString()} \t Trasaction cost: ${transactionCost.toString()}",
                    "Ok"){ dialog, which ->
                    Toast.makeText(requireContext(), "Successful, wait for Mpesa prompt", Toast.LENGTH_LONG).show()
                }

            }
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

    fun validatePhoneNumber(phoneNumber: String, inputLayout: TextInputLayout): Boolean{
        val validPhoneNumber = (inputLayout.prefixText.toString().plus(phoneNumber))
        return if(validPhoneNumber.isEmpty()){
            inputLayout.error = "Phone number cannot be empty"
            false
        }else if(validPhoneNumber.matches(phoneNumberPattern.toRegex())){
            i("Buy airtime fragment", "valid phone number $validPhoneNumber")
            true
        }else{
            inputLayout.error = "Invalid phone number"
            false
        }
    }

    fun validateAmount(amount: String, inputLayout: TextInputLayout): Boolean{
        val stringBuilder = StringBuilder(amount)
        while(stringBuilder.isNotEmpty() && stringBuilder[0] == '0'){
            stringBuilder.deleteCharAt(0)
        }
        val validAmount = stringBuilder.toString()
        i("Buy Airtime Fragment", "Valid amount $validAmount")
        return if( validAmount.isEmpty()){
            inputLayout.error = "Amount cannot be empty"
            false
        } else {
            true
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

    private fun showRationaleDialog(
        title: String,
        message: String,
        postiveButton: String,
        listener: DialogInterface.OnClickListener
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton(postiveButton, listener)
            .setNegativeButton("Edit") { dialog, which ->
                d("Buy Airtime fragment", "Edit details")
            }
        builder.create().show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}