package ge.bootcamp.travel19.model

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
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

    private val item: MutableList<String> = mutableListOf()
    private val nationalities: MutableList<String> = mutableListOf()
    private lateinit var userInfo: UserInfo

    override fun start() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            fetchVaccine()
            fetchNationalities()
        }
        listeners()
        val items = listOf("Option 1", "Option 2", "Option 3", "Option 4")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, item)
        (binding.tiVaccines.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        val nationalitiesAdapter = ArrayAdapter(requireContext(), R.layout.list_item, nationalities)
        (binding.tiNationality.editText as? AutoCompleteTextView)?.setAdapter(nationalitiesAdapter)
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
        binding.eFirstName.text
        binding.eLastName.text
        binding.eEmail.text
        binding.ePassword.text
        binding.eNationalities.text
        binding.eVaccines.text

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

}