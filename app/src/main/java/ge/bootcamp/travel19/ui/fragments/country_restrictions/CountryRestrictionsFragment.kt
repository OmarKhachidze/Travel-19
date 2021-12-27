package ge.bootcamp.travel19.ui.fragments.country_restrictions

import androidx.navigation.fragment.navArgs
import ge.bootcamp.travel19.databinding.FragmentCountryRestrictionsBinding
import ge.bootcamp.travel19.ui.fragments.BaseFragment


class CountryRestrictionsFragment :
    BaseFragment<FragmentCountryRestrictionsBinding>(FragmentCountryRestrictionsBinding::inflate) {
    private val countryRestrictionArgs: CountryRestrictionsFragmentArgs by navArgs()

    override fun start() {
        countryRestrictionArgs.selectedCountry?.let {
            binding.awd.text = it.toString()
        }
    }


}