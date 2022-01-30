package ge.bootcamp.travel19.ui.fragments.profile

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import ge.bootcamp.travel19.databinding.FragmentProfileBinding
import ge.bootcamp.travel19.extensions.gone
import ge.bootcamp.travel19.extensions.hideAllView
import ge.bootcamp.travel19.extensions.visible
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.ui.fragments.auth.AuthViewModel
import ge.bootcamp.travel19.utils.Constants.USER_TOKEN_KEY
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val authViewModel: AuthViewModel by activityViewModels()

    override fun start() {
        binding.searchToolbar.setNavigationOnClickListener { findNavController().navigateUp() }
    }

    override fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            val userToken = authViewModel.getUserToken(stringPreferencesKey(USER_TOKEN_KEY))
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                if (userToken != null) {
                    authViewModel.getUserInfo(userToken).collect { userState ->
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
                            }
                        }
                    }
                }
            }
        }
    }
}