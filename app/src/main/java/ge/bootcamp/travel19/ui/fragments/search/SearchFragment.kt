package ge.bootcamp.travel19.ui.fragments.search

import android.util.Log

import androidx.navigation.fragment.findNavController
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentSearchBinding
import ge.bootcamp.travel19.ui.activity.MainActivity
import ge.bootcamp.travel19.ui.fragments.BaseFragment


class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    override fun start() {
        Log.d("TAG", "Not yet implemented")
        binding.cvCountries.setOnClickListener {
            findNavController().navigate(R.id.miCountriesRestriction)
          //  (activity as MainActivity).hideBottomNavigationView()
        }
        binding.cvAirports.setOnClickListener{
            findNavController().navigate(R.id.action_miSearch_to_airportsFragment)
        }
    }


}