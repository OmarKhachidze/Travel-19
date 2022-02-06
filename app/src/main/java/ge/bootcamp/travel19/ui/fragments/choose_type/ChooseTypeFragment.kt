package ge.bootcamp.travel19.ui.fragments.choose_type

import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialSharedAxis
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentChooseTypeBinding
import ge.bootcamp.travel19.ui.activity.MainActivity
import ge.bootcamp.travel19.ui.fragments.BaseFragment

class ChooseTypeFragment :
    BaseFragment<FragmentChooseTypeBinding>(FragmentChooseTypeBinding::inflate) {

    override fun start() {
        binding.cvCountries.setOnClickListener {
            (requireActivity() as MainActivity).currentNavigationFragment?.apply {
                exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
                    duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
                }
                reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
                    duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
                }
            }
            findNavController().navigate(ChooseTypeFragmentDirections.actionChooseTypeFragmentToSearchCountryFragment())
        }
        binding.cvAirports.setOnClickListener {
            findNavController().navigate(ChooseTypeFragmentDirections.actionChooseTypeFragmentToChooseAirportsFragment())
        }
    }

}