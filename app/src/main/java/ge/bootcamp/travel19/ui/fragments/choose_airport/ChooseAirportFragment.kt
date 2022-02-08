package ge.bootcamp.travel19.ui.fragments.choose_airport

import androidx.core.widget.addTextChangedListener
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentChooseAirportBinding
import ge.bootcamp.travel19.domain.model.airports.RestrictionByAirport
import ge.bootcamp.travel19.domain.model.airports.plans.TravelPlan
import ge.bootcamp.travel19.domain.model.airports.plans.TravelPlanModel
import ge.bootcamp.travel19.domain.states.Resource
import ge.bootcamp.travel19.extensions.*
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.ui.fragments.choose_airport.adapter.TravelPlansAdapter
import ge.bootcamp.travel19.utils.Constants.USER_BASICS_KEY

@AndroidEntryPoint
class ChooseAirportFragment :
    BaseFragment<FragmentChooseAirportBinding>(FragmentChooseAirportBinding::inflate) {

    private val chooseAirportViewModel: ChooseAirportViewModel by viewModels()
    private val plansAdapter: TravelPlansAdapter = TravelPlansAdapter()
    private val airports: MutableList<String> = mutableListOf()
    private var planId: String? = null
    private var position: Int? = null

    private fun navigateToRestrictionsFragment() {
        binding.apply {
            findNavController().navigate(
                ChooseAirportFragmentDirections.actionChooseAirportFragmentToAirportRestrictionFragment(
                    RestrictionByAirport(
                        etAirportLocation.text.toString(),
                        etAirportDestination.text.toString(),
                        if (etAirportVaccine.text.toString() == getString(R.string.none)) "" else etAirportVaccine.text.toString(),
                        if (etAirportNationality.text.toString() == getString(R.string.none)) "" else etAirportNationality.text.toString(),
                        "TBS"
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
                    val plan = TravelPlanModel(
                        etAirportLocation.text.toString(),
                        etAirportDestination.text.toString(),
                        etAirportVaccine.text.toString(),
                        etAirportNationality.text.toString(),
                    )
                    if (btnSearch.text.equals(getString(R.string.Search))) {
                        if (saveSwitch.isChecked) {
                            chooseAirportViewModel.saveTravelPlan(plan)
                        } else
                            navigateToRestrictionsFragment()
                    } else {
                        planId?.let {
                            chooseAirportViewModel.updateTravelPlan(it, plan)
                        }
                    }
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
                }
                else -> {
                    binding.root.showSnack(
                        getString(R.string.sign_up_warning),
                        R.color.warning_orange
                    )
                }
            }

        }

        chooseAirportViewModel.updatePlanState.collectWhenStarted(viewLifecycleOwner) { updateTravelPlanState ->
            when (updateTravelPlanState) {
                is Resource.Loading -> {
                    binding.apply {
                        prSave.visible()
                        btnSearch.text = null
                    }
                }
                is Resource.Success -> {
                    binding.apply {
                        btnSearch.text = getString(R.string.Search)
                        prSave.gone()
                    }
                    position?.let {
                        plansAdapter.notifyItemChanged(it)
                    }
                }
                is Resource.Error -> {
                    updateTravelPlanState.message?.let { msg ->
                        binding.btnSearch.showSnack(
                            msg, R.color.error_red
                        )
                    }
                    binding.apply {
                        prSave.gone()
                        btnSearch.text = getString(R.string.Search)
                    }
                }
                else -> {
                    binding.root.showSnack(
                        getString(R.string.sign_up_warning_update_plan),
                        R.color.warning_orange
                    )
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
                                getString(R.string.plan_delete_confirmation),
                                R.color.success_green
                            )
                        }
                    }
                    position?.let {
                        plansAdapter.apply {
                            val current = mutableListOf<TravelPlan>()
                            current.addAll(currentList)
                            current.removeAt(it)
                            if (current.isEmpty()) {
                                binding.travelPlanText.visible()
                            }
                            submitList(current)
                            notifyItemRemoved(it)
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
                else -> {}
            }

        }

        chooseAirportViewModel.getPlanState.collectWhenStarted(viewLifecycleOwner) { travelPlans ->
            when (travelPlans) {
                is Resource.Loading -> {
                    binding.apply {
                        travelPlanText.gone()
                        prTravelPlan.visible()
                    }
                }
                is Resource.Success -> {
                    binding.apply {
                        btnTravelPlanSignUp.gone()
                        prTravelPlan.gone()
                        travelPlans.data?.let { plans ->
                            if (plans.travelPlans.isNotEmpty())
                                plansAdapter.submitList(plans.travelPlans)
                            else {
                                travelPlanText.visible()
                                travelPlanText.text =
                                    getString(R.string.you_don_t_have_saved_travel_plans)
                            }
                        }
                    }

                }
                is Resource.Error -> {
                    binding.apply {
                        btnTravelPlanSignUp.gone()
                        travelPlanText.visible()
                        travelPlanText.text = travelPlans.message
                        prTravelPlan.gone()
                    }
                }
                else -> {
                    binding.btnTravelPlanSignUp.visible()
                }
            }

        }

        chooseAirportViewModel.airportsFormState.collectWhenStarted(viewLifecycleOwner) { airportFormState ->
            binding.apply {
                btnSearch.tag = airportFormState.isDataValid
                etAirportLocation.validateInput(airportFormState.location)
                etAirportDestination.validateInput(airportFormState.destination)
            }
        }
        chooseAirportViewModel.airports.collectWhenStarted(viewLifecycleOwner) { airportsState ->
            when (airportsState) {
                is Resource.Loading -> {
                    binding.etAirportLocation.setData()
                }
                is Resource.Success -> {
                    binding.apply {
                        airports.clear()
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
                else -> {}
            }
        }

        chooseAirportViewModel.vaccines.collectWhenStarted(viewLifecycleOwner) { vaccinesState ->
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
                else -> {}
            }
        }

        chooseAirportViewModel.nationalities.collectWhenStarted(viewLifecycleOwner) { nationalitiesState ->
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
                else -> {}
            }
        }
    }

    private fun initRecycler() {
        binding.rvTravelPlans.apply {
            adapter = plansAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        plansAdapter.apply {
            planItemOnClick = { plan, _ ->
                plan.apply {
                    val action = ChooseAirportFragmentDirections
                        .actionChooseAirportFragmentToAirportRestrictionFragment(
                            RestrictionByAirport(
                                source.toString(),
                                destination.toString(),
                                vaccine.toString(),
                                nationality.toString()
                            )
                        )
                    findNavController().navigate(action)
                }
            }
            updatePlanItemOnClick = { plan, position ->
                binding.apply {
                    plan.apply {
                        this@ChooseAirportFragment.planId = id
                        this@ChooseAirportFragment.position = position
                        etAirportLocation.setData(source, null, airports)
                        etAirportDestination.setData(destination, null, airports)
                        etAirportVaccine.setData(
                            vaccine,
                            null,
                            chooseAirportViewModel.vaccines.value.data?.vaccines
                        )
                        etAirportNationality.setData(
                            nationality,
                            null,
                            chooseAirportViewModel.nationalities.value.data?.nacionalities
                        )
                    }
                    btnSearch.apply {
                        text = getString(R.string.update_text)
                    }

                }
            }
            deletePlanItemOnClick = { id, position ->
                this@ChooseAirportFragment.position = position
                chooseAirportViewModel.deleteTravelPlan(id)
            }
        }
    }
}
