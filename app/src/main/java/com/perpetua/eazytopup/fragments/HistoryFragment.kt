package com.perpetua.eazytopup.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.perpetua.eazytopup.R
import com.perpetua.eazytopup.adapters.FavoritesAdapter
import com.perpetua.eazytopup.adapters.TransactionsAdapter
import com.perpetua.eazytopup.databinding.FragmentHistoryBinding
import com.perpetua.eazytopup.models.Contact
import com.perpetua.eazytopup.models.TransactionStatement


class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var transactionsAdapter: TransactionsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpRecyclerViewAdapter()
        binding.totalAirtimeToday.text = "200"
        binding.totalAirtimeWeek.text = "1000"
        binding.totalPointsStatement.text = "28000"
    }

    fun setUpRecyclerViewAdapter(){
        transactionsAdapter = TransactionsAdapter()
        binding.transactionsList.apply {
            adapter = transactionsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        transactionsAdapter.differ.submitList(dummytransactions())
    }

    fun dummytransactions(): List<TransactionStatement>{
        val transactionsList = mutableListOf<TransactionStatement>()
        for(i: Int in 0..15){
            val transaction = TransactionStatement(
                i.toString(),
                "James Smith",
            "0712345678",
                "50",
                System.currentTimeMillis().toString()
            )
            transactionsList.add(transaction)
        }
        return transactionsList
    }

}