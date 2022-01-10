package ge.bootcamp.travel19.ui.fragments.home

import androidx.navigation.fragment.findNavController
import ge.bootcamp.travel19.databinding.FragmentHomeBinding
import ge.bootcamp.travel19.ui.fragments.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun start() {
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionMiHomeToRegisterFragment())
        }
    }

}