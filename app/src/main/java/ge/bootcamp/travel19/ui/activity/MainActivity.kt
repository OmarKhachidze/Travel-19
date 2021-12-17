package ge.bootcamp.travel19.ui.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.graphics.Path
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import ge.bootcamp.travel19.R
import ge.bootcamp.travel19.databinding.ActivityMainBinding


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var splashScreen: SplashScreen
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreen = installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController

        val navView: BottomNavigationView = binding.bottomNavigationView

        //val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
        //setupWithNavController(navController)


        customizeSplashScreen(splashScreen)
        findViewById<BottomNavigationView>(ge.bootcamp.travel19.R.id.bottomNavigationView).background = null

    }

    private fun customizeSplashScreen(splashScreen: SplashScreen) {
         keepSplashScreenLonger(splashScreen)
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

    private fun keepSplashScreenLonger(splashScreen: SplashScreen) {
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
            duration = resources.getInteger(ge.bootcamp.travel19.R.integer.splash_exit_total_duration).toLong()

            playTogether(scaleOut)
            doOnEnd {
                onExit()
            }
            start()
        }
    }

    private fun navigation() {
        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottomAppBar)
        //set bottom bar to Action bar as it is similar like Toolbar
        setSupportActionBar(bottomAppBar)
        bottomAppBar.setNavigationOnClickListener {
            it.findNavController().navigate(R.id.miSearch)
        }
        findViewById<BottomAppBar>(R.id.bottomAppBar).setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                ge.bootcamp.travel19.R.id.miSearch -> {
                    // Handle search icon press
                    findNavController(R.id.miHome).navigate(R.id.miSearch)
                    true
                }
                R.id.miHome -> {
                    // Handle more item (inside overflow menu) press
                    //findNavController(R.id.bottomNavigationView).navigate(ge.bootcamp.travel19.R.id.homeFragment)
                    true
                }
                else -> false
            }
        }
    }

}