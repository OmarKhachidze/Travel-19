package ge.bootcamp.travel19.ui.fragments.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentLogInBinding
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.collect


class LogInFragment: BaseFragment<FragmentLogInBinding>(FragmentLogInBinding::inflate) {

    private val viewModel: LogInViewModel by activityViewModels()

    override fun start() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            fetchVaccine()
        }
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