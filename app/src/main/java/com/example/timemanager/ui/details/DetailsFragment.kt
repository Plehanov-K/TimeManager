package com.example.timemanager.ui.details

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shared.utils.parseTimeFromMinutes
import com.example.timemanager.R
import com.example.timemanager.data.CAL_DAY
import com.example.timemanager.data.CAL_MONTH
import com.example.timemanager.data.MDate
import com.example.timemanager.utils.showAlert
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {
    private lateinit var detailsViewModel: DetailsViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailsViewModel =
            ViewModelProvider(this).get(DetailsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MDate.tempTime = CAL_DAY
        val eventAdapter = RecyclerEventInfoAdapter(this)
        recycler_view_event.adapter = eventAdapter
        recycler_view_event.layoutManager = LinearLayoutManager(context)
        MDate.date.observe(viewLifecycleOwner, Observer {
            detailsViewModel.loadEventTodayFromDb()
        })
        detailsViewModel.listEventInfo.observe(viewLifecycleOwner, Observer {
            eventAdapter.setEvents(it)
        })


        detailsViewModel.tempEvent.observe(viewLifecycleOwner, Observer {

            val inflater: LayoutInflater = LayoutInflater.from(activity)
            val customView: View = inflater.inflate(R.layout.alert_view_edit_time, null)
            val editDescription =
                customView.findViewById<EditText>(R.id.edit_text_alert_view_delete)
            val timePicker: TimePicker = customView.findViewById(R.id.time_picker_alert_view_delete)
            timePicker.setIs24HourView(true)
            val titleAlert = customView.findViewById<TextView>(R.id.title_alert_view_delete)
            val freeTimeTextView = customView.findViewById<TextView>(R.id.time_alert_view_delete)
            val freeTime = detailsViewModel.dayTimeInMinutes

            titleAlert.text = it.name
            titleAlert.setBackgroundColor(it.color)
            timePicker.currentHour = it.timeSpent / 60
            timePicker.currentMinute = it.timeSpent % 60
            editDescription.setText(it.description)
            freeTimeTextView.text = getString(R.string.free_time) + freeTime.parseTimeFromMinutes()

            showAlert(
                positiveButtonResId = (R.string.edit),
                neutralButtonResId = (R.string.delete),
                view = customView,
                theme = R.style.ColorPickerDialogTheme,
                neutralButtonFun = {
                    showAlert(
                        title = getString(R.string.delete_event),
                        theme = R.style.ColorPickerDialogTheme,
                        positiveButtonFun = {
                            detailsViewModel.deleteEventFromDb(id = it.idDb)
                        }
                    )
                },
                positiveButtonFun = {
                    if (timePicker.currentHour.times(60).plus(timePicker.currentMinute) <= freeTime+it.timeSpent) {
                        showAlert(
                            title = getString(R.string.change_event),
                            theme = R.style.ColorPickerDialogTheme,
                            positiveButtonFun = {
                                detailsViewModel.updateEventFromDb(
                                    id = it.idDb,
                                    time = timePicker.currentHour.times(60)
                                        .plus(timePicker.currentMinute),
                                    description = editDescription.text.toString().trim()
                                )
                            })
                    } else {
                        showAlert(
                            title = getString(R.string.alert_exceeding_time)+freeTime.plus(it.timeSpent).parseTimeFromMinutes(),
                            theme = R.style.ColorPickerDialogTheme,
                        )
                    }
                }
            )
        })
    }
}