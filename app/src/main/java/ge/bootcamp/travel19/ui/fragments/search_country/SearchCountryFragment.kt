package ge.bootcamp.travel19.ui.fragments.search_country

import android.os.Bundle
import android.util.Log.d
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentSearchCountryBinding
import ge.bootcamp.travel19.extensions.*
import ge.bootcamp.travel19.model.countriesv3.V3CountriesItem
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.ui.fragments.search_country.adapter.CountriesAdapter
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchCountryFragment :
    BaseFragment<FragmentSearchCountryBinding>(FragmentSearchCountryBinding::inflate) {

    private val countriesAdapter: CountriesAdapter = CountriesAdapter()
    private val countriesViewModel: SearchCountryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyAnim()
    }

    override fun start() {
        binding.searchToolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        onClickListeners()
        initRecycler()
    }

    private fun onClickListeners() {
        binding.btnChooseAirport.setOnClickListener {
            findNavController().navigate(SearchCountryFragmentDirections.actionSearchCountryFragmentToChooseAirportFragment())
        }
    }

    override fun observer() {
        collectLatestLifecycleFlow(countriesViewModel.allCountries) {
            chooseState(it)
        }
//                launch {
//                    countriesViewModel.results.collect {
//                        chooseState(it)
//                    }
//                }

        var job: Job? = null
        binding.etSearch.doAfterTextChanged {
            job?.cancel()
            job = viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    delay(500)
                    it?.let { editable ->
                        if (editable.isNotEmpty()) {
                            countriesViewModel.countries(editable.toString())
                                .flowWithLifecycle(
                                    viewLifecycleOwner.lifecycle,
                                    Lifecycle.State.STARTED
                                ).collect { chooseState(it) }
//                        countriesViewModel.setQuery(editable.toString())
                        } else {
                            countriesAdapter.submitList(
                                countriesViewModel.allCountries.value.data
                            )
                        }
                    }
                }
            }
        }
    }

    private fun chooseState(state: Resource<out List<V3CountriesItem>>) {
        when (state) {
            is Resource.Loading -> {
                binding.prLinear.visible()
            }
            is Resource.Success -> {
                binding.apply {
                    prLinear.invisible()
                    rvCountries.visible()
                }
                countriesAdapter.submitList(state.data)
            }
            is Resource.Error -> {
                binding.apply {
                    prLinear.invisible()
                    etSearch.showSnack("${state.message}", R.color.error_red)
                }
            }
        }
    }

    private fun initRecycler() {
        binding.rvCountries.apply {
            adapter = countriesAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        countriesAdapter.countryItemOnClick = { country ->
            findNavController().navigate(
                SearchCountryFragmentDirections.actionSearchCountryFragmentToCountryRestrictionsFragment(
                    country
                )
            )

        }
    }

    private fun applyAnim() {
        enterTransition = MaterialSharedAxis(
            MaterialSharedAxis.Z,
            true
        ).apply {
            duration = 500L
        }
        returnTransition = MaterialSharedAxis(
            MaterialSharedAxis.Z,
            false
        ).apply {
            duration = 500L
        }
    }
}