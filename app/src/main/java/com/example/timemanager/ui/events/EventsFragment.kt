package com.example.timemanager.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.timemanager.R
import com.example.timemanager.data.CAL_DAY
import com.example.timemanager.data.MDate
import com.example.timemanager.databinding.FragmentEventsBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet


class EventsFragment : Fragment() {
    private lateinit var binding: FragmentEventsBinding
    private val eventsViewModel: EventsViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_events, container, false)
        binding.list = eventsViewModel.listParamsEvent
        binding.timeSpentList =eventsViewModel.listEventToday
        binding.timeScheduledList = eventsViewModel.listEventScheduledTime
        binding.viewModel = eventsViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MDate.tempTime = CAL_DAY
        eventsViewModel.loadEventTodayFromDb()
        eventsViewModel.loadScheduledTimeFromDb()

        MDate.date.observe(viewLifecycleOwner, Observer {
            eventsViewModel.loadEventTodayFromDb()
            eventsViewModel.loadScheduledTimeFromDb()
        })

        val pieChart = view.findViewById<PieChart>(R.id.chart)
        var pieDataSet = PieDataSet(eventsViewModel.actionList.value, "MyPie")

        pieDataSet.colors = eventsViewModel.listPieColors.value
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

        eventsViewModel.listPieColors.observe(viewLifecycleOwner, Observer {
            pieDataSet = PieDataSet(eventsViewModel.actionList.value, "MyPie")
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

        eventsViewModel.actionList.observe(viewLifecycleOwner, Observer {
            pieDataSet = PieDataSet(it, "MyPie")
            pieDataSet.sliceSpace = 2f
            pieDataSet.setDrawValues(false)
            pieDataSet.colors = eventsViewModel.listPieColors.value
            pieDate = PieData(pieDataSet)
            pieChart.data = pieDate
            pieChart.notifyDataSetChanged()
            pieChart.invalidate()
        })
    }
}