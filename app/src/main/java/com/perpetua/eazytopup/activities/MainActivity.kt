package com.perpetua.eazytopup.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.perpetua.eazytopup.R
import android.view.ViewGroup

import android.view.MotionEvent

import android.view.View.OnTouchListener
import android.view.inputmethod.InputMethodManager

import android.widget.EditText




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        val decorView: View = window.decorView
        val uiOptions: Int = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        decorView.setSystemUiVisibility(uiOptions)
        decorView.setBackgroundColor(resources.getColor(android.R.color.white))
        setContentView(R.layout.activity_main)

    }

    fun View.hideSoftKeyboard(){
        val imm = context.getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager

        if(imm.isAcceptingText){
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }

    }


}