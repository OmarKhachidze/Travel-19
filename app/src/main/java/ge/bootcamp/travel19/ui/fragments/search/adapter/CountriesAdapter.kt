package ge.bootcamp.travel19.ui.fragments.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ge.bootcamp.travel19.databinding.CountriesItemBinding
import ge.bootcamp.travel19.extensions.setNetworkImage
import ge.bootcamp.travel19.model.countries.Countries

class CountriesAdapter :
    ListAdapter<Countries, CountriesAdapter.CountriesViewHolder>(CountriesComparator()) {


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

    class CountriesViewHolder(private val binding: CountriesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(country: Countries) {
            binding.tvCountryName.text = country.name
            binding.ivCountry.setNetworkImage(country.flags?.png)
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