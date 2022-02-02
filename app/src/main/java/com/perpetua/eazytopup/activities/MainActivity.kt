package com.perpetua.eazytopup.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.perpetua.eazytopup.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val decorView: View = window.decorView
        val uiOptions: Int = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        decorView.setSystemUiVisibility(uiOptions)
        decorView.setBackgroundColor(resources.getColor(android.R.color.white))
        setContentView(R.layout.activity_main)
    }
}