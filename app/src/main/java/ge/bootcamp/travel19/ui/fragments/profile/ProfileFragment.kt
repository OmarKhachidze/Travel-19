package ge.bootcamp.travel19.ui.fragments.profile

import android.util.Log
import android.widget.ArrayAdapter
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentProfileBinding
import ge.bootcamp.travel19.ui.activity.MainViewModel
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.ui.fragments.auth.AuthViewModel
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.collect

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val authViewModel: AuthViewModel by activityViewModels()

    override fun observer() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            val userToken = authViewModel.getUserToken(stringPreferencesKey("userToken"))
            if (userToken != null) {
                authViewModel.getUserInfo(userToken).collect { userState ->
                    when (userState) {
                        is Resource.Loading -> {
                            Log.d("TAG", "LOADING PROFILE")

                        }
                        is Resource.Success -> {
                            Log.d("TAG", "SUCCESS PROFILE")

                            binding.apply {
                                tvMainEmail.text = userState.data?.user?.email
                                tvProfileFullName.text = userState.data?.user?.email
                                tvNationalityProfile.text = userState.data?.user?.data?.vaccine
                                tvVaccineProfile.text = userState.data?.user?.data?.nationalities
                            }
                        }
                        is Resource.Error -> {
                            Log.d("TAG", "ERROR PROFILE")

                        }
                    }
                }

            }
        }
    }

    override fun start() {
        Log.d("TAG", "Not yet implemented")
        binding.searchToolbar.setNavigationOnClickListener { findNavController().navigateUp() }
    }

}