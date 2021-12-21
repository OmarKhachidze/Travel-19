package ge.bootcamp.travel19.ui.fragments.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentLogInBinding
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.collect


class LogInFragment: BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {

    private val viewModel: LogInViewModel by activityViewModels()

    override fun start() {

        binding.register.setOnClickListener {
            it.findNavController().navigate(R.id.action_logInFragment_to_registerFragment)
        }
    }



}