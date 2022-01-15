package ge.bootcamp.travel19.ui.fragments.auth.home

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentHomeBinding
import ge.bootcamp.travel19.extensions.showSnack
import ge.bootcamp.travel19.model.logIn.LoginRequest
import ge.bootcamp.travel19.model.singup.SignUpResponse
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.ui.fragments.auth.AuthViewModel
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.collect


// SafeClickListener

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val authViewModel: AuthViewModel by activityViewModels()

    override fun start() {
        onClickListeners()
    }

    override fun observer() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            val shake: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.vibrate)
            authViewModel.authFormState.collect { loginState ->
                binding.btnLogin.tag = loginState.isDataValid
                if (loginState.emailError != null) {
                    binding.etEmailLayout.apply {
                        startAnimation(shake)
                        error = getString(loginState.emailError)
                    }
                    binding.btnLogin.showSnack(getString(loginState.emailError.toInt()), R.color.error_red)
                } else
                    binding.etEmailLayout.error = null
                if (loginState.passwordError != null) {
                    binding.etPasswordLayout.apply {
                        startAnimation(shake)
                    }
                    binding.btnLogin.showSnack(getString(loginState.passwordError.toInt()), R.color.error_red)
                } else
                    binding.etPasswordLayout.error = null
            }
        }

    }

    private fun onClickListeners() {
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionMiHomeToRegisterFragment())
        }
        binding.btnLogin.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                authViewModel.signInDataChanged(binding.etEmail.text.toString(), binding.etPassword.text.toString())
                if (binding.btnLogin.tag == true) {
                    viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                        authViewModel.signInUser(
                                LoginRequest(
                                        binding.etEmail.text?.trim().toString(),
                                        binding.etPassword.text?.trim().toString()
                                )
                        ).collect { state ->
                            chooseState(state)
                        }
                    }
                }
            }
        }
    }

    private fun chooseState(state: Resource<out SignUpResponse>) {
        when (state) {
            is Resource.Loading -> {
                showLoading(true)
            }
            is Resource.Success -> {
                binding.btnLogin.showSnack(
                        state.data?.user?.email.toString(),
                        R.color.success_green
                )
                showLoading(false)
            }
            is Resource.Error -> {
                binding.btnLogin.showSnack(
                        state.message.toString(),
                        R.color.error_red
                )
                showLoading(false)
            }
        }
    }

    private fun showLoading(show: Boolean) {
        binding.prLogin.isVisible = show
        binding.btnLogin.apply {
            text = if (show) null else getString(R.string.sign_in_text)
            isEnabled = !show
            isClickable = !show
        }
    }

}