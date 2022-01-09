package ge.bootcamp.travel19.ui.fragments.airport_restriction

import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentAirportRestrictionBinding
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.collect

class AirportRestrictionFragment : BaseFragment<FragmentAirportRestrictionBinding>(FragmentAirportRestrictionBinding::inflate) {

    private val airportRestrictionViewModel: AirportRestrictionViewModel by activityViewModels()
    private val airports: MutableList<String> = mutableListOf()

    override fun start() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            fetchAirports()
        }
        val airportsAdapter = ArrayAdapter(requireContext(), R.layout.list_item, airports)
        (binding.tiDestinationAirport.editText as? AutoCompleteTextView)?.setAdapter(airportsAdapter)
        (binding.tiLocationAirport.editText as? AutoCompleteTextView)?.setAdapter(airportsAdapter)

        binding.next.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                fetchRestrictionsByAirport(binding.eLocationAirport.text.toString(), binding.eDestinationAirport.text.toString())
            }

        }
    }

    private suspend fun fetchAirports() {
        Log.i("fetch", "airports")
        lifecycleScope.launchWhenStarted {
            airportRestrictionViewModel.getAirports().collect { state ->
                when (state) {
                    is Resource.Success -> {
                        Log.d("state", "Success")
                        Log.d("data", state.data!!.toString())
                        state.data.airports!!.forEach {
                            it.code?.let { it1 -> airports.add(it1) }
                        }
                    }
                    is Resource.Error -> {
                        Log.d("state", "Error")
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

    private suspend fun fetchRestrictionsByAirport(loc:String, dest: String) {
        lifecycleScope.launchWhenStarted {
            airportRestrictionViewModel.fetchRestrictionsByAirport(loc, dest).collect { state ->
                when (state) {
                    is Resource.Success -> {
                        Log.d("state", "Success")
                        Log.d("data", state.data!!.toString())
                        binding.tIsVaccinated.text = state.data.toString()
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