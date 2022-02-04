package com.perpetua.eazytopup.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.perpetua.eazytopup.R
import com.perpetua.eazytopup.adapters.FavoritesAdapter
import com.perpetua.eazytopup.databinding.FragmentFavoritesBinding
import com.perpetua.eazytopup.models.Contact


class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    lateinit var favoritesAdapter: FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpRecyclerViewAdapter()
        favoritesAdapter.setOnItemClickListener {
            parentFragment?.parentFragment?.findNavController()?.navigate(R.id.action_homeHostFragment_to_buyAirtimeFragment)
        }
    }

    fun setUpRecyclerViewAdapter(){
        favoritesAdapter = FavoritesAdapter()
        binding.favoritesList.apply {
            adapter = favoritesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        favoritesAdapter.differ.submitList(dummyFavorites())
    }

    fun dummyFavorites(): List<Contact>{
        val contactList = mutableListOf<Contact>()
        for(i: Int in 0..15){
            val contact = Contact("John Doe", "0712345678")
            contactList.add(contact)
        }
        return contactList
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
            .setNegativeButton("Cancel") { dialog, which ->
                Log.d("Buy Airtime fragment", "Cancel dialogue")
            }
        builder.create().show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}