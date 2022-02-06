package ge.bootcamp.travel19.ui.fragments.airport_restriction

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentAirportRestrictionBinding
import ge.bootcamp.travel19.domain.model.airports.restrictionsbyairport.AirportRestricion
import ge.bootcamp.travel19.domain.model.airports.restrictionsbyairport.GeneralRestrictions
import ge.bootcamp.travel19.domain.model.airports.restrictionsbyairport.RestrictionsByVaccination
import ge.bootcamp.travel19.domain.states.Resource
import ge.bootcamp.travel19.extensions.*
import ge.bootcamp.travel19.ui.fragments.BaseFragment

@AndroidEntryPoint
class AirportRestrictionFragment :
    BaseFragment<FragmentAirportRestrictionBinding>(FragmentAirportRestrictionBinding::inflate) {

    private val airportsViewModel: AirportRestrictionsViewModel by viewModels()
    private val airportArgs: AirportRestrictionFragmentArgs by navArgs()

    override fun start() {
        airportArgs.airport?.let { args ->
            airportsViewModel.getAirportRestrictions(args)
                .collectWhenStarted(viewLifecycleOwner) { restrictionState ->
                    when (restrictionState) {
                        is Resource.Loading -> {
                            binding.mainConst.hideAllView(true)
                            binding.errorTextAirportRestrictions.gone()
                            binding.prAirportRestrictions.visible()
                        }
                        is Resource.Success -> {
                            binding.apply {
                                mainConst.hideAllView(false)
                                prAirportRestrictions.gone()
                            }
                            restrictionState.data?.restricions?.get(args.destination)?.let {
                                setUpDestinationRestrictions(it)
                            }
                            if (restrictionState.data?.restricions?.get(args.destination)?.restrictionsByVaccination != null) {
                                restrictionState.data.restricions[args.destination]?.restrictionsByVaccination?.let {
                                    setUpVaccinationRestrictions(it)
                                }
                            } else {
                                binding.apply {
                                    llDosesRequired.visibility = View.INVISIBLE
                                    errorVaccine.visibility = View.VISIBLE
                                }
                            }

                            if (restrictionState.data?.restricions?.get(args.transfer) != null) {
                                restrictionState.data.restricions[args.transfer]?.generalRestrictions?.let {
                                    setUpTransferRestrictions(it)
                                }
                            } else {
                                binding.apply {
                                    errorTransfer.visible()
                                    chipSecondAirportGroup.visibility = View.INVISIBLE
                                    titleGeneralRestrictionSecondAirport.visibility = View.INVISIBLE
                                    tvGeneralRestrictionSecondAirport.visibility = View.INVISIBLE
                                }
                            }
                        }
                        is Resource.Error -> {
                            binding.apply {
                                mainConst.hideAllView(true)
                                errorTextAirportRestrictions.visible()
                                errorTextAirportRestrictions.text = restrictionState.message
                            }
                        }
                        else -> {}
                    }
                }
        }
    }

    private fun setUpDestinationRestrictions(airportRestrictions: AirportRestricion) {
        airportRestrictions.generalRestrictions?.apply {
            binding.apply {
                tvGeneralRestriction.text = generalInformation
                pcrRequiredForResidentsChip.setUp(pcrRequiredForResidents!!)
                pcrRequiredForNoneResidentsChip.setUp(pcrRequiredForNoneResidents!!)
                covidPassportRequiredChip.setUp(covidPassportRequired!!)
                isAllowTouristsChip.setUp(allowsTourists!!)
                businessVisitChip.setUp(allowsBusinessVisit!!)
            }
        }
    }

    private fun setUpVaccinationRestrictions(vaccinationRestriction: RestrictionsByVaccination) {
        vaccinationRestriction.apply {
            chooseIfVaccineAllowed(isAllowed!!)
            binding.apply {
                errorVaccine.gone()
                tvVaccineName.text = airportArgs.airport?.vaccine
                tvDoseValue.text = dozesRequired.toString()
                tvDaysValue.text = minDaysAfterVaccination.toString()
                    .plus(" - $maxDaysAfterVaccination")
            }
        }
    }

    private fun chooseIfVaccineAllowed(isAllowed: Boolean) {
        binding.isAllowedVaccineIcon.apply {
            backgroundTintList = if (isAllowed) {
                setDrawable(R.drawable.ic_check)
                ContextCompat.getColorStateList(
                    requireContext(),
                    R.color.success_green
                )
            } else {
                setDrawable(R.drawable.ic_cancel)
                ContextCompat.getColorStateList(
                    requireContext(),
                    R.color.error_red
                )
            }
        }
    }

    private fun setUpTransferRestrictions(transferRestrictions: GeneralRestrictions) {
        transferRestrictions.apply {
            binding.apply {
                errorTransfer.gone()
                tvGeneralRestrictionSecondAirport.text = generalInformation
                pcrRequiredForResidentsSecondAirportChip.setUp(pcrRequiredForResidents!!)
                pcrRequiredForNoneResidentsSecondAirportChip.setUp(pcrRequiredForNoneResidents!!)
                covidPassportRequiredSecondAirportChip.setUp(covidPassportRequired!!)
                isAllowTouristsSecondChip.setUp(allowsTourists!!)
                businessVisitSecondChip.setUp(allowsBusinessVisit!!)
            }
        }
    }

}