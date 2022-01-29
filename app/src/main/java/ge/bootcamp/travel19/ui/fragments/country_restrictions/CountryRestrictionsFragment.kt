package ge.bootcamp.travel19.ui.fragments.country_restrictions

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentCountryRestrictionsBinding
import ge.bootcamp.travel19.extensions.*
import ge.bootcamp.travel19.model.country_restrictions.Data
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
        binding.restrictionsToolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        setUpRecycler()
        countryRestrictionArgs.selectedCountry?.let { countryItem ->
            binding.ivCountryFlagImage.setNetworkImage(countryItem.flags?.png)
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                restrictionsViewModel.data(countryItem.cca2.toString()).collect { state ->
                    when (state) {
                        is Resource.Loading -> {
                            setShimmerState(true)
                        }
                        is Resource.Success -> {
                            setShimmerState(false)
                            setSuccessState(state.data?.data)
                        }
                        is Resource.Error -> {
                            binding.root.hideAllView()
                            binding.root.showSnack(state.message.toString(), R.color.error_red)
                        }
                    }
                }
            }
        }
    }

    private fun setSuccessState(restrictionData: Data?) {
        binding.shimmerLayoutRecycler.visibility = View.INVISIBLE
        restrictionData?.areaVaccinated?.let { vaccinated ->
            binding.apply {
                prOneDose.setUpProgress(vaccinated[0].percentage, tvOneDosePercent)
                prFullDose.setUpProgress(vaccinated[1].percentage, tvFullDosePercent)
            }
        }
        restrictionData?.let { restrictions ->
            restrictionAdapter.submitList(restrictions.areaRestrictions)
            binding.apply {
                tvSummary.text = restrictions.summary?.parseHtml()
                tvDiseaseTestingText.text =
                    restrictions.areaAccessRestriction?.diseaseTesting?.text?.parseHtml()
                tvQuarantineModality.text =
                    restrictions.areaAccessRestriction?.quarantineModality?.text?.parseHtml()
                pcrRequiredForNoneResidentsChip.text =
                    restrictions.areaAccessRestriction?.diseaseTesting?.whenX
                businessVisitChip.text =
                    restrictions.areaAccessRestriction?.diseaseTesting?.testType
                covidPassportRequiredChip.text =
                    restrictions.areaAccessRestriction?.diseaseTesting?.requirement
            }

        }
    }

    private fun setShimmerState(shouldLoad: Boolean) {
        binding.apply {
            shimmerLayoutCv.loading(shouldLoad)
            shimmerLayout.loading(shouldLoad)
            shimmerLayoutRecycler.loading(shouldLoad)
            rvAreaRestrictions.isVisible = !shouldLoad
        }
    }

    private fun setUpRecycler() {
        binding.apply {
            rvAreaRestrictions.adapter = restrictionAdapter
            rvAreaRestrictions.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
    }

}