package com.perpetua.eazytopup.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.perpetua.eazytopup.adapters.TransactionsAdapter
import com.perpetua.eazytopup.databinding.FragmentHistoryBinding
import com.perpetua.eazytopup.models.TransactionStatement
import java.text.*
import java.util.*


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
        val dailyAirtime = 1000
        val monthyAirtime = 12000
        val numberFormat  = NumberFormat.getCurrencyInstance()
        val decimalFormatSymbols: DecimalFormatSymbols = (numberFormat as DecimalFormat).decimalFormatSymbols
        decimalFormatSymbols.currencySymbol = "KSH. "
        (numberFormat as DecimalFormat).decimalFormatSymbols = decimalFormatSymbols

        val todaySpend = numberFormat.format(dailyAirtime)
        val monthlySpend = numberFormat.format(monthyAirtime)

        val c = Calendar.getInstance()
        val monthMaxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH)
        val average = monthyAirtime / monthMaxDays
        val averageSpend = numberFormat.format(average)



        binding.totalAirtimeMonth.text = monthlySpend
        binding.averageSpend.text = averageSpend
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