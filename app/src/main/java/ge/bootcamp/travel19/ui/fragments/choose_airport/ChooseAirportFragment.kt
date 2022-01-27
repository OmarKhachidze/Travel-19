package ge.bootcamp.travel19.ui.fragments.choose_airport

import dagger.hilt.android.AndroidEntryPoint
import ge.bootcamp.travel19.databinding.FragmentChooseAirportBinding
import ge.bootcamp.travel19.extensions.setUpSwitch
import ge.bootcamp.travel19.ui.fragments.BaseFragment

@AndroidEntryPoint
class ChooseAirportFragment : BaseFragment<FragmentChooseAirportBinding>(FragmentChooseAirportBinding::inflate) {
    override fun start() {
        binding.saveSwitch.setUpSwitch()

    }

}