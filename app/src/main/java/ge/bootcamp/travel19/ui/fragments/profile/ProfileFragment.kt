package ge.bootcamp.travel19.ui.fragments.profile

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import ge.bootcamp.travel19.databinding.FragmentProfileBinding
import ge.bootcamp.travel19.ui.activity.MainViewModel
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.collect

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    override fun start() {
        Log.d("TAG", "Not yet implemented")
        binding.searchToolbar.setNavigationOnClickListener { findNavController().navigateUp() }

    }

}