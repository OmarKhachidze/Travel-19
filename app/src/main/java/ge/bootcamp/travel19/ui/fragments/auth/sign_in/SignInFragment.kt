package ge.bootcamp.travel19.ui.fragments.auth.sign_in

import android.util.Log.d
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentSignInBinding
import ge.bootcamp.travel19.domain.model.auth.AuthResponse
import ge.bootcamp.travel19.domain.model.auth.UserInfo
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.ui.fragments.auth.AuthViewModel
import ge.bootcamp.travel19.utils.Constants.USER_BASICS_KEY
import ge.bootcamp.travel19.utils.Constants.USER_TOKEN_KEY
import ge.bootcamp.travel19.domain.states.Resource
import ge.bootcamp.travel19.extensions.*

class SignInFragment : BaseFragment<FragmentSignInBinding>(FragmentSignInBinding::inflate) {

    private val authViewModel: AuthViewModel by activityViewModels()

    override fun start() {
        onClickListeners()
    }

    override fun observer() {

        collectLatestLifecycleFlow(authViewModel.signInUserState) {
            chooseState(it)
        }

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
            binding.apply {
                authViewModel.signInDataChanged(
                    etEmail.text.toString().trim(),
                    etPassword.text.toString().trim()
                )
                if (btnLogin.tag == true) {
                    authViewModel.signInUser(
                        UserInfo(
                            etEmail.text.toString().trim(),
                            etPassword.text.toString().trim()
                        )
                    )
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
                state.data?.user?.let {
                    authViewModel.saveTokenToDataStore(
                        stringSetPreferencesKey(USER_BASICS_KEY),
                        setOf(it.data?.nationalities!!, it.data.vaccine!!)
                    )
                }
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
            btnLogin.setData(R.string.sign_in, prLogin, visibility)
        }
    }

}