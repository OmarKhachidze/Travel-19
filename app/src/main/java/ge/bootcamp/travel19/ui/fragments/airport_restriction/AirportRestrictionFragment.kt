package ge.bootcamp.travel19.ui.fragments.airport_restriction

import android.util.Log
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import ge.bootcamp.travel19.databinding.FragmentAirportRestrictionBinding
import ge.bootcamp.travel19.extensions.setUp
import ge.bootcamp.travel19.domain.model.airports.GeneralRestrictions
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.domain.states.Resource
import kotlinx.coroutines.flow.collect

class AirportRestrictionFragment :
    BaseFragment<FragmentAirportRestrictionBinding>(FragmentAirportRestrictionBinding::inflate) {

    private val airportsViewModel: AirportRestrictionsViewModel by activityViewModels()

    // private val restrictByAirportsAdapter: RestrictByAirportsAdapter = RestrictByAirportsAdapter()
    private val args: AirportRestrictionFragmentArgs by navArgs()
    private val adapterData: MutableList<GeneralRestrictions> = mutableListOf()

    override fun start() {
        // val (location, destination, nationality, vaccine) = args.airport!!
//        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
//            args.airport?.let {
//                fetchRestrictionsByAirport(it.location, it.destination, it.vaccine, it.nationality)
//            }
//        }

    }

    private fun renderInfoOnUi(bool: Boolean) {
        with(binding) {
            pcrRequiredForResidentsChip.setUp(bool)
            pcrRequiredForNoneResidentsChip.setUp(bool)
            businessVisitChip.setUp(bool)
            covidPassportRequiredChip.setUp(bool)
        }
    }

//    private suspend fun checkToken() {
//        return airportsViewModel.checkTokenInDataStore(stringPreferencesKey("userToken"))
//    }

//    private fun initRecycler() {
//        binding.recycler.apply {
//            Log.d("state", "initRecycler")
//            adapter = restrictByAirportsAdapter
//            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        }
//    }

    private suspend fun fetchRestrictionsByAirport(
        loc: String,
        dest: String,
        nationality: String,
        vaccine: String
    ) {
//        lifecycleScope.launchWhenStarted {
//            airportsViewModel.airportRestrictions(loc, dest, nationality, vaccine)
//                .collect { state ->
//                    when (state) {
//                        is Resource.Success -> {
//                            Log.d("data", state.data!!.toString())
//                            val entry1 = state.data.restricions[dest]
//
//                            with(entry1!!.generalRestrictions) {
//                                this!!.allowsBusinessVisit
//
//                                with(binding) {
//                                    pcrRequiredForResidentsChip.setUp(pcrRequiredForResidents!!)
//                                    pcrRequiredForNoneResidentsChip.setUp(
//                                        pcrRequiredForNoneResidents!!
//                                    )
//                                    businessVisitChip.setUp(allowsBusinessVisit!!)
//                                    covidPassportRequiredChip.setUp(covidPassportRequired!!)
//                                    tvGeneralRestriction.text = generalInformation
//                                }
//
//                            }
//
////                            with(entry1.restrictionsByVaccination) {
////                                binding.tvDose.text = this?.dozesRequired.toString()
////                                binding.tvDays.text =
////                                    this?.minDaysAfterVaccination.toString().plus(" - ")
////                                        .plus(this?.maxDaysAfterVaccination.toString())
////                                binding.allowedWithVaccine.setUp(this?.isAllowed!!)
////                                binding.allowedWithVaccine.text = if (this?.isAllowed!!) {
////                                    "Allowed"
////                                } else {
////                                    "Not Allowed"
////                                }
////                                args.airport?.let {
////                                    it.apply {
////                                        binding.titleVaccination.text = vaccine
////                                    }
////                                }
////                            }
////                        restrictByAirportsAdapter.setData(adapterData)
////                        initRecycler()
//                            Log.d("dataRest", entry1.toString())
//                        }
//                        is Resource.Error -> {
//                            Log.d("state", "Error")
//                            Log.d("state", state.message.toString())
//                            //                       state.message?.let { onError(it) }
//                        }
//                        is Resource.Loading -> {
//                            Log.d("state", "Loading")
////                        handleUiVisibility(true)
//                        }
//                    }
//                }
//        }
    }
}