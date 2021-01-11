package com.example.timemanager.ui.notifications

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.shared.data.entity.event.EventScheduledInfoUi
import com.example.shared.utils.parseTimeFromMinutes
import com.example.timemanager.R
import kotlinx.android.synthetic.main.alert_view_scheduled_time.view.*
import kotlinx.android.synthetic.main.item_event_info.view.*
import kotlinx.android.synthetic.main.item_scheduled_time.view.*

class RecyclerScheduledTimeAdapter(fragment: NotificationsFragment) :
    RecyclerView.Adapter<RecyclerScheduledTimeAdapter.ScheduledTimeViewHolder>() {

    private var events = emptyList<EventScheduledInfoUi>()
    private val viewModel: NotificationsViewModel =
        ViewModelProvider(fragment).get(NotificationsViewModel::class.java)

    fun setEvents(items: List<EventScheduledInfoUi>) {
        this.events = items
        notifyDataSetChanged()
    }

    class ScheduledTimeViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduledTimeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_scheduled_time, parent, false)
        return ScheduledTimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduledTimeViewHolder, position: Int) {
        holder.itemView.icon_scheduled_time.background.setTint(Color.parseColor(events[position].color))
        holder.itemView.name_scheduled_time.text = events[position].name
        holder.itemView.time_spent_scheduled_time_item.setTextColor(Color.parseColor(events[position].color))
        holder.itemView.scheduled_time_item.text =events[position].scheduledTime.parseTimeFromMinutes()
        holder.itemView.time_spent_scheduled_time_item.text = events[position].spentTime.parseTimeFromMinutes()
        holder.itemView.negative_progress_scheduled_time.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            events[position].spentTime.toFloat()
        )
        holder.itemView.positive_progress_scheduled_time.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            events[position].scheduledTime.toFloat().plus(0.001f).minus(events[position].spentTime.toFloat())

        )
        holder.itemView.progress_scheduled_time_back.background.setTint(Color.parseColor(events[position].color))

        holder.itemView.cardViewScheduledTime.setOnClickListener {
            viewModel.openBottomSheet(position)
        }
    }

    override fun getItemCount() = events.size
}
