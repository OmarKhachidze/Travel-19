package ge.bootcamp.travel19.ui.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Path
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import androidx.navigation.*
import dagger.hilt.android.AndroidEntryPoint
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.ActivityMainBinding
import ge.bootcamp.travel19.extensions.setDrawable
import ge.bootcamp.travel19.ui.fragments.auth.sign_in.SignInFragmentDirections
import ge.bootcamp.travel19.ui.fragments.choose_type.ChooseTypeFragmentDirections
import ge.bootcamp.travel19.ui.fragments.country_restrictions.CountryRestrictionsFragmentDirections
import ge.bootcamp.travel19.ui.fragments.profile.ProfileFragmentDirections
import ge.bootcamp.travel19.utils.Keys

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var splashScreen: SplashScreen
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreen = installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        customizeSplashScreen(splashScreen)
        setUpBottomNavigation()
        changeNavigationStartDestination()
    }

    private fun changeNavigationStartDestination() {
        val navController = findNavController(R.id.nav_host_fragment)
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        lifecycleScope.launchWhenStarted {
            if (mainViewModel.getUserToken(stringPreferencesKey("userToken")) != null) {
                navGraph.setStartDestination(R.id.chooseTypeFragment)
            } else
                navGraph.setStartDestination(R.id.signInFragment)
            navController.graph = navGraph

        }

    }

    private fun setUpBottomNavigation() {
        binding.run {
            findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener(
                this@MainActivity
            )
        }

        binding.fab.apply {
            setShowMotionSpecResource(R.animator.fab_show)
            setHideMotionSpecResource(R.animator.fab_hide)

        }
    }

    private fun customizeSplashScreen(splashScreen: SplashScreen) {
//        keepSplashScreenLonger(splashScreen, viewModel)
        customizeSplashScreenExit(splashScreen)
    }

    private fun customizeSplashScreenExit(splashScreen: SplashScreen) {
        splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->

            showSplashExitAnimator(splashScreenViewProvider.view) {
                splashScreenViewProvider.remove()
            }

            showSplashIconExitAnimator(splashScreenViewProvider.iconView) {
                splashScreenViewProvider.remove()
            }
        }
    }

//    private fun keepSplashScreenLonger(splashScreen: SplashScreen, viewModel: MainViewModel) {
//        splashScreen.setKeepVisibleCondition { !viewModel.isDataReady() }
//    }

    private fun showSplashExitAnimator(splashScreenView: View, onExit: () -> Unit = {}) {

        val scaleOut = ObjectAnimator.ofFloat(
            splashScreenView,
            View.SCALE_X,
            View.SCALE_Y,
            Path().apply {
                moveTo(1.0f, 1.0f)
                lineTo(0f, 0f)
            }
        )
        AnimatorSet().run {
            duration = resources.getInteger(R.integer.splash_exit_total_duration).toLong()
            interpolator = AnticipateInterpolator()
            playTogether(scaleOut)

            doOnEnd {
                onExit()
            }
            start()
        }
    }


    private fun showSplashIconExitAnimator(iconView: View, onExit: () -> Unit = {}) {

        val scaleOut = ObjectAnimator.ofFloat(
            iconView,
            View.SCALE_X,
            View.SCALE_Y,
            Path().apply {
                moveTo(1.0f, 1.0f)
                lineTo(0.3f, 0.3f)
            }
        )

        AnimatorSet().run {
            interpolator = AnticipateInterpolator()
            duration =
                resources.getInteger(R.integer.splash_exit_total_duration)
                    .toLong()

            playTogether(scaleOut)
            doOnEnd {
                onExit()
            }
            start()
        }
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.signInFragment -> {
                setBottomAppBarForHome()
            }
            R.id.chooseTypeFragment -> {
                setBottomAppBarForChooseType()
            }
            R.id.SearchCountryFragment -> {
                binding.fab.hide()
            }
            R.id.signUpFragment -> {
                binding.fab.hide()
            }
            R.id.CountryRestrictionsFragment -> {
                setBottomAppBarForCountryRestrictions()
            }
            R.id.profileFragment -> {
                setBottomAppBarForProfileFragment()
            }
            R.id.chooseAirportFragment -> {
                setBottomAppBarForChooseAirportFragment()
            }
        }
    }

    private fun setBottomAppBarForChooseAirportFragment() {
        setUpFab(
            R.drawable.ic_arrow_back
        )
    }

    private fun setBottomAppBarForProfileFragment() {
        binding.fab.show()
        binding.fab.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                mainViewModel.removeUserToken(stringPreferencesKey("userToken"))
                findNavController(R.id.nav_host_fragment).navigate(
                    ProfileFragmentDirections.actionProfileFragmentToSignInFragment()
                )
            }
        }
        binding.fab.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.ic_sign_out
            )
        )
    }

    private fun setBottomAppBarForCountryRestrictions() {
        setUpFab(
            R.drawable.ic_outlined_flag,
            CountryRestrictionsFragmentDirections.actionCountryRestrictionsFragmentToSearchCountryFragment()
        )
    }

    private fun setBottomAppBarForChooseType() {
        lifecycleScope.launchWhenStarted {
            if (mainViewModel.getUserToken(stringPreferencesKey("userToken")) != null) {
                setUpFab(
                    R.drawable.ic_profile,
                    ChooseTypeFragmentDirections.actionChooseTypeFragmentToProfileFragment()
                )
            } else {
                setUpFab(
                    R.drawable.ic_arrow_back
                )
            }
        }
    }

    private fun setBottomAppBarForHome() {
        setUpFab(
            R.drawable.ic_baseline_search_24,
            SignInFragmentDirections.actionSignInFragmentToChooseTypeFragment2()
        )
    }


    private fun setUpFab(icon: Int, direction: NavDirections? = null) {
        binding.run {
            fab.setDrawable(icon)
            fab.setOnClickListener {
                if (direction != null)
                    findNavController(R.id.nav_host_fragment).navigate(direction)
                else
                    findNavController(R.id.nav_host_fragment).navigateUp()
            }
            fab.show()
        }
    }

}