package ge.bootcamp.travel19.ui.fragments.choose_type

import androidx.navigation.fragment.findNavController
import ge.bootcamp.travel19.databinding.FragmentChooseTypeBinding
import ge.bootcamp.travel19.ui.fragments.BaseFragment

class ChooseTypeFragment : BaseFragment<FragmentChooseTypeBinding>(FragmentChooseTypeBinding::inflate) {

    override fun start() {
        binding.cvCountries.setOnClickListener {
            findNavController().navigate(ChooseTypeFragmentDirections.actionMiChooseTypeToMiSearchCountry())
        }
        binding.cvAirports.setOnClickListener {
//            findNavController().navigate(R.id.miCountryRestrictions)
        }
    }

}