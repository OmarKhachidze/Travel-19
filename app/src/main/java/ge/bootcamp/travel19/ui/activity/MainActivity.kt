package ge.bootcamp.travel19.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Path
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.navigation.*
import dagger.hilt.android.AndroidEntryPoint
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.ActivityMainBinding
import ge.bootcamp.travel19.ui.fragments.country_restrictions.CountryRestrictionsFragmentDirections

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
            R.id.signInFragment -> {
                setBottomAppBarForHome(getBottomAppBarMenuForDestination(destination))
            }
            R.id.chooseTypeFragment -> {
                setBottomAppBarForChooseType()
            }
            R.id.SearchCountryFragment -> {
                hideFabAndAppBar()
            }
            R.id.signUpFragment -> {
                hideFabAndAppBar()
            }
            R.id.CountryRestrictionsFragment -> {
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

    private fun hideFabAndAppBar() {
        hideBottomAppBar()
        binding.fab.hide()
    }

    private fun setBottomAppBarForCountryRestrictions() {
        binding.fab.show()
        binding.fab.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(
                CountryRestrictionsFragmentDirections.actionCountryRestrictionsFragmentToSearchCountryFragment()
            )
        }
        binding.fab.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.ic_outlined_flag
            )
        )
    }

    private fun setBottomAppBarForChooseType() {
        binding.fab.show()
        binding.fab.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigateUp()
        }
        binding.fab.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.ic_arrow_back
            )
        )

    }

    private fun setBottomAppBarForHome(@MenuRes menuRes: Int) {
        hideBottomAppBar()
        binding.run {
            fab.setImageState(intArrayOf(-android.R.attr.state_activated), true)
//            bottomAppBar.visibility = View.VISIBLE
//            bottomAppBar.replaceMenu(menuRes)
//            bottomAppBar.performShow()
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
//        currentNavigationFragment?.apply {
//            exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
//                duration = 300L
//            }
//            reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false).apply {
//                duration = 300L
//            }
//        }
        findNavController(R.id.nav_host_fragment).navigate(
            R.id.chooseTypeFragment, null, NavOptions.Builder()
                .setPopUpTo(
                    R.id.signInFragment,
                    false
                ).build()
        )
    }

    @MenuRes
    private fun getBottomAppBarMenuForDestination(destination: NavDestination? = null): Int {
        val dest = destination ?: findNavController(R.id.nav_host_fragment).currentDestination
        return when (dest?.id) {
            R.id.signInFragment -> R.menu.bottom_app_bar
//            R.id.emailFragment -> R.menu.bottom_app_bar_email_menu
            else -> R.menu.bottom_app_bar
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.profileFragment -> navigateToProfile()
            R.id.settingsFragment -> navigateToSettings()
        }
        return true
    }

    private fun navigateToProfile() {
//        currentNavigationFragment?.apply {
//            exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
//                duration = 300L
//            }
//            reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
//                duration = 300L
//            }
//        }
        findNavController(R.id.nav_host_fragment).navigate(
            R.id.profileFragment, null, NavOptions.Builder()
                .setPopUpTo(
                    R.id.signInFragment,
                    false
                ).build()
        )
    }

    private fun navigateToSettings() {
//        currentNavigationFragment?.apply {
//            exitTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true).apply {
//                duration = 300L
//            }
//            reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false).apply {
//                duration = 300L
//            }
//        }
        findNavController(R.id.nav_host_fragment).navigate(
            R.id.settingsFragment, null, NavOptions.Builder()
                .setPopUpTo(
                    R.id.signInFragment,
                    false
                ).build()
        )
    }

}