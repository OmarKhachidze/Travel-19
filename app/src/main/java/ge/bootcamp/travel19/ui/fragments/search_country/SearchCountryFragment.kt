package ge.bootcamp.travel19.ui.fragments.search_country

import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentSearchCountryBinding
import ge.bootcamp.travel19.extensions.gone
import ge.bootcamp.travel19.extensions.invisible
import ge.bootcamp.travel19.extensions.showSnack
import ge.bootcamp.travel19.extensions.visible
import ge.bootcamp.travel19.model.countries.Countries
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.ui.fragments.search_country.adapter.CountriesAdapter
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

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

        initRecycler()
    }

    override fun observer() {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            countriesViewModel.allCountries().collect { chooseState(it) }
        }

        var job: Job? = null
        binding.etSearch.doAfterTextChanged {
            job?.cancel()
            job = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                delay(500)
                it?.let { editable ->
                    if (editable.isNotEmpty()) {
                        countriesViewModel.countries(editable.toString())
                            .collect { chooseState(it) }
                    } else {
                        countriesViewModel.allCountries().collect { chooseState(it) }
                    }
                }
            }
        }
    }

    private fun chooseState(state: Resource<out List<Countries>>) {
        when (state) {
            is Resource.Loading -> {
                binding.prLinear.visible()
            }
            is Resource.Success -> {
                binding.prLinear.invisible()
                binding.rvCountries.visible()
                countriesAdapter.submitList(state.data)
            }
            is Resource.Error -> {
                countriesAdapter.submitList(listOf())
                binding.rvCountries.gone()
                binding.prLinear.invisible()
                binding.etSearch.showSnack("${state.message}", R.color.error_red)
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
                SearchCountryFragmentDirections.actionMiSearchCountryToMiCountryRestrictions(
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
            duration = 300L
        }
        returnTransition = MaterialSharedAxis(
            MaterialSharedAxis.Z,
            false
        ).apply {
            duration = 300L
        }
    }
}