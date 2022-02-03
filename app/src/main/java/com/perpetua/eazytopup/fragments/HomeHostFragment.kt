package com.perpetua.eazytopup.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.perpetua.eazytopup.R
import com.perpetua.eazytopup.databinding.FragmentHomeHostBinding


class HomeHostFragment : Fragment() {
    var _binding: FragmentHomeHostBinding? = null
    private val binding get() = _binding!!
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeHostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bottomNavigationView = binding.bottomNavigationView
        val hostFragment = childFragmentManager.findFragmentById(binding.homeHostNavHostFragment.id)
        val navController = hostFragment?.findNavController()
        if(navController != null){
            bottomNavigationView.setupWithNavController(navController)
        }

        val drawerLayout = binding.drawerLayout
        val toolBar = binding.toolbar
        toggle = ActionBarDrawerToggle(activity, drawerLayout,toolBar, R.string.drawer_open, R.string.drawer_close)
        drawerLayout.addDrawerListener(toggle)
    }

    override fun onResume() {
        super.onResume()
        toggle.syncState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}