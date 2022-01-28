package ge.bootcamp.travel19.ui.fragments.country_restrictions.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ge.bootcamp.travel19.databinding.AreaRestrictionLayoutBinding
import ge.bootcamp.travel19.extensions.parseHtml
import ge.bootcamp.travel19.model.country_restrictions.AreaRestriction

class AreaRestrictionAdapter :
    ListAdapter<AreaRestriction, AreaRestrictionAdapter.AreaRestrictionViewHolder>(RestrictionComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AreaRestrictionViewHolder(
            AreaRestrictionLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: AreaRestrictionViewHolder, position: Int) {
        val currentRestriction = getItem(position)
        holder.bind(currentRestriction)
    }

    inner class AreaRestrictionViewHolder(private val binding: AreaRestrictionLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(restriction: AreaRestriction) {
            binding.apply {
//                tvAreaRestrictionText12.text = restriction.text?.parseHtml()
                tvAreaRestrictionName.text = restriction.restrictionType
                tvRestrictionDate.text = restriction.date
            }
        }

    }

    class RestrictionComparator : DiffUtil.ItemCallback<AreaRestriction>() {
        override fun areItemsTheSame(oldItem: AreaRestriction, newItem: AreaRestriction): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: AreaRestriction, newItem: AreaRestriction): Boolean {
            return oldItem.restrictionType == newItem.restrictionType
        }
    }


}