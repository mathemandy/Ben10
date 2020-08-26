package ng.mathemandy.venten.presentation.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devs.readmoreoption.ReadMoreOption
import kotlinx.android.synthetic.main.item_car.view.*
import kotlinx.android.synthetic.main.item_filter.view.image_card
import ng.mathemandy.venten.R
import ng.mathemandy.venten.data.model.Car
import java.util.*


class CarsAdapter(private val interaction: Interaction? = null) :
    ListAdapter<Car, CarsAdapter.CarsViewHolder>(CarsDC()) {

    private var layoutId: Int = R.layout.item_car

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CarsViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(layoutId, parent, false), interaction
    )

    override fun onBindViewHolder(holder: CarsViewHolder, position: Int) =
        holder.bind(getItem(position))

    fun swapData(data: List<Car>, layoutId: Int?) {
        if (layoutId != null) {
            this.layoutId = layoutId
        }
        submitList(data.toMutableList())
    }

    inner class CarsViewHolder(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            if (adapterPosition == RecyclerView.NO_POSITION) return

            val clicked = getItem(adapterPosition)

            interaction?.itemClicked(clicked)
        }

        fun bind(item: Car) = with(itemView) {


            this.fullName.text = "${item.first_name} ${item.last_name}"
            this.email.text = item.email
            this.carCountry.text = item.country
            this.car_model.text  = item.car_model
            this.car_model_year.text =item.car_model_year
            this.car_color.text  =   item.car_color
            this.carGender.text   = item.gender
            this.jobTitle.text  =  item.job_title


            // OR using options to customize


            // OR using options to customize
            val readMoreOption: ReadMoreOption = ReadMoreOption.Builder(this.context)
                .textLength(50)
                .moreLabel("more")
                .lessLabel("less")
                .moreLabelColor(Color.RED)
                .lessLabelColor(Color.BLUE)
                .labelUnderLine(true)
                .build()

            readMoreOption.addReadMoreTo(this.bio,  item.bio)



        }
    }

    interface Interaction {
        fun itemClicked(item: Car)
    }

    private class CarsDC : DiffUtil.ItemCallback<Car>() {

        override fun areContentsTheSame(oldItem: Car, newItem: Car): Boolean = oldItem == newItem

        override fun areItemsTheSame(oldItem: Car, newItem: Car): Boolean = oldItem.id == newItem.id
    }
}