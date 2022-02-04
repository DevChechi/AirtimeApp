package com.perpetua.eazytopup.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.perpetua.eazytopup.R
import com.perpetua.eazytopup.adapters.RedeemPointsAdapter
import com.perpetua.eazytopup.databinding.FragmentFavoritesBinding
import com.perpetua.eazytopup.databinding.FragmentPointsBinding
import com.perpetua.eazytopup.models.RedeemPoints


class PointsFragment : Fragment() {
    private var _binding: FragmentPointsBinding? = null
    private val binding get() = _binding!!
    lateinit var redeemPointsAdapter: RedeemPointsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPointsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpRecyclerViewAdapter()
        binding.totalPoints.text = "28000"
    }

    fun setUpRecyclerViewAdapter(){
        redeemPointsAdapter = RedeemPointsAdapter()
        binding.favoritesList.apply {
            adapter = redeemPointsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        redeemPointsAdapter.differ.submitList(dummyFavorites())
    }

    fun dummyFavorites(): List<RedeemPoints>{
        val redeemPointsList = mutableListOf<RedeemPoints>()
        for(i: Int in 0..15){
            val RedeemPoints = RedeemPoints(
                i.toString(),
                "200",
                "50"
            )
            redeemPointsList.add(RedeemPoints)
        }
        return redeemPointsList
    }

}