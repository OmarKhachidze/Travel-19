package ge.bootcamp.travel19.ui.fragments.search_country.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.CountriesItemBinding
import ge.bootcamp.travel19.extensions.setNetworkImage
import ge.bootcamp.travel19.model.countries.Countries

typealias OnClickCountyItem = (country: Countries) -> Unit

class CountriesAdapter :
    ListAdapter<Countries, CountriesAdapter.CountriesViewHolder>(CountriesComparator()) {

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

        fun bind(country: Countries) {
            binding.tvCountryName.text = country.name
            binding.tvCountryCode.text =
                country.alpha2Code.plus(binding.tvCountryCode.context.getString(R.string.dash))
            binding.ivCountry.setNetworkImage(country.flags?.png)

            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            countryItemOnClick?.invoke(getItem(adapterPosition))
        }
    }


    class CountriesComparator : DiffUtil.ItemCallback<Countries>() {
        override fun areItemsTheSame(oldItem: Countries, newItem: Countries): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Countries, newItem: Countries): Boolean {
            return oldItem.nativeName == newItem.nativeName && oldItem.name == oldItem.name
        }
    }


}