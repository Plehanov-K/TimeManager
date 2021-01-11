package com.example.timemanager.ui.notifications

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shared.utils.parseTimeFromMinutes
import com.example.timemanager.R
import com.example.timemanager.data.MDate
import com.example.timemanager.ui.dashboard.RecyclerEventInfoAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.alert_view_scheduled_time.*
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.recycler_view_event
import kotlinx.android.synthetic.main.fragment_notifications.*
import java.util.*

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val totalTime = view.findViewById<TextView>(R.id.text_total_time_scheduled)
        val scheduledAdapter = RecyclerScheduledTimeAdapter(this)
        recycler_scheduled_time.adapter = scheduledAdapter
        recycler_scheduled_time.layoutManager = LinearLayoutManager(context)
        notificationsViewModel.listScheduledInfo.observe(viewLifecycleOwner, Observer {
            scheduledAdapter.setEvents(it)
            totalTime.text = getString(R.string.total_time)+" " + MDate.dateM + " " +
                    notificationsViewModel.totalTimeMonth.parseTimeFromMinutes()
        })

        MDate.date.observe(viewLifecycleOwner, Observer {
            notificationsViewModel.loadScheduledInfoList()
            totalTime.text = getString(R.string.total_time) + MDate.dateM + " : " +
                    notificationsViewModel.totalTimeMonth.parseTimeFromMinutes()
        })

        val inflater = LayoutInflater.from(activity)
        notificationsViewModel.tempEvent.observe(viewLifecycleOwner, Observer { tempEvent ->
            val alertView: View = inflater.inflate(R.layout.alert_view_scheduled_time, null)
            val timePicker =
                alertView.findViewById<TimePicker>(R.id.time_picker_alert_view_scheduled)
            timePicker.setIs24HourView(true)
            timePicker.currentHour = 0
            timePicker.currentMinute = 0
            val title = alertView.findViewById<TextView>(R.id.title_alert_view_scheduled)
            title.text = tempEvent.name
            title.setBackgroundColor(tempEvent.color)
            val radioBtnDay = alertView.findViewById<RadioButton>(R.id.radio_btn_day)
            val radioBtnWeek = alertView.findViewById<RadioButton>(R.id.radio_btn_week)
            val radioBtnMonth = alertView.findViewById<RadioButton>(R.id.radio_btn_month)
            val btnAdd = alertView.findViewById<Button>(R.id.button_add_alert_view_scheduled)
            val btnCancel = alertView.findViewById<Button>(R.id.button_cancel_alert_view_scheduled)

            val dialog =
                MaterialAlertDialogBuilder(requireContext(), R.style.ColorPickerDialogTheme)
                    .setView(alertView)
                    .show()
            btnAdd.setOnClickListener {
                val timeInMinutes = timePicker.currentHour.times(60).plus(timePicker.currentMinute)
                var temp: Int? = null

                if (timeInMinutes == 0) {
                    notificationsViewModel.addTimeScheduledInDb(tempEvent.idDb, 0)
                    dialog.dismiss()
                } else {
                    when {
                        radioBtnDay.isChecked -> temp = timeInMinutes.times(MDate.maxDayInMonth)
                        radioBtnWeek.isChecked -> temp =
                            timeInMinutes.div(7.0f).times(MDate.maxDayInMonth).toInt()
                        radioBtnMonth.isChecked -> temp = timeInMinutes
                        else -> Toast.makeText(context, getString(R.string.alert_ranget_not_select), Toast.LENGTH_SHORT)
                            .show()
                    }
                    temp?.let {
                        if (it > notificationsViewModel.totalTimeMonth) {
                            Toast.makeText(
                                context,
                                getString(R.string.alert_exceeding_time) +
                                        notificationsViewModel.totalTimeMonth.parseTimeFromMinutes(),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            notificationsViewModel.addTimeScheduledInDb(tempEvent.idDb, it)
                            dialog.dismiss()
                        }
                    }
                }
            }
            btnCancel.setOnClickListener { dialog.dismiss() }
        })
    }
}