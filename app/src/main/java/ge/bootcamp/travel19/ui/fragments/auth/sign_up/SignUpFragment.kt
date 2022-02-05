package ge.bootcamp.travel19.ui.fragments.auth.sign_up

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentSignUpBinding
import ge.bootcamp.travel19.domain.model.auth.Data
import ge.bootcamp.travel19.domain.model.auth.UserInfo
import ge.bootcamp.travel19.domain.states.Resource
import ge.bootcamp.travel19.extensions.collectWhenStarted
import ge.bootcamp.travel19.extensions.setData
import ge.bootcamp.travel19.extensions.showSnack
import ge.bootcamp.travel19.extensions.validateInput
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.ui.fragments.auth.AuthViewModel
import ge.bootcamp.travel19.utils.Constants.USER_BASICS_KEY
import ge.bootcamp.travel19.utils.Constants.USER_TOKEN_KEY

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(FragmentSignUpBinding::inflate) {

    private val authViewModel: AuthViewModel by activityViewModels()

    override fun observer() {

        authViewModel.authFormState.collectWhenStarted(viewLifecycleOwner) { signUpState ->
            binding.apply {
                btnSignUp.tag = signUpState.isDataValid
                etFullNameLayout.validateInput(signUpState.fullNameError)
                etRegisterEmailLayout.validateInput(signUpState.emailError)
                etRegisterPasswordLayout.validateInput(signUpState.passwordError)
            }
        }

        authViewModel.signUpUserState.collectWhenStarted(viewLifecycleOwner) { signUpState ->
            when (signUpState) {
                is Resource.Loading -> {
                    setLoading(true)
                }
                is Resource.Success -> {
                    setLoading(false)
                    signUpState.data?.user?.let {
                        authViewModel.saveTokenToDataStore(
                            stringSetPreferencesKey(USER_BASICS_KEY),
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

        authViewModel.nationalities.collectWhenStarted(this) { nationalityState ->
            when (nationalityState) {
                is Resource.Loading -> {
                    binding.acNationalities.apply {
                        setData(context.getString(R.string.fetching), null)
                    }
                }
                is Resource.Success -> {
                    nationalityState.data?.let { nationality ->
                        binding.acNationalities.apply {
                            setData(nationality.nacionalities[0], null, nationality.nacionalities)
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

        authViewModel.vaccines.collectWhenStarted(this) { vaccineState ->
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

    override fun start() {
        binding.tvSignIn.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment())
        }
        binding.btnSignUp.setOnClickListener {

            binding.apply {
                authViewModel.signUpDataChanged(
                    etFullName.text.toString(),
                    etRegisterEmail.text.toString(),
                    etRegisterPassword.text.toString()
                )

                if (btnSignUp.tag == true) {
                    authViewModel.signUpUser(
                        UserInfo(
                            etRegisterEmail.text.toString(),
                            etRegisterPassword.text.toString(),
                            Data(
                                acVaccines.text.toString(),
                                acNationalities.text.toString()
                            )
                        )
                    )
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
