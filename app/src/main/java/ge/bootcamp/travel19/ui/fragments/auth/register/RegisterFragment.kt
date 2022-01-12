package ge.bootcamp.travel19.ui.fragments.auth.register

import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentRegisterBinding
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.ui.fragments.auth.AuthViewModel
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val authViewModel: AuthViewModel by activityViewModels()

    override fun observer() {

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    authViewModel.nationalities.collect { nationalityState ->
                        when (nationalityState) {
                            is Resource.Loading -> {

                            }
                            is Resource.Success -> {
                                binding.acNationalities.setAdapter(ArrayAdapter(
                                        requireContext(),
                                        R.layout.list_item,
                                        nationalityState.data?.nacionalities ?: listOf()
                                ))
                            }
                            is Resource.Error -> {}
                        }
                    }
                }

                launch {
                    authViewModel.vaccines.collect { vaccineState ->
                        when (vaccineState) {
                            is Resource.Loading -> {

                            }
                            is Resource.Success -> {
                                binding.acVaccines.setAdapter(ArrayAdapter(
                                        requireContext(),
                                        R.layout.list_item,
                                        vaccineState.data?.vaccines ?: listOf()
                                ))
                            }
                            is Resource.Error -> {}
                        }
                    }
                }
            }
        }

    }

    override fun start() {

    }
}