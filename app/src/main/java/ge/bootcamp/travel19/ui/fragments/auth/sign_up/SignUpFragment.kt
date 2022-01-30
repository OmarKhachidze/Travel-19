package ge.bootcamp.travel19.ui.fragments.auth.sign_up

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentSignUpBinding
import ge.bootcamp.travel19.extensions.collectLatestLifecycleFlow
import ge.bootcamp.travel19.extensions.setData
import ge.bootcamp.travel19.extensions.showSnack
import ge.bootcamp.travel19.extensions.validateInput
import ge.bootcamp.travel19.model.auth.Data
import ge.bootcamp.travel19.model.auth.UserInfo
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.ui.fragments.auth.AuthViewModel
import ge.bootcamp.travel19.utils.Constants
import ge.bootcamp.travel19.utils.Constants.USER_TOKEN_KEY
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    private val authViewModel: AuthViewModel by activityViewModels()

    override fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    authViewModel.nationalities.collect { nationalityState ->
                        when (nationalityState) {
                            is Resource.Loading -> {
                                binding.acNationalities.apply {
                                    setData(context.getString(R.string.fetching), null)
                                }
                            }
                            is Resource.Success -> {
                                nationalityState.data?.let { nationalities ->
                                    binding.acNationalities.apply {
                                        setData(
                                            nationalities.nacionalities[0],
                                            null,
                                            nationalities.nacionalities
                                        )
                                    }
                                }
                            }
                            is Resource.Error -> {
                                binding.acNationalities.apply {
                                    setData(null, nationalityState.message)
                                }
                            }
                        }
                    }
                }

                launch {
                    authViewModel.vaccines.collect { vaccineState ->
                        when (vaccineState) {
                            is Resource.Loading -> {
                                binding.acVaccines.apply {
                                    setData(context.getString(R.string.fetching), null)
                                }
                            }
                            is Resource.Success -> {
                                vaccineState.data?.let { vaccines ->
                                    binding.acVaccines.apply {
                                        setData(vaccines.vaccines[0], null, vaccines.vaccines)
                                    }
                                }
                            }
                            is Resource.Error -> {
                                binding.acVaccines.apply {
                                    setData(null, vaccineState.message)
                                }
                            }
                        }
                    }
                }
                launch {
                    authViewModel.authFormState.collect { signUpState ->
                        binding.apply {
                            btnSignUp.tag = signUpState.isDataValid
                            etFullNameLayout.validateInput(signUpState.fullNameError)
                            etRegisterEmailLayout.validateInput(signUpState.emailError)
                            etRegisterPasswordLayout.validateInput(signUpState.passwordError)
                        }
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
            authViewModel.signUpDataChanged(
                binding.etFullName.text.toString(),
                binding.etRegisterEmail.text.toString(),
                binding.etRegisterPassword.text.toString()
            )
            if (binding.btnSignUp.tag == true) {
                collectLatestLifecycleFlow(
                    authViewModel.signUpUser(
                        UserInfo(
                            binding.etRegisterEmail.text.toString(),
                            binding.etRegisterPassword.text.toString(),
                            Data(
                                binding.acVaccines.text.toString(),
                                binding.acNationalities.text.toString()
                            )
                        )
                    )
                ) { signUpState ->
                    when (signUpState) {
                        is Resource.Loading -> {
                            setLoading(true)
                        }
                        is Resource.Success -> {
                            setLoading(false)
                            signUpState.data?.user?.let {
                                authViewModel.saveTokenToDataStore(
                                    stringSetPreferencesKey(Constants.USER_BASICS_KEY),
                                    setOf(it.data?.nationalities!!, it.data.vaccine!!)
                                )
                            }
                            signUpState.data?.token?.let {
                                authViewModel.saveTokenToDataStore(
                                    stringPreferencesKey(USER_TOKEN_KEY),
                                    it
                                )
                            }.apply {
                                findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToChooseTypeFragment())
                            }
                        }
                        is Resource.Error -> {
                            setLoading(false)
                            binding.btnSignUp.showSnack(
                                signUpState.message.toString(),
                                R.color.error_red
                            )
                        }
                    }
                }
            }
        }
    }

    private fun setLoading(visibility: Boolean) {
        binding.apply {
            btnSignUp.setData(R.string.sign_up, prSignUp, visibility)
        }
    }

}
