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
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.ActivityMainBinding
import ge.bootcamp.travel19.utils.Resource
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var splashScreen: SplashScreen

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreen = installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: MainViewModel by viewModels()
        customizeSplashScreen(splashScreen, viewModel)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_test)
        navView.setupWithNavController(navController)

        lifecycleScope.launchWhenStarted {
            viewModel.data("GE").collect { state ->
                when (state) {
                    is Resource.Success -> {
                        d("state", "Success")
//                        handleUiVisibility(false)
//                        userAdapter.submitList(state.data)
                    }

                    is Resource.Error -> {
                        d("state", "Error")
//                        state.message?.let { onError(it) }
                    }

                    is Resource.Loading -> {
                        d("state", "Loading")
//                        handleUiVisibility(true)
                    }
                }

            }
        }
    }

    private fun customizeSplashScreen(splashScreen: SplashScreen, viewModel: MainViewModel) {
        keepSplashScreenLonger(splashScreen, viewModel)
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

    private fun keepSplashScreenLonger(splashScreen: SplashScreen, viewModel: MainViewModel) {
        splashScreen.setKeepVisibleCondition { !viewModel.isDataReady() }
    }

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
            duration = resources.getInteger(R.integer.splash_exit_total_duration).toLong()

            playTogether(scaleOut)
            doOnEnd {
                onExit()
            }
            start()
        }
    }

}