package com.example.timemanager.ui.details

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.shared.data.entity.event.EventInfoUi
import com.example.shared.utils.parseTimeFromMinutes
import com.example.timemanager.R
import kotlinx.android.synthetic.main.item_event_info.view.*

class RecyclerEventInfoAdapter(fragment: DetailsFragment) :
    RecyclerView.Adapter<RecyclerEventInfoAdapter.EventViewHolder>() {

    private var events = emptyList<EventInfoUi>()
    private val viewModel: DetailsViewModel =
        ViewModelProvider(fragment).get(DetailsViewModel::class.java)

    fun setEvents(items: List<EventInfoUi>) {
        this.events = items
        notifyDataSetChanged()
    }

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_event_info, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.itemView.time_spent_event_info.text =
            events[position].spentTime.parseTimeFromMinutes()
        holder.itemView.icon_event_info.background.setTint(Color.parseColor(events[position].color))
        holder.itemView.name_event_info.text = events[position].name
        holder.itemView.description_event_info.text = events[position].description
        holder.itemView.time_event_info.text = events[position].date.substringAfter(" ")
        holder.itemView.cardView.setOnClickListener {
            viewModel.openBottomSheet(position)
        }
    }

    override fun getItemCount() = events.size
}