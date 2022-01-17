package ge.bootcamp.travel19.ui.fragments.choose_airport

import android.text.Editable
import android.util.Log
import android.widget.ArrayAdapter
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentChooseAirportBinding
import ge.bootcamp.travel19.model.airports.RestrictionByAirport
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.ui.fragments.auth.AuthViewModel
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChooseAirportFragment : BaseFragment<FragmentChooseAirportBinding>(FragmentChooseAirportBinding::inflate) {

    private val viewModel: AuthViewModel by activityViewModels()

    override fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.airports.collect { vaccineState ->
                        when (vaccineState) {
                            is Resource.Loading -> {

                            }
                            is Resource.Success -> {
                                binding.apply {
                                    val airports: MutableList<String> = mutableListOf()

                                    vaccineState.data?.airports?.onEach {
                                        airports.add(it.code.toString())
                                    }

                                    etAirportLocation.setAdapter(ArrayAdapter(
                                            requireContext(),
                                            R.layout.list_item,
                                            airports
                                    ))
                                    etAirportDestination.setAdapter(ArrayAdapter(
                                            requireContext(),
                                            R.layout.list_item,
                                            airports
                                    ))
                                }
                            }
                            is Resource.Error -> {}
                        }
                    }
                }
                launch {
                    viewModel.nationalities.collect { nationalityState ->
                        when (nationalityState) {
                            is Resource.Loading -> {

                            }
                            is Resource.Success -> {
                                binding.etAirportNationality.setAdapter(ArrayAdapter(
                                        requireContext(),
                                        R.layout.list_item,
                                        nationalityState.data?.nacionalities ?: listOf()
                                ))
                            }
                            is Resource.Error -> {}
                        }
                    }
                }

                launch {
                    viewModel.vaccines.collect { vaccineState ->
                        when (vaccineState) {
                            is Resource.Loading -> {

                            }
                            is Resource.Success -> {
                                binding.etAirportVaccine.setAdapter(ArrayAdapter(
                                        requireContext(),
                                        R.layout.list_item,
                                        vaccineState.data?.vaccines ?: listOf()
                                ))
                            }
                            is Resource.Error -> {}
                        }
                    }
                }
            }
        }
    }

    private suspend fun checkToken(): String? {
        return viewModel.checkTokenInDataStore(stringPreferencesKey("user_token"))
    }

    override fun start() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
           val token = checkToken()
            Log.i("token", token.toString())
            if (!token.isNullOrEmpty()) {
                viewModel.getUserInfo(token).collect {
                    when (it) {
                        is Resource.Loading -> {
                        }
                        is Resource.Success -> {
                            binding.etAirportVaccine.setText(it.data?.user?.data?.vaccine)
                            binding.etAirportNationality.setText(it.data?.user?.data?.nationalities)
                        }
                        is Resource.Error -> {
                            Log.i("errToken", it.toString())
                        }
                    }
                }
            }
        }

        binding.btnNext.setOnClickListener {
            val action = ChooseAirportFragmentDirections
                    .actionChooseAirportFragmentToAirportRestrictionFragment(RestrictionByAirport(
                            binding.etAirportLocation.text.toString(),
                            binding.etAirportDestination.text.toString(),
                            binding.etAirportVaccine.text.toString(),
                            binding.etAirportNationality.text.toString()
                    ))

            findNavController().navigate(action)


        }
    }

}