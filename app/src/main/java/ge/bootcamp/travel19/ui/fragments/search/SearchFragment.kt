package ge.bootcamp.travel19.ui.fragments.search

import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentSearchBinding
import ge.bootcamp.travel19.ui.activity.MainActivity
import ge.bootcamp.travel19.ui.fragments.BaseFragment
import ge.bootcamp.travel19.ui.fragments.countries_restrictions.CountriesRestrictionFragment
import ge.bootcamp.travel19.ui.fragments.search.adapter.CountriesAdapter
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    override fun start() {
        Log.d("TAG", "Not yet implemented")
        binding.cvCountries.setOnClickListener {
            findNavController().navigate(R.id.miCountriesRestriction)
            (activity as MainActivity).hideBottomNavigationView()
        }
    }


}