package ge.bootcamp.travel19.ui.fragments.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import dagger.hilt.EntryPoint
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.FragmentFavoritesBinding
import ge.bootcamp.travel19.ui.fragments.BaseFragment


class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

  //  private val favViewModel: FavoritesViewModel by activityViewModels()

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun observer() {

    }

    suspend fun checkToken() {

    }

}