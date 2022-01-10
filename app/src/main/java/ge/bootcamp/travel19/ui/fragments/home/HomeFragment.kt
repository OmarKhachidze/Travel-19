package ge.bootcamp.travel19.ui.fragments.home

import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentHomeBinding
import ge.bootcamp.travel19.extensions.gone
import ge.bootcamp.travel19.extensions.showSnack
import ge.bootcamp.travel19.extensions.visible
import ge.bootcamp.travel19.model.logIn.LoginRequest
import ge.bootcamp.travel19.model.singup.SignUpResponse
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun start() {
        onClickListeners()
    }

    override fun observer() {
        homeViewModel.loginFormState.observe(viewLifecycleOwner) { loginState ->

            // disable login button unless both username / password is valid
            binding.btnLogin.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null)
                binding.etEmailLayout.error = getString(loginState.usernameError)
            else
                binding.etEmailLayout.error = null

            if (loginState.passwordError != null)
                binding.etPasswordLayout.error = getString(loginState.passwordError)
            else
                binding.etPasswordLayout.error = null
        }
    }

    private fun onClickListeners() {
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionMiHomeToRegisterFragment())
        }
        binding.btnLogin.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                homeViewModel.signInUser(
                    LoginRequest(
                        binding.etEmail.text?.trim().toString(),
                        binding.etPassword.text?.trim().toString()
                    )
                ).collect { state ->
                    chooseState(state)
                }
            }
        }

        binding.etEmail.doAfterTextChanged { editable ->
            homeViewModel.loginDataChanged(editable.toString(), binding.etPassword.text.toString())
        }

        binding.etPassword.doAfterTextChanged { editable ->
            homeViewModel.loginDataChanged(binding.etEmail.text.toString(), editable.toString())
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