package com.perpetua.eazytopup.fragments

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputLayout
import com.perpetua.eazytopup.databinding.FragmentBuyAirtimeBinding
import java.lang.StringBuilder

import androidx.core.app.ActivityCompat


class BuyAirtimeFragment : Fragment() {
    private val TAG: String? = "BuyAirtimeFragment"
    private val REQUEST_PERMISSIONS_REQUEST_CODE: Int = 1
    private var _binding: FragmentBuyAirtimeBinding? = null
    private val binding get() = _binding!!
    private lateinit var toolbar: MaterialToolbar
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
        toolbar = binding.toolbar
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.setDisplayShowHomeEnabled(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val buyFor = args.buyFor
        setupUI(view)
        binding.phoneNumberTopupField.setEndIconOnClickListener {
            it.hideSoftKeyboard()
            Toast.makeText(activity, "end icon clicked", Toast.LENGTH_LONG).show()
            if (!checkPermissions()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions()
                }
            }
            else {
                readContacts()

            }
        }

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
                    "EDIT",
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
                    "EDIT",
                    "Ok"){ dialog, which ->
                    Toast.makeText(requireContext(), "Successful, wait for Mpesa prompt", Toast.LENGTH_LONG).show()
                }

            }
        }

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
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
        negativeButton: String,
        postiveButton: String,
        listener: DialogInterface.OnClickListener
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton(postiveButton, listener)
            .setNegativeButton(negativeButton) { dialog, which ->
                d(TAG, "Dialogue cancelled")
            }
        builder.create().show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun checkPermissions(): Boolean{
        val permissionState = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS)
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermissions(){
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_CONTACTS )
        if(shouldProvideRationale){
            d("Contacts Permission", "Providing permission rationale to give user more info")
            showRationaleDialog(
                "We need permission to read your contacts",
                "This will allow you to search a contact from your contacts instead of entering their number. ",
                "CANCEL",
                "OK"
            ) { dialog, which ->
                startReadContactsPermissionRequest()
            }
        }else{
            d("Contacts Permission", "Requesting READ_CONTACTS Permission")
            startReadContactsPermissionRequest()
        }
    }
    private fun startReadContactsPermissionRequest() {
        requestPermissions(arrayOf(android.Manifest.permission.READ_CONTACTS), REQUEST_PERMISSIONS_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        d(TAG, "OnRequestPermissionResult")
        d(TAG, "Request code: $requestCode")
        d(TAG, "Grant Results is empty ${grantResults.isEmpty()}")
        d(TAG, "Permission granted ${grantResults[0] == PackageManager.PERMISSION_GRANTED}")
        if(requestCode == REQUEST_PERMISSIONS_REQUEST_CODE){
            when{
                grantResults.isEmpty() -> {
                    d(TAG, "User interaction was cancelled")
                }
                grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    readContacts()
                }else ->{
                d(TAG, "Permission denied")
                showRationaleDialog("Permission denied",
                    "Search contacts requires permission to access your contacts. To give this app permission to access contacts, go to settings -> Permissions and turn contacts permission on",
                    "CANCEL",
                    "SETTINGS"
                ) { dialog, which ->
                    requireContext().openAppSystemSettings()
                }
            }
            }
        }
    }

    fun Context.openAppSystemSettings() {
        startActivity(Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", packageName, null)
        })
    }

    fun readContacts(){
        i(TAG, "Ready to read contacts")
    }

}