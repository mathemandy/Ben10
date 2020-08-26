package ng.mathemandy.venten.presentation.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_filter.view.*
import ng.mathemandy.venten.R
import ng.mathemandy.venten.data.model.Filter
import java.util.*

class FilterAdapter(private val interaction: Interaction? = null) :
    ListAdapter<Filter, FilterAdapter.FilterViewHolder>(FilterDC()) {


    private var layoutId: Int = R.layout.item_filter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FilterViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(layoutId, parent, false), interaction
    )

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) =
        holder.bind(getItem(position))

    fun swapData(data: List<Filter>, layoutId: Int?) {
        if (layoutId != null) {
            this.layoutId = layoutId
        }
        submitList(data.toMutableList())
    }

    inner class FilterViewHolder(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val cardColor = MutableList(10) {
            when (it) {
                0, 5-> Color.parseColor("#2E2E2E")
                1 , 6-> Color.parseColor("#0C5E35")
                2 ,7-> Color.parseColor("#7A0C4C")
                3, 8 -> Color.parseColor("#00326F")
                4, 9 -> Color.parseColor("#7A4F01")
                else -> Color.parseColor("#004883")

            }
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            if (adapterPosition == RecyclerView.NO_POSITION) return

            val clicked = getItem(adapterPosition)

            interaction?.itemClicked(clicked)
        }

        fun bind(item: Filter) = with(itemView) {
            val rnd = Random()
            val currentColor: Int = cardColor[rnd.nextInt(10)]

            this.card.setBackgroundColor(currentColor)

            this.gender.text = if (item.gender.isEmpty()) "N/A" else item.gender
            this.country.text = item.countries.toString().replace("[", "")
                .replace("]", "")
            this.year.text = "${item.start_year} - ${item.end_year}"
            this.colors.text = item.colors.toString().replace("[", "")
                .replace("]", "").trim()
        }
    }

    interface Interaction {
        fun itemClicked(item: Filter)
    }

    private class FilterDC : DiffUtil.ItemCallback<Filter>() {

        override fun areContentsTheSame(oldItem: Filter, newItem: Filter): Boolean =
            oldItem == newItem

        override fun areItemsTheSame(oldItem: Filter, newItem: Filter): Boolean =
            oldItem.id == newItem.id

    }
}