package ge.bootcamp.travel19.ui.fragments

import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentAirportsBinding
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.collect

class AirportsFragment : BaseFragment<FragmentAirportsBinding>(FragmentAirportsBinding::inflate) {

    private val airportsViewModel: AirportsViewModel by activityViewModels()
    private val airports: MutableList<String> = mutableListOf()
    private val vaccines: MutableList<String> = mutableListOf()
    private val nationality: MutableList<String> = mutableListOf()

    override fun start() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            fetchAirports()
            fetchNationalities()
            fetchVaccines()
        }
        val airportsAdapter = ArrayAdapter(requireContext(), R.layout.list_item, airports)
        (binding.tiDestinationAirport.editText as? AutoCompleteTextView)?.setAdapter(airportsAdapter)
        (binding.tiLocationAirport.editText as? AutoCompleteTextView)?.setAdapter(airportsAdapter)

        val vaccinesAdapter = ArrayAdapter(requireContext(), R.layout.list_item, vaccines)
        (binding.tiVaccine.editText as? AutoCompleteTextView)?.setAdapter(vaccinesAdapter)

        val nationalitiesAdapter = ArrayAdapter(requireContext(), R.layout.list_item, nationality)
        (binding.tiNationality.editText as? AutoCompleteTextView)?.setAdapter(nationalitiesAdapter)

        binding.next.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                fetchRestrictionsByAirport(binding.eLocationAirport.text.toString(), binding.eDestinationAirport.text.toString())
            }

        }
    }

    private suspend fun fetchAirports() {
        Log.i("fetch", "airports")
        lifecycleScope.launchWhenStarted {
            airportsViewModel.getAirports().collect { state ->
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

    private suspend fun fetchVaccines() {
        lifecycleScope.launchWhenStarted {
            airportsViewModel.getVaccine().collect { state ->
                when (state) {
                    is Resource.Success -> {
                        vaccines.addAll(state.data!!.vaccines)
                    }
                    is Resource.Error -> {
                        //                       state.message?.let { onError(it) }
                    }
                    is Resource.Loading -> {
//                        handleUiVisibility(true)
                    }
                }

            }
        }

    }

    private suspend fun fetchNationalities() {
        lifecycleScope.launchWhenStarted {
            airportsViewModel.nationalities().collect { state ->
                when (state) {
                    is Resource.Success -> {
                        nationality.addAll(state.data!!.nacionalities)
                    }
                    is Resource.Error -> {
                        //                       state.message?.let { onError(it) }
                    }
                    is Resource.Loading -> {
//                        handleUiVisibility(true)
                    }
                }

            }
        }

    }

    private suspend fun fetchRestrictionsByAirport(loc:String, dest: String) {
        lifecycleScope.launchWhenStarted {
            airportsViewModel.fetchRestrictionsByAirport(loc, dest).collect { state ->
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