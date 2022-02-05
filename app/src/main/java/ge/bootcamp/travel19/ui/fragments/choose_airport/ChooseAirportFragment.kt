package ge.bootcamp.travel19.ui.fragments.choose_airport

import android.util.Log.d
import androidx.core.widget.addTextChangedListener
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentChooseAirportBinding
import ge.bootcamp.travel19.extensions.*
import ge.bootcamp.travel19.domain.model.airports.RestrictionByAirport
import ge.bootcamp.travel19.domain.model.airports.plans.SaveTravelPlan
import ge.bootcamp.travel19.domain.model.airports.plans.TravelPlan
import ge.bootcamp.travel19.domain.states.Resource
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.ui.fragments.choose_airport.adapter.TravelPlansAdapter
import ge.bootcamp.travel19.utils.Constants.USER_BASICS_KEY
import ge.bootcamp.travel19.utils.Constants.USER_TOKEN_KEY
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChooseAirportFragment :
    BaseFragment<FragmentChooseAirportBinding>(FragmentChooseAirportBinding::inflate) {

    private val chooseAirportViewModel: ChooseAirportViewModel by viewModels()
    private val plansAdapter: TravelPlansAdapter = TravelPlansAdapter()
    private val airports: MutableList<String> = mutableListOf()

    private fun navigateToRestrictionsFragment() {
        binding.apply {
            findNavController().navigate(
                ChooseAirportFragmentDirections.actionChooseAirportFragmentToAirportRestrictionFragment(
                    RestrictionByAirport(
                        etAirportLocation.text.toString(),
                        etAirportDestination.text.toString(),
                        if (etAirportVaccine.text.toString() == getString(R.string.none)) "" else etAirportVaccine.text.toString(),
                        if (etAirportNationality.text.toString() == getString(R.string.none)) "" else etAirportNationality.text.toString(),
                    )
                )
            )
        }
    }

    override fun start() {
        binding.saveSwitch.setUpSwitch()
        listeners()
        initRecycler()
        chooseAirportViewModel.getTravelPlan()
    }

    private fun listeners() {
        binding.apply {
            etAirportLocation.addTextChangedListener { location ->
                if (airports.isNotEmpty())
                    etAirportDestination.setData(null, null, airports.filter {
                        it != location.toString()
                    })
            }
            btnSearch.setOnClickListener {
                chooseAirportViewModel.airportsDataChanged(
                    etAirportLocation.text.toString(),
                    etAirportDestination.text.toString()
                )
                if (btnSearch.tag == true) {
                    if (saveSwitch.isChecked) {
                        chooseAirportViewModel.saveTravelPlan(
                            SaveTravelPlan(
                                etAirportLocation.text.toString(),
                                etAirportDestination.text.toString(),
                                etAirportVaccine.text.toString(),
                                etAirportNationality.text.toString(),
                            )
                        )
                    } else
                        navigateToRestrictionsFragment()
                }
            }
        }
    }

    override fun observer() {

        chooseAirportViewModel.savePlanState.collectWhenStarted(viewLifecycleOwner) { saveTravelPlanState ->
            when (saveTravelPlanState) {
                is Resource.Loading -> {
                    binding.prSave.visible()
                    binding.btnSearch.text = null
                }
                is Resource.Success -> {
                    binding.prSave.gone()
                    navigateToRestrictionsFragment()
                }
                is Resource.Error -> {
                    saveTravelPlanState.message?.let { msg ->
                        binding.btnSearch.showSnack(
                            msg, R.color.error_red
                        )
                    }
                    binding.prSave.gone()
                    binding.btnSearch.text = getString(R.string.Search)
                }
            }

        }

        chooseAirportViewModel.deletePlanState.collectWhenStarted(viewLifecycleOwner) { planState ->
            when (planState) {
                is Resource.Loading -> {
                    binding.prTravelPlan.visible()
                }
                is Resource.Success -> {
                    binding.apply {
                        prTravelPlan.gone()
                        if (planState.data?.success == true) {
                            rvTravelPlans.showSnack(
                                planState.data.success.toString(),
                                R.color.success_green
                            )
                        }
                    }
                }
                is Resource.Error -> {
                    binding.apply {
                        prTravelPlan.gone()
                        rvTravelPlans.showSnack(
                            planState.message.toString(),
                            R.color.error_red
                        )
                    }
                }
            }

        }

        chooseAirportViewModel.getPlanState.collectWhenStarted(viewLifecycleOwner) { travelPlans ->
            d("Travel plan", " $travelPlans ")
            when (travelPlans) {
                is Resource.Loading -> {
                    binding.prTravelPlan.visible()
                    binding.travelPlanText.gone()
                    binding.btnTravelPlanSignUp.gone()
                }
                is Resource.Success -> {
                    binding.prTravelPlan.gone()
                    travelPlans.data?.let { plans ->
                        if (plans.travelPlans.isNotEmpty())
                            plansAdapter.submitList(plans.travelPlans)
                        else {
                            binding.travelPlanText.visible()
                            binding.travelPlanText.text =
                                getString(R.string.you_don_t_have_saved_travel_plans)
                        }
                    }
                }
                is Resource.Error -> {
                    binding.travelPlanText.visible()
                    binding.travelPlanText.text = travelPlans.message
                    binding.prTravelPlan.gone()
                    binding.btnTravelPlanSignUp.gone()
                }
            }

        }

//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                chooseAirportViewModel.readUserInfo(stringPreferencesKey(USER_TOKEN_KEY))
//                    ?.let { token ->
//                        chooseAirportViewModel.getTravelPlan(token)
//                            .collectLatest { travelPlanState ->
//                                when (travelPlanState) { travelPlans ->
//                                    is Resource.Loading -> {
//                                        binding.prTravelPlan.visible()
//                                        binding.travelPlanText.gone()
//                                        binding.btnTravelPlanSignUp.gone()
//                                    }
//                                    is Resource.Success -> {
//                                        binding.prTravelPlan.gone()
//                                        travelPlanState.data?.let {
//                                            if (travelPlans.travelPlan.isNotEmpty())
//                                                plansAdapter.submitList(travelPlans.travelPlan)
//                                            else {
//                                                binding.travelPlanText.visible()
//                                                binding.travelPlanText.text =
//                                                    getString(R.string.you_don_t_have_saved_travel_plans)
//                                            }
//                                        }
//                                    }
//                                    is Resource.Error -> {
//                                        binding.travelPlanText.gone()
//                                        binding.prTravelPlan.visible()
//                                        binding.btnTravelPlanSignUp.gone()
//                                    }
//                                }
//                            }
//                    }
//                binding.travelPlanText.gone()
//                binding.prTravelPlan.gone()
//                binding.btnTravelPlanSignUp.visible()
//            }
//        }

        collectLatestLifecycleFlow(chooseAirportViewModel.airportsFormState) { airportFormState ->
            binding.apply {
                btnSearch.tag = airportFormState.isDataValid
                etAirportLocation.validateInput(airportFormState.location)
                etAirportDestination.validateInput(airportFormState.destination)
            }
        }

        collectLatestLifecycleFlow(chooseAirportViewModel.airports) { airportsState ->
            when (airportsState) {
                is Resource.Loading -> {
                    binding.etAirportLocation.setData()
                }
                is Resource.Success -> {
                    binding.apply {
                        airportsState.data?.airports?.onEach {
                            airports.add(it.code.toString())
                        }
                        binding.apply {
                            etAirportLocation.setData(null, null, airports)
                        }
                    }
                }
                is Resource.Error -> {
                    binding.etAirportLocation.setData(null, airportsState.message)
                    binding.etAirportDestination.setData(null, airportsState.message)
                }
            }
        }

        collectLatestLifecycleFlow(chooseAirportViewModel.vaccines) { vaccinesState ->
            when (vaccinesState) {
                is Resource.Loading -> {
                    binding.etAirportVaccine.setData()
                }
                is Resource.Success -> {
                    val userInfo =
                        chooseAirportViewModel.readUserInfo(stringSetPreferencesKey(USER_BASICS_KEY))
                    vaccinesState.data?.let {
                        binding.etAirportVaccine.setData(
                            userInfo?.first() ?: getString(R.string.none),
                            null,
                            it.vaccines
                        )
                    }
                }
                is Resource.Error -> {
                    binding.etAirportVaccine.setData(null, vaccinesState.message)
                }
            }
        }

        collectLatestLifecycleFlow(chooseAirportViewModel.nationalities) { nationalitiesState ->
            when (nationalitiesState) {
                is Resource.Loading -> {
                    binding.etAirportNationality.setData()
                }
                is Resource.Success -> {
                    val userInfo =
                        chooseAirportViewModel.readUserInfo(stringSetPreferencesKey(USER_BASICS_KEY))
                    nationalitiesState.data?.let {
                        binding.etAirportNationality.setData(
                            userInfo?.last() ?: getString(R.string.none),
                            null,
                            it.nacionalities
                        )
                    }
                }
                is Resource.Error -> {
                    binding.etAirportNationality.setData(null, nationalitiesState.message)
                }
            }
        }
    }

    private fun initRecycler() {
        binding.rvTravelPlans.apply {
            adapter = plansAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
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
        plansAdapter.apply {
            deleteItemOnClick = { id, position ->
                chooseAirportViewModel.deleteTravelPlan(id)
//                val current = mutableListOf<TravelPlan>()
//                current.addAll(plansAdapter.currentList)
//                current.removeAt(position)
//                if (current.isEmpty()) {
//                    binding.travelPlanText.visible()
//                }
//                submitList(current)
//                notifyItemRemoved(position)

            }
        }
    }
}