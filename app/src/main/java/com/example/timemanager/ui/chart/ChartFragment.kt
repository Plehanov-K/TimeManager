package com.example.timemanager.ui.chart

import android.os.Bundle
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shared.utils.parseTimeFromMinutes
import com.example.timemanager.R
import com.example.timemanager.data.CAL_MONTH
import com.example.timemanager.data.MDate
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_chart.*
import java.util.*

class ChartFragment : Fragment() {

    private lateinit var viewModel: ChartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ChartViewModel::class.java)
        return inflater.inflate(R.layout.fragment_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MDate.tempTime = CAL_MONTH
        val totalTime = view.findViewById<TextView>(R.id.text_total_time)
        val chartAdapter = RecyclerChartAdapter(this)
        recycler_chart.adapter = chartAdapter
        recycler_chart.layoutManager = LinearLayoutManager(context)
        viewModel.loadChartInfoList()
        MDate.date.observe(viewLifecycleOwner, Observer {
            viewModel.loadChartInfoList()
        })

        viewModel.listChartInfo.observe(viewLifecycleOwner, Observer {
            totalTime.text =
                getString(R.string.total_time) + viewModel.totalTime.parseTimeFromMinutes()
            chartAdapter.setEvents(it)
        })
    }
}