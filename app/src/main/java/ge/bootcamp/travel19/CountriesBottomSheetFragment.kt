package ge.bootcamp.travel19

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ge.bootcamp.travel19.databinding.FragmentCountriesBottomSheetBinding
import ge.bootcamp.travel19.ui.fragments.search.SearchViewModel
import ge.bootcamp.travel19.ui.fragments.search.adapter.CountriesAdapter
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import android.app.Activity

import android.util.DisplayMetrics

import android.R
import android.app.Dialog
import android.content.DialogInterface
import android.content.DialogInterface.OnShowListener

import android.widget.FrameLayout


class CountriesBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentCountriesBottomSheetBinding? = null
    private val binding: FragmentCountriesBottomSheetBinding get() = _binding!!

    private val searchViewModel: SearchViewModel by activityViewModels()
    private val countriesAdapter: CountriesAdapter = CountriesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCountriesBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        val offsetFromTop = 200
        (dialog as? BottomSheetDialog)?.behavior?.apply {
//            expandedOffset = Resources.getSystem().displayMetrics.heightPixels
            peekHeight = 200
            state = BottomSheetBehavior.STATE_EXPANDED
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            searchViewModel.allCountries.collect { state ->
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
        }


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
                    } else {
                        countriesAdapter.submitList(listOf())
                    }
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener { dialog: DialogInterface ->

            (dialog as? BottomSheetDialog)?.behavior?.apply {
                peekHeight = Resources.getSystem().displayMetrics.heightPixels
                state = BottomSheetBehavior.STATE_EXPANDED
            }

        }
        return bottomSheetDialog
    }

    private fun initRecycler() {
        binding.rvCountries.apply {
            adapter = countriesAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}