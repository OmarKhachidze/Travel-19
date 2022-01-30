package ge.bootcamp.travel19.ui.fragments.auth.sign_in

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentSignInBinding
import ge.bootcamp.travel19.extensions.collectLatestLifecycleFlow
import ge.bootcamp.travel19.extensions.setLoading
import ge.bootcamp.travel19.extensions.showSnack
import ge.bootcamp.travel19.extensions.validateInput
import ge.bootcamp.travel19.model.auth.AuthResponse
import ge.bootcamp.travel19.model.auth.UserInfo
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.ui.fragments.auth.AuthViewModel
import ge.bootcamp.travel19.utils.Constants.USER_TOKEN_KEY
import ge.bootcamp.travel19.utils.Resource

class SignInFragment : BaseFragment<FragmentSignInBinding>(FragmentSignInBinding::inflate) {

    private val authViewModel: AuthViewModel by activityViewModels()

    override fun start() {
        onClickListeners()
    }

    override fun observer() {
        collectLatestLifecycleFlow(authViewModel.authFormState) { loginState ->
            binding.apply {
                btnLogin.tag = loginState.isDataValid
                etEmailLayout.validateInput(loginState.emailError)
                etPasswordLayout.validateInput(loginState.passwordError)
            }
        }
    }

    private fun onClickListeners() {
        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
        }

        binding.btnLogin.setOnClickListener {
            authViewModel.signInDataChanged(
                binding.etEmail.text.toString().trim(),
                binding.etPassword.text.toString().trim()
            )
            if (binding.btnLogin.tag == true) {
                collectLatestLifecycleFlow(
                    authViewModel.signInUser(
                        UserInfo(
                            binding.etEmail.text.toString().trim(),
                            binding.etPassword.text.toString().trim()
                        )
                    )
                ) {
                    chooseState(it)
                }
            }
        }
    }

    private suspend fun chooseState(state: Resource<out AuthResponse>) {
        when (state) {
            is Resource.Loading -> {
                setLoading(true)
            }
            is Resource.Success -> {
                setLoading(false)
                state.data?.token?.let {
                    authViewModel.saveTokenToDataStore(
                        stringPreferencesKey(USER_TOKEN_KEY), it
                    )
                }.apply {
                    findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToChooseTypeFragment())
                }
            }
            is Resource.Error -> {
                setLoading(false)
                binding.btnLogin.showSnack(
                    state.message.toString(),
                    R.color.error_red
                )
            }
        }
    }

    private fun setLoading(visibility: Boolean) {
        binding.apply {
            btnLogin.setLoading(R.string.sign_in, prLogin, visibility)
        }
    }

}