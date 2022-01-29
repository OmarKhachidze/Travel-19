package ge.bootcamp.travel19.ui.fragments.choose_airport

import android.annotation.SuppressLint
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentChooseAirportBinding
import ge.bootcamp.travel19.extensions.setUpSwitch
import ge.bootcamp.travel19.model.airports.RestrictionByAirport
import ge.bootcamp.travel19.model.airports.plans.PostTravelPlan
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.ui.fragments.auth.AuthViewModel
import ge.bootcamp.travel19.ui.fragments.choose_airport.adapter.TravelPlansAdapter
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChooseAirportFragment : BaseFragment<FragmentChooseAirportBinding>(FragmentChooseAirportBinding::inflate) {

    private val viewModel: AuthViewModel by activityViewModels()
    private val chooseAirportViewModel: ChooseAirportViewModel by activityViewModels()
    private val listOfPlans: MutableList<PostTravelPlan> = mutableListOf()
    private val plansAdapter: TravelPlansAdapter = TravelPlansAdapter()
    private var token: String? = null

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun start() {
        binding.saveSwitch.setUpSwitch()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    token = checkToken()
                    token?.let { authToken ->
                        Log.i("tokenExist", "it.toString()")
                        getPlans(authToken)
                        viewModel.getUserInfo(authToken).collect{
                            when (it) {
                                is Resource.Loading -> {
                                }
                                is Resource.Success -> {
//                                    binding.etAirportVaccine.setText(it.data?.user?.data?.vaccine)
//                                    binding.etAirportNationality.setText(it.data?.user?.data?.nationalities)
                                }
                                is Resource.Error -> {
                                    Log.i("errToken", it.toString())
                                }
                            }
                        }
                    }
                }
            }

        }
        initRecycler()
        observer1()
        listeners()

    }

    private suspend fun getPlans(token:String) {

                chooseAirportViewModel.getTravelPlan(token).collect { plan ->
                    Log.i("tokenExist", "plan.toString()")
                    when (plan) {
                        is Resource.Loading -> {
                        }
                        is Resource.Success -> {
                            Log.i("success", plan.toString())
                            plansAdapter.submitList(plan.data?.travelPlans)
                        }
                        is Resource.Error -> {
                            Log.i("errToken", plan.toString())
                        }
                    }

                }
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    private fun observer1() {
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
                                    etAirportLocation.setAdapter(
                                        ArrayAdapter(
                                        requireContext(),
                                        R.layout.list_item,
                                        airports
                                    )
                                    )
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
                     token = checkToken()
                    token?.let { authToken ->
                        viewModel.getUserInfo(authToken).collect{
                            when (it) {
                                is Resource.Loading -> {
                                }
                                is Resource.Success -> {
//                                    binding.etAirportVaccine.setText(it.data?.user?.data?.vaccine)
//                                    binding.etAirportNationality.setText(it.data?.user?.data?.nationalities)
                                }
                                is Resource.Error -> {
                                    Log.i("errToken", it.toString())
                                }
                            }
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
        return viewModel.checkTokenInDataStore(stringPreferencesKey("userToken"))
    }

    private fun getPlanData(): PostTravelPlan {
       return  PostTravelPlan( nationality = binding.etAirportNationality.text.toString(),
           vaccine = binding.etAirportVaccine.text.toString(),
            source = binding.etAirportLocation.text.toString(),
            destination = binding.etAirportDestination.text.toString(),
        )
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    private fun savePlan(token: String) {
        val plan = getPlanData()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                chooseAirportViewModel.postTravelPlan(token, plan).collect {
                    when (it) {
                                is Resource.Loading -> {
                                }
                                is Resource.Success -> {
                                    Log.i("success", plan.toString())
                                    Toast.makeText(requireContext(), "plan is saved", Toast.LENGTH_SHORT).show()
                                }
                                is Resource.Error -> {
                                    Toast.makeText(requireContext(), "plan is not saved", Toast.LENGTH_SHORT).show()
                                    Log.i("errToken", plan.toString())
                                }
                            }
                }
            }
        }
    }

    private fun listeners() {
        binding.btnSearch.setOnClickListener {
            val action = ChooseAirportFragmentDirections
                .actionChooseAirportFragmentToAirportRestrictionFragment(
                    RestrictionByAirport(
                        binding.etAirportLocation.text.toString(),
                        binding.etAirportDestination.text.toString(),
                        binding.etAirportVaccine.text.toString(),
                        binding.etAirportNationality.text.toString()
                    )
                )

            findNavController().navigate(action)
        }

        binding.saveSwitch.setOnClickListener {
            if(binding.saveSwitch.isChecked) {
                Log.i("ischeckd", "switch is checjed")
                token?.let {
                    savePlan(it)
                }
            }
        }
    }

    private fun initRecycler() {
        binding.rvTravelPlans.apply {
            adapter = plansAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        plansAdapter.planItemOnClick = {

            val action = ChooseAirportFragmentDirections
                .actionChooseAirportFragmentToAirportRestrictionFragment(
                    RestrictionByAirport(
                        it.source.toString(),
                        it.destination.toString(),
                        it.vaccine.toString(),
                        it.nationality.toString()
                    )
                )

            findNavController().navigate(action)
        }
    }

}