package ge.bootcamp.travel19.ui.fragments.auth.sign_up

import android.util.Log.d
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentSignUpBinding
import ge.bootcamp.travel19.extensions.showSnack
import ge.bootcamp.travel19.model.logIn.Data
import ge.bootcamp.travel19.model.singup.UserInfo
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.ui.fragments.auth.AuthViewModel
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    private val authViewModel: AuthViewModel by activityViewModels()

    override fun observer() {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    authViewModel.nationalities.collect { nationalityState ->
                        when (nationalityState) {
                            is Resource.Loading -> {

                            }
                            is Resource.Success -> {
                                nationalityState.data?.let { nationalities ->
                                    binding.acNationalities.apply {
                                        setText(nationalities.nacionalities[0])
                                        setAdapter(ArrayAdapter(
                                                requireContext(),
                                                R.layout.list_item,
                                                nationalities.nacionalities
                                        ))
                                    }
                                }
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
                                vaccineState.data?.let { vaccines ->
                                    binding.acVaccines.apply {
                                        setText(vaccines.vaccines[0])
                                        setAdapter(ArrayAdapter(
                                                requireContext(),
                                                R.layout.list_item,
                                                vaccines.vaccines
                                        ))
                                    }
                                }
                            }
                            is Resource.Error -> {}
                        }
                    }
                }
                launch {
                    authViewModel.authFormState.collect { signUpState ->
                        binding.btnSignUp.tag = signUpState.isDataValid

                        if (signUpState.fullNameError != null) {
                            binding.etFullNameLayout.apply {
                                error = getString(signUpState.fullNameError)
                            }
                        } else
                            binding.etFullNameLayout.error = null
                        if (signUpState.emailError != null) {
                            binding.etRegisterEmailLayout.apply {
                                error = getString(signUpState.emailError)
                            }
                        } else
                            binding.etRegisterEmailLayout.error = null
                        if (signUpState.passwordError != null) {
                            binding.etRegisterPasswordLayout.apply {
                                error = getString(signUpState.passwordError)
                            }
                        } else
                            binding.etRegisterPasswordLayout.error = null
                    }
                }
            }
        }
    }

    override fun start() {
        binding.tvSignIn.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment())
        }
        binding.btnSignUp.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                authViewModel.signUpDataChanged(
                        binding.etFullName.text.toString(),
                        binding.etRegisterEmail.text.toString(),
                        binding.etRegisterPassword.text.toString()
                )
                if (binding.btnSignUp.tag == true) {
                    d("validation", "dfhdfghd")
                    authViewModel.signUpUser(
                            UserInfo(
                                    binding.etRegisterEmail.text.toString(),
                                    binding.etRegisterPassword.text.toString(),
                                    Data(binding.acNationalities.text.toString(),
                                            binding.acVaccines.text.toString())
                            )
                    ).collect { signUpState ->
                        when (signUpState) {
                            is Resource.Loading -> {
                                showLoading(true)
                            }
                            is Resource.Success -> {
                                binding.btnSignUp.showSnack(
                                        signUpState.data?.user?.email.toString(),
                                        R.color.success_green
                                )
                                showLoading(false)
                            }
                            is Resource.Error -> {
                                binding.btnSignUp.showSnack(
                                        signUpState.message.toString(),
                                        R.color.error_red
                                )
                                showLoading(false)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(show: Boolean) {
        binding.prSignUp.isVisible = show
        binding.btnSignUp.apply {
            text = if (show) null else getString(R.string.sign_up)
            isEnabled = !show
            isClickable = !show
        }
    }

}
