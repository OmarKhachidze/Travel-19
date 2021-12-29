package ge.bootcamp.travel19.ui.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Path
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ge.bootcamp.travel19.R
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ge.bootcamp.travel19.databinding.ActivityMainBinding
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var splashScreen: SplashScreen
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreen = installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        customizeSplashScreen(splashScreen)
        setUpBottomNav()
    }

    private fun setUpBottomNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        val navView: BottomNavigationView = binding.bottomNavigationView
        navView.setupWithNavController(navController)
        binding.bottomNavigationView.background = null
        binding.fab.setOnClickListener {
            navController.navigate(R.id.miSearch)
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
                resources.getInteger(ge.bootcamp.travel19.R.integer.splash_exit_total_duration)
                    .toLong()

            playTogether(scaleOut)
            doOnEnd {
                onExit()
            }
            start()
        }
    }


     fun hideBottomNavigationView() {
        binding.bottomAppBar.clearAnimation()
        binding.bottomAppBar.animate().translationY(binding.bottomAppBar.height.toFloat()).duration = 300
    }

    fun showBottomNavigationView() {
        binding.bottomAppBar.clearAnimation()
        binding.bottomAppBar.animate().translationY(0F).duration = 300

    }

}