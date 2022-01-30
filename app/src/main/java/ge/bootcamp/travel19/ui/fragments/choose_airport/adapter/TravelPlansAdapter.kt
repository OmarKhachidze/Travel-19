package ge.bootcamp.travel19.ui.fragments.choose_airport.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ge.bootcamp.travel19.databinding.AirportPlanLayoutBinding
import ge.bootcamp.travel19.model.airports.plans.PostTravelPlan
import ge.bootcamp.travel19.model.airports.plans.travlePlans.TravelPlan

typealias OnClickPlanItem = (plan: TravelPlan) -> Unit
typealias OnClickDeleteItem = (id: String, position: Int) -> Unit

class TravelPlansAdapter : ListAdapter<TravelPlan, TravelPlansAdapter.PlansViewHolder>(
    TravelPlansAdapter.PlansComparator()
) {

    var planItemOnClick: OnClickPlanItem? = null
    var deleteItemOnClick: OnClickDeleteItem? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlansViewHolder(
            AirportPlanLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TravelPlansAdapter.PlansViewHolder, position: Int) {
        val travelPlan = getItem(position)
        holder.bind(travelPlan)
    }

    inner class PlansViewHolder(private val binding: AirportPlanLayoutBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(plan: TravelPlan) {
            binding.apply {
                tvFromDestination.text = plan.source
                tvToDestination.text = plan.destination
                vaccineChip.text = plan.vaccine
                nationalityChip.text = plan.nationality
            }
//            binding.ivCountry.apply {
//                setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.ic_georgia_flag))
//            }
            binding.ibDelete.setOnClickListener {
                getItem(adapterPosition).id?.let {
                    deleteItemOnClick?.invoke(it, adapterPosition)
                }
            }
            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            planItemOnClick?.invoke(getItem(adapterPosition))
        }
    }


    class PlansComparator : DiffUtil.ItemCallback<TravelPlan>() {
        override fun areItemsTheSame(oldItem: TravelPlan, newItem: TravelPlan): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: TravelPlan, newItem: TravelPlan): Boolean {
            return oldItem.source == newItem.source && oldItem.destination == oldItem.destination
        }

    }

}