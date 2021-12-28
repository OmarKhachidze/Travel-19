package ge.bootcamp.travel19.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Path
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.MenuItem
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.viewModels
import androidx.annotation.MenuRes
import androidx.appcompat.widget.Toolbar
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.*
import dagger.hilt.android.AndroidEntryPoint
import ge.bootcamp.travel19.R
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis
import ge.bootcamp.travel19.databinding.ActivityMainBinding
import ge.bootcamp.travel19.ui.fragments.country_restrictions.CountryRestrictionsFragmentDirections
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener,
    Toolbar.OnMenuItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var splashScreen: SplashScreen

    private val currentNavigationFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            ?.childFragmentManager
            ?.fragments
            ?.first()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreen = installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        customizeSplashScreen(splashScreen)
        setUpBottomNavigation()
    }


    private fun setUpBottomNavigation() {
        binding.run {
            findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener(
                this@MainActivity
            )
        }

        binding.bottomAppBar.apply {
            setOnMenuItemClickListener(this@MainActivity)
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
            R.id.miHome -> {
                setBottomAppBarForHome(getBottomAppBarMenuForDestination(destination))
            }
            R.id.miChooseType -> {
                setBottomAppBarForHome(getBottomAppBarMenuForDestination(destination))
            }
            R.id.miSearchCountry -> {
                setBottomAppBarForSearchCountries()
            }

            R.id.miCountryRestrictions -> {
                setBottomAppBarForCountryRestrictions()
            }
        }
    }

    private fun hideBottomAppBar() {
        binding.run {
            bottomAppBar.performHide()
            bottomAppBar.animate().setListener(object : AnimatorListenerAdapter() {
                var isCanceled = false
                override fun onAnimationEnd(animation: Animator?) {
                    if (isCanceled) return
                    bottomAppBar.visibility = View.GONE
                }

                override fun onAnimationCancel(animation: Animator?) {
                    isCanceled = true
                }
            })
        }
    }

    private fun setBottomAppBarForSearchCountries() {
        hideBottomAppBar()
        binding.fab.hide()

    }

    private fun setBottomAppBarForCountryRestrictions() {
        hideBottomAppBar()
        binding.fab.show()
        binding.fab.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(
                CountryRestrictionsFragmentDirections.actionMiCountryRestrictionsToMiSearchCountry()
            )
        }
        binding.fab.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.ic_flag
            )
        )

    }

    private fun setBottomAppBarForHome(@MenuRes menuRes: Int) {
        binding.run {
            fab.setImageState(intArrayOf(-android.R.attr.state_activated), true)
            bottomAppBar.visibility = View.VISIBLE
            bottomAppBar.replaceMenu(menuRes)
            bottomAppBar.performShow()
            fab.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.ic_baseline_search_24
                )
            )
            fab.setOnClickListener {
                navigateToChooseType()
            }
            fab.show()
        }
    }

    private fun navigateToChooseType() {
        currentNavigationFragment?.apply {
            exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
                duration = 300L
            }
            reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false).apply {
                duration = 300L
            }
        }
        findNavController(R.id.nav_host_fragment).navigate(
            R.id.miChooseType, null, NavOptions.Builder()
                .setPopUpTo(
                    R.id.miHome,
                    false
                ).build()
        )
    }

    @MenuRes
    private fun getBottomAppBarMenuForDestination(destination: NavDestination? = null): Int {
        val dest = destination ?: findNavController(R.id.nav_host_fragment).currentDestination
        return when (dest?.id) {
            R.id.miHome -> R.menu.bottom_app_bar
//            R.id.emailFragment -> R.menu.bottom_app_bar_email_menu
            else -> R.menu.bottom_app_bar
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.miProfile -> navigateToProfile()
            R.id.miSettings -> navigateToSettings()
        }
        return true
    }

    private fun navigateToProfile() {
        currentNavigationFragment?.apply {
            exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
                duration = 300L
            }
            reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
                duration = 300L
            }
        }
        findNavController(R.id.nav_host_fragment).navigate(
            R.id.miProfile, null, NavOptions.Builder()
                .setPopUpTo(
                    R.id.miHome,
                    false
                ).build()
        )
    }

    private fun navigateToSettings() {
        currentNavigationFragment?.apply {
            exitTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true).apply {
                duration = 300L
            }
            reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false).apply {
                duration = 300L
            }
        }
        findNavController(R.id.nav_host_fragment).navigate(
            R.id.miSettings, null, NavOptions.Builder()
                .setPopUpTo(
                    R.id.miHome,
                    false
                ).build()
        )
    }

}