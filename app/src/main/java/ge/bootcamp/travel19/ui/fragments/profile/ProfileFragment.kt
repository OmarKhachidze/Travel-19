package ge.bootcamp.travel19.ui.fragments.profile

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import ge.bootcamp.travel19.databinding.FragmentProfileBinding
import ge.bootcamp.travel19.ui.activity.MainViewModel
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.collect

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: MainViewModel by activityViewModels()

    override fun start() {
        Log.d("TAG", "Not yet implemented")

        lifecycleScope.launchWhenStarted {
            viewModel.data("GE").collect { state ->
                when (state) {
                    is Resource.Success -> {
                        Log.d("state", "Success")
//                        handleUiVisibility(false)
//                        userAdapter.submitList(state.data)
                    }

                    is Resource.Error -> {
                        Log.d("state", "Error")
//                        state.message?.let { onError(it) }
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