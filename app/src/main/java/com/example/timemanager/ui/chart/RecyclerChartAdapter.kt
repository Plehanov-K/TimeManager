package com.example.timemanager.ui.chart

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.shared.data.entity.event.EventChartInfoUi
import com.example.shared.data.entity.event.EventScheduledInfoUi
import com.example.shared.utils.parseTimeFromMinutes
import com.example.timemanager.R
import kotlinx.android.synthetic.main.alert_view_scheduled_time.view.*
import kotlinx.android.synthetic.main.item_chart.view.*
import kotlinx.android.synthetic.main.item_event_info.view.*
import kotlinx.android.synthetic.main.item_scheduled_time.view.*

class RecyclerChartAdapter(fragment: ChartFragment) :
    RecyclerView.Adapter<RecyclerChartAdapter.ChartViewHolder>() {

    private var events = emptyList<EventChartInfoUi>()
    private val viewModel: ChartViewModel =
        ViewModelProvider(fragment).get(ChartViewModel::class.java)

    fun setEvents(items: List<EventChartInfoUi>) {
        this.events = items
        notifyDataSetChanged()
    }

    class ChartViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_chart, parent, false)
        return ChartViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChartViewHolder, position: Int) {

        val timeSpentPercent =
            events[position].spentTime.div(viewModel.totalTime.div(100.0)).toInt()

        holder.itemView.name_chart_item.text = events[position].name
        holder.itemView.time_chart_item.text = events[position].spentTime.parseTimeFromMinutes()
        holder.itemView.line_positive_progress.background.setTint(Color.parseColor(events[position].color))
        holder.itemView.text_positive_progress.setTextColor(Color.parseColor(events[position].color))
        holder.itemView.text_positive_progress.text = timeSpentPercent.toString().plus("%")
        holder.itemView.positive_progress_chart_item.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            100.minus(timeSpentPercent).toFloat()
        )
        holder.itemView.negative_progrss_chart_item.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            timeSpentPercent.toFloat().plus(15)
        )
    }

    override fun getItemCount() = events.size
}
