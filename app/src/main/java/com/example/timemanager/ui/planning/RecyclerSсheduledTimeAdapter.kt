package com.example.timemanager.ui.planning

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
import kotlinx.android.synthetic.main.item_scheduled_time.view.*

class RecyclerScheduledTimeAdapter(fragment: PlanningFragment) :
    RecyclerView.Adapter<RecyclerScheduledTimeAdapter.ScheduledTimeViewHolder>() {

    private var events = emptyList<EventScheduledInfoUi>()
    private val viewModel: PlanningViewModel =
        ViewModelProvider(fragment).get(PlanningViewModel::class.java)

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

    override fun onBindViewHolder(holder: ScheduledTimeViewHolder, p: Int) {
        val difTime = events[p].scheduledTime - events[p].spentTime
        if(events[p].scheduledTime == 0){
            holder.itemView.time_difference_time_item.visibility = View.GONE
        }else if (difTime > 0){
            holder.itemView.time_difference_time_item.visibility = View.VISIBLE
            holder.itemView.time_difference_time_item.text = difTime.parseTimeFromMinutes()
        }else {
            holder.itemView.time_difference_time_item.visibility = View.VISIBLE
            holder.itemView.time_difference_time_item.setBackgroundColor(Color.parseColor("#FF3E3E"))
            holder.itemView.time_difference_time_item.text = difTime.times(-1).parseTimeFromMinutes()
        }
        holder.itemView.icon_scheduled_time.background.setTint(Color.parseColor(events[p].color))
        holder.itemView.name_scheduled_time.text = events[p].name
        holder.itemView.time_spent_scheduled_time_item.setTextColor(Color.parseColor(events[p].color))
        holder.itemView.scheduled_time_item.text =events[p].scheduledTime.parseTimeFromMinutes()
        holder.itemView.time_spent_scheduled_time_item.text = events[p].spentTime.parseTimeFromMinutes()
        holder.itemView.negative_progress_scheduled_time.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            events[p].spentTime.toFloat()
        )
        holder.itemView.positive_progress_scheduled_time.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            events[p].scheduledTime.toFloat().plus(0.001f).minus(events[p].spentTime.toFloat())

        )
        holder.itemView.progress_scheduled_time_back.background.setTint(Color.parseColor(events[p].color))

        holder.itemView.cardViewScheduledTime.setOnClickListener {
            viewModel.openBottomSheet(p)
        }
    }

    override fun getItemCount() = events.size
}