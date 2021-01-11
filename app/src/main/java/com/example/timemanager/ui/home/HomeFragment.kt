package com.example.timemanager.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.timemanager.R
import com.example.timemanager.data.MDate
import com.example.timemanager.databinding.FragmentHomeBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.list = homeViewModel.listParamsEvent
        binding.timeSpentList =homeViewModel.listEventToday
        binding.timeScheduledList = homeViewModel.listEventScheduledTime
        binding.viewModel = homeViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.loadEventTodayFromDb()
        homeViewModel.loadScheduledTimeFromDb()

        MDate.date.observe(viewLifecycleOwner, Observer {
            homeViewModel.loadEventTodayFromDb()
            homeViewModel.loadScheduledTimeFromDb()
        })

        val pieChart = view.findViewById<PieChart>(R.id.chart)
        var pieDataSet = PieDataSet(homeViewModel.actionList.value, "MyPie")

        pieDataSet.colors = homeViewModel.listPieColors.value
        pieDataSet.setAutomaticallyDisableSliceSpacing(true)
        pieDataSet.sliceSpace = 2f
        pieDataSet.setDrawValues(false)
        var pieDate = PieData(pieDataSet)
        pieChart.data = pieDate
        pieChart.isRotationEnabled = false
        pieChart.setHoleColor(android.R.color.transparent)
        pieChart.holeRadius = 88f
        pieChart.isHighlightPerTapEnabled = false
        pieChart.isDragDecelerationEnabled = false
        pieChart.setDrawEntryLabels(false)
        pieChart.description.isEnabled = false
        pieChart.legend.isEnabled = false

        homeViewModel.listPieColors.observe(viewLifecycleOwner, Observer {
            pieDataSet = PieDataSet(homeViewModel.actionList.value, "MyPie")
            pieDataSet.sliceSpace = 2f
            pieDataSet.setDrawValues(false)
            pieDataSet.colors = it
            pieDataSet.setDrawValues(false)
            pieChart.setDrawEntryLabels(false)
            pieChart.setDrawSliceText(false)
            pieDate = PieData(pieDataSet)
            pieChart.data = pieDate
            pieChart.notifyDataSetChanged()
            pieChart.invalidate()
        })

        homeViewModel.actionList.observe(viewLifecycleOwner, Observer {
            pieDataSet = PieDataSet(it, "MyPie")
            pieDataSet.sliceSpace = 2f
            pieDataSet.setDrawValues(false)
            pieDataSet.colors = homeViewModel.listPieColors.value
            pieDate = PieData(pieDataSet)
            pieChart.data = pieDate
            pieChart.notifyDataSetChanged()
            pieChart.invalidate()
        })

    }
}