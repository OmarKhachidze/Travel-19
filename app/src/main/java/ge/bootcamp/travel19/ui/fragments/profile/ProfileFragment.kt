package ge.bootcamp.travel19.ui.fragments.profile

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ge.bootcamp.travel19.databinding.FragmentProfileBinding
import ge.bootcamp.travel19.domain.states.Resource
import ge.bootcamp.travel19.extensions.gone
import ge.bootcamp.travel19.extensions.hideAllView
import ge.bootcamp.travel19.extensions.visible
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.utils.Constants.USER_TOKEN_KEY
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun start() {
        binding.searchToolbar.setNavigationOnClickListener { findNavController().navigateUp() }
    }

    override fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            val userToken = profileViewModel.getUserToken(stringPreferencesKey(USER_TOKEN_KEY))
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                if (userToken != null) {
                    profileViewModel.getUserInfo(userToken).collectLatest { userState ->
                        when (userState) {
                            is Resource.Loading -> {
                                binding.root.hideAllView(true)
                                binding.errorText.gone()
                                binding.prProfile.visible()
                            }
                            is Resource.Success -> {
                                binding.root.hideAllView(false)
                                binding.prProfile.gone()
                                binding.apply {
                                    tvProfileFullName.text = userState.data?.user?.email
                                    tvNationalityProfile.text = userState.data?.user?.data?.vaccine
                                    tvVaccineProfile.text =
                                        userState.data?.user?.data?.nationalities
                                }
                            }
                            is Resource.Error -> {
                                binding.root.hideAllView(true)
                                binding.errorText.visible()
                                binding.errorText.text = userState.message
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }
}