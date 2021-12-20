package ge.bootcamp.travel19.ui.fragments.home

import android.util.Log
import androidx.navigation.findNavController
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentHomeBinding
import ge.bootcamp.travel19.ui.fragments.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun start() {
        Log.d("TAG", "Not yet implemented")
        binding.home.setOnClickListener {
            it.findNavController().navigate(R.id.action_miHome_to_logInFragment)
        }
    }

}