package ge.bootcamp.travel19.ui.fragments.country_restrictions

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentCountryRestrictionsBinding
import ge.bootcamp.travel19.extensions.parseHtml
import ge.bootcamp.travel19.extensions.showSnack
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.ui.fragments.country_restrictions.adapter.AreaRestrictionAdapter
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CountryRestrictionsFragment :
    BaseFragment<FragmentCountryRestrictionsBinding>(FragmentCountryRestrictionsBinding::inflate) {
    private val countryRestrictionArgs: CountryRestrictionsFragmentArgs by navArgs()
    private val restrictionsViewModel: CountryRestrictionsViewModel by viewModels()
    private val restrictionAdapter: AreaRestrictionAdapter = AreaRestrictionAdapter()

    override fun start() {
        setUpRecycler()
//        countryRestrictionArgs.selectedCountry?.let {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            restrictionsViewModel.testData().collect { state ->
                when (state) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        state.data?.data?.areaVaccinated?.let { vaccinated ->
                            binding.apply {
                                tvOneDosePercent.text = vaccinated[0].percentage?.toInt().toString()
                                prOneDose.progress = vaccinated[0].percentage?.toInt()!!
                                tvFullDosePercent.text =
                                    vaccinated[1].percentage?.toInt().toString()
                                prFullDose.progress = vaccinated[1].percentage?.toInt()!!
                            }
                        }
                        state.data?.data?.let { restrictions ->
                            restrictionAdapter.submitList(restrictions.areaRestrictions)
                            binding.apply {
                                tvSummary.text = restrictions.summary?.parseHtml()
                                tvDiseaseTestingText.text = restrictions.areaAccessRestriction?.diseaseTesting?.text?.parseHtml()
                            }

                        }
                    }
                    is Resource.Error -> {
                        binding.root.showSnack(state.message.toString(), R.color.error_red)
                    }
                }
            }
        }
//        }
    }

    private fun setUpRecycler() {
        binding.apply {
            rvAreaRestrictions.adapter = restrictionAdapter
            rvAreaRestrictions.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

}