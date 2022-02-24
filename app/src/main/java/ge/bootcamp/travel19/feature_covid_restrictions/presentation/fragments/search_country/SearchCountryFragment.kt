package ge.bootcamp.travel19.feature_covid_restrictions.presentation.fragments.search_country

import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentSearchCountryBinding
import ge.bootcamp.travel19.feature_covid_restrictions.domain.model.countriesv3.V3CountriesItem
import ge.bootcamp.travel19.feature_covid_restrictions.domain.utils.Resource
import ge.bootcamp.travel19.extensions.collectWhenStarted
import ge.bootcamp.travel19.extensions.invisible
import ge.bootcamp.travel19.extensions.showSnack
import ge.bootcamp.travel19.extensions.visible
import ge.bootcamp.travel19.feature_covid_restrictions.presentation.fragments.BaseFragment
import ge.bootcamp.travel19.feature_covid_restrictions.presentation.fragments.search_country.adapter.CountriesAdapter
import kotlinx.coroutines.Job

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
        var job: Job? = null
        binding.etSearch.doAfterTextChanged { editable ->
            job?.cancel()
            job = countriesViewModel.countries(editable)
        }
    }

    override fun observer() {

        countriesViewModel.allCountries.collectWhenStarted(viewLifecycleOwner) {
            chooseState(it)
        }

        countriesViewModel.countryState.collectWhenStarted(viewLifecycleOwner) {
            chooseState(it)
        }

    }

    private fun chooseState(state: Resource<out List<V3CountriesItem>>) {
        when (state) {
            is Resource.Loading -> {
                binding.prLinear.visible()
            }
            is Resource.Empty -> {
                binding.apply {
                    prLinear.invisible()
                    rvCountries.visible()
                }
                countriesAdapter.submitList(countriesViewModel.allCountries.value.data)
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