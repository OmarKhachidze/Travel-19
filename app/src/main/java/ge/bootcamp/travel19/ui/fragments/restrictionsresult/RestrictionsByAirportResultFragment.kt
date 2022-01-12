package ge.bootcamp.travel19.ui.fragments.restrictionsresult

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import ge.bootcamp.travel19.databinding.FragmentRestrictionsByAirpotResultBinding
import ge.bootcamp.travel19.model.generalRestrictions.GeneralRestrictions
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.ui.fragments.restrictionsresult.adapters.RestrictByAirportsAdapter
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.collect

class RestrictionsByAirportResultFragment : BaseFragment<FragmentRestrictionsByAirpotResultBinding>(FragmentRestrictionsByAirpotResultBinding::inflate) {
    private val airportsViewModel: AirportRestrictionsViewModel by activityViewModels()
    private val restrictByAirportsAdapter: RestrictByAirportsAdapter = RestrictByAirportsAdapter()
    private val args: RestrictionsByAirportResultFragmentArgs by navArgs()
    private val adapterData: MutableList<GeneralRestrictions> = mutableListOf()

    override fun start() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            args.airport?.let {
                it.apply {
                    fetchRestrictionsByAirport(location, destination, nationality, vaccine)
                }
            }
        }

    }

    private fun initRecycler() {
        binding.recycler.apply {
            Log.d("state", "initRecycler")
            adapter = restrictByAirportsAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private suspend fun fetchRestrictionsByAirport(loc:String, dest: String, nationality:String, vaccine:String) {
        lifecycleScope.launchWhenStarted {
            airportsViewModel.airportRestrictions(loc, dest, nationality, vaccine).collect { state ->
                when (state) {
                    is Resource.Success -> {
                        Log.d("data", state.data!!.toString())
                        val entry1 = state.data.restricions[dest]

                        with(entry1!!.generalRestrictions) {
                            this!!.allowsBusinessVisit
                            adapterData.add(GeneralRestrictions(name = "allowsTourists", this.allowsTourists, null))
                            adapterData.add(GeneralRestrictions(name = "allowsBusinessVisit", this.allowsBusinessVisit, null))
                            adapterData.add(GeneralRestrictions(name = "covidPassportRequired", this.covidPassportRequired, null))
                            adapterData.add(GeneralRestrictions(name = "pcrRequiredForNoneResidents", this.pcrRequiredForNoneResidents, null))
                            adapterData.add(GeneralRestrictions(name = "pcrRequiredForResidents", this.pcrRequiredForResidents, null))
                            adapterData.add(GeneralRestrictions(name = "generalInformation", null, generalInformation))
                            adapterData.add(GeneralRestrictions(name = "moreInfoUrl", null, moreInfoUrl))
                        }
                        restrictByAirportsAdapter.setData(adapterData)
                        initRecycler()

                        Log.d("dataRest", entry1.toString())


                    }
                    is Resource.Error -> {
                        Log.d("state", "Error")
                        Log.d("state", state.message.toString())
                        //                       state.message?.let { onError(it) }
                    }
                    is Resource.Loading -> {
                        Log.d("state", "Loading")
//                        handleUiVisibility(true)
                    }
                }
            }
        }
    }


}