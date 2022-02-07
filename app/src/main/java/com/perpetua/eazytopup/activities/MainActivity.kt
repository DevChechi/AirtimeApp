package com.perpetua.eazytopup.activities

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log.d
import android.view.View
import android.view.WindowManager
import com.perpetua.eazytopup.R
import android.view.ViewGroup

import android.view.MotionEvent

import android.view.View.OnTouchListener
import android.view.inputmethod.InputMethodManager

import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext


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
}