package ge.bootcamp.travel19.ui.fragments.country_restrictions

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentCountryRestrictionsBinding
import ge.bootcamp.travel19.extensions.gone
import ge.bootcamp.travel19.extensions.invisible
import ge.bootcamp.travel19.extensions.showSnack
import ge.bootcamp.travel19.extensions.visible
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.collect


class CountryRestrictionsFragment :
    BaseFragment<FragmentCountryRestrictionsBinding>(FragmentCountryRestrictionsBinding::inflate) {
    private val countryRestrictionArgs: CountryRestrictionsFragmentArgs by navArgs()

    private val restrictionsViewModel: CountryRestrictionsViewModel by activityViewModels()

    override fun start() {
        countryRestrictionArgs.selectedCountry?.let {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                restrictionsViewModel.data(it.alpha2Code!!).collect { state ->
                    when (state) {
                        is Resource.Loading -> {
                            binding.prCircular.visible()
                        }
                        is Resource.Success -> {
                            binding.prCircular.gone()
                            binding.root.showSnack(
                                "${state.data?.data?.area?.name}",
                                R.color.success_green
                            )
                        }
                        is Resource.Error -> {
                            binding.root.showSnack("Error", R.color.error_red)
                            binding.prCircular.gone()
                        }
                    }
                }
            }
        }
    }


}