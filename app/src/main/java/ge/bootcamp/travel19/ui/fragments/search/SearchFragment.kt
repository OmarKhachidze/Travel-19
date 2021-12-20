package ge.bootcamp.travel19.ui.fragments.search

import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ge.bootcamp.travel19.databinding.FragmentSearchBinding
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.ui.fragments.search.adapter.CountriesAdapter
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val searchViewModel: SearchViewModel by activityViewModels()
    private val countriesAdapter: CountriesAdapter = CountriesAdapter()
    override fun start() {
        Log.d("TAG", "Not yet implemented")
        initRecycler()
        var job: Job? = null
        binding.etSearch.addTextChangedListener {
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                it?.let {
                    if (it.toString().isNotEmpty()) {
                        searchViewModel.countries(it.toString()).collect { state ->
                            when (state) {
                                is Resource.Success -> {
                                    Log.d("state", "Success")
                                    countriesAdapter.submitList(state.data)
                                }
                                is Resource.Error -> {
                                    Log.d("state", "Error")
                                }

                                is Resource.Loading -> {
                                    Log.d("state", "Loading")
                                }
                            }
                        }
                    }else {
                        countriesAdapter.submitList(listOf())
                    }
                }
            }
        }
    }

    private fun initRecycler() {
        binding.rvCountries.apply {
            adapter = countriesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}