package ge.bootcamp.travel19.model

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.homework17.ui.ui.login.LoggedInUserView
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentRegisterBinding
import ge.bootcamp.travel19.model.logIn.Data
import ge.bootcamp.travel19.model.singup.UserInfo
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.ui.fragments.register.LogInViewModel
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.collect

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: LogInViewModel by activityViewModels()

    private val item: MutableList<String> = mutableListOf("Not vaccinated")
    private val nationalities: MutableList<String> = mutableListOf()
    private lateinit var userInfo: UserInfo

    override fun start() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            fetchVaccine()
            fetchNationalities()
        }
        listeners()
        val vaccinesAdapter = ArrayAdapter(requireContext(), R.layout.list_item, item)
        (binding.tiVaccines.editText as? AutoCompleteTextView)?.setAdapter(vaccinesAdapter)

        val nationalitiesAdapter = ArrayAdapter(requireContext(), R.layout.list_item, nationalities)
        (binding.tiNationality.editText as? AutoCompleteTextView)?.setAdapter(nationalitiesAdapter)



        val usernameEditText = binding.eEmail
        val passwordEditText = binding.ePassword
        val loginButton = binding.signUp
        val loadingProgressBar = binding.loading
/*
        loginViewModel.loginFormState.observe(viewLifecycleOwner,
            Observer { loginFormState ->
                if (loginFormState == null) {
                    return@Observer
                }
                loginButton.isEnabled = loginFormState.isDataValid
                loginFormState.usernameError?.let {
                    usernameEditText.error = getString(it)
                }
                loginFormState.passwordError?.let {
                    passwordEditText.error = getString(it)
                }
            })

 */


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.loginFormState1.collect { loginFormState ->
//                if (loginFormState.isDataValid == false) {
                //                   return@collect
                //               }
                loginButton.isEnabled = loginFormState.isDataValid
                loginFormState.usernameError?.let {
                    usernameEditText.error = getString(it)
                }
                loginFormState.passwordError?.let {
                    passwordEditText.error = getString(it)
                }
            }
        }

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                viewModel.loginDataChanged(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
        }
        usernameEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.login(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
            false
        }

        loginButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            viewModel.login(
                usernameEditText.text.toString(),
                passwordEditText.text.toString()
            )

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.loginResult1.collect { loginResult ->
                    loadingProgressBar.visibility = View.GONE
                    loginResult.error?.let {
                        showLoginFailed(it)
                    }
                    loginResult.success?.let {
                        Log.i("result_succ", "aq modis")
                        updateUiWithUser(it)
                    }
                }
            }
        }
    }

    private fun listeners() {
        binding.signUp.setOnClickListener {
            saveUserData()
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                postUserInfo()
            }
            Toast.makeText(requireContext(), "$userInfo", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveUserData() {
        userInfo = UserInfo(binding.eEmail.text.toString(), binding.ePassword.text.toString(),
                            Data(binding.eVaccines.text.toString(), binding.eNationalities.text.toString())
        )

    }

    private suspend fun fetchVaccine() {
        Log.i("fetch", "vaccines")
        lifecycleScope.launchWhenStarted {
            viewModel.data().collect { state ->
                when (state) {
                    is Resource.Success -> {
                        Log.d("state", "Success")
//                        handleUiVisibility(false)
//                        userAdapter.submitList(state.data)
                        Log.d("data", state.data!!.toString())
                        item.addAll(state.data.vaccines)
                    }

                    is Resource.Error -> {
                        Log.d("state", "Error")
                        //                       state.message?.let { onError(it) }
                    }

                    is Resource.Loading -> {
                        Log.d("state", "Loading")
//                        handleUiVisibility(true)
                    }
                }

            }
        }

    }

    private suspend fun fetchNationalities() {
        Log.i("fetch", "nationality")
        lifecycleScope.launchWhenStarted {
            viewModel.nationalities().collect { state ->
                when (state) {
                    is Resource.Success -> {
                        Log.d("state", "Success")
//                        handleUiVisibility(false)
//                        userAdapter.submitList(state.data)
                        Log.d("data", state.data!!.toString())
                        nationalities.addAll(state.data.nacionalities)
                    }

                    is Resource.Error -> {
                        Log.d("state", "Error")
                        //                       state.message?.let { onError(it) }
                    }

                    is Resource.Loading -> {
                        Log.d("state", "Loading")
//                        handleUiVisibility(true)
                    }
                }

            }
        }

    }

    private suspend fun postUserInfo() {
        Log.i("fetch", "nationality")
        lifecycleScope.launchWhenStarted {
            viewModel.postUser(userInfo).collect { state ->
                when (state) {
                    is Resource.Success -> {
                        Log.d("state", "Success")
//                        handleUiVisibility(false)
//                        userAdapter.submitList(state.data)
                        Log.d("data", state.data!!.toString())

                    }

                    is Resource.Error -> {
                        Log.d("state", "Error")
                        //                       state.message?.let { onError(it) }
                    }

                    is Resource.Loading -> {
                        Log.d("state", "Loading")
//                        handleUiVisibility(true)
                    }
                }

            }
        }

    }




private fun updateUiWithUser(model: LoggedInUserView) {
    Log.i("result_upd", "aq modis")
    val welcome = getString(R.string.welcome) + model.displayName
    // TODO : initiate successful logged in experience
    val appContext = context?.applicationContext ?: return
    Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()
}

private fun showLoginFailed(@StringRes errorString: Int) {
    val appContext = context?.applicationContext ?: return
    Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
}

}