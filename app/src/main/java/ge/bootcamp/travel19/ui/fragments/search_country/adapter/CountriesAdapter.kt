package ge.bootcamp.travel19.ui.fragments.search_country.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.CountriesItemBinding
import ge.bootcamp.travel19.extensions.setNetworkImage
import ge.bootcamp.travel19.model.countriesv3.V3CountriesItem

typealias OnClickCountyItem = (country: V3CountriesItem) -> Unit

class CountriesAdapter :
    ListAdapter<V3CountriesItem, CountriesAdapter.CountriesViewHolder>(CountriesComparator()) {

    var countryItemOnClick: OnClickCountyItem? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CountriesViewHolder(
            CountriesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        val currentCountries = getItem(position)
        holder.bind(currentCountries)
    }

    inner class CountriesViewHolder(private val binding: CountriesItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(country: V3CountriesItem) {
            binding.apply {
                tvCountryName.text = country.name?.common
                tvCountryCode.text =
                    country.cca2.plus(binding.tvCountryCode.context.getString(R.string.dash))
                ivCountry.setNetworkImage(country.flags?.png)
            }
            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            countryItemOnClick?.invoke(getItem(adapterPosition))
        }
    }


    class CountriesComparator : DiffUtil.ItemCallback<V3CountriesItem>() {
        override fun areItemsTheSame(oldItem: V3CountriesItem, newItem: V3CountriesItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: V3CountriesItem,
            newItem: V3CountriesItem
        ): Boolean {
            return oldItem.name?.common == newItem.name?.common && oldItem.name?.official == oldItem.name?.official
        }
    }


}