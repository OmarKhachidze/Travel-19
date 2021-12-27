package ge.bootcamp.travel19.ui.fragments.choose_type

import androidx.navigation.fragment.findNavController
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentChooseTypeBinding
import ge.bootcamp.travel19.ui.fragments.BaseFragment

class ChooseTypeFragment : BaseFragment<FragmentChooseTypeBinding>(FragmentChooseTypeBinding::inflate) {

    override fun start() {
        binding.cvCountries.setOnClickListener {
            findNavController().navigate(R.id.miCountryRestrictions)
        }
        binding.cvAirports.setOnClickListener {
//            findNavController().navigate(R.id.miCountryRestrictions)
        }
    }

}