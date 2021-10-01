package com.example.basekotlinkoin.ui.splash

import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.basekotlinkoin.R
import com.example.basekotlinkoin.base.BaseFragment
import com.example.basekotlinkoin.databinding.FragmentSplashBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(false) {

    private val mainNavController: NavController? by lazy { activity?.findNavController(R.id.nav_host_fragment_main) }
    private val splashViewModel: SplashViewModel by viewModel()

    override fun setLayout(): Int = R.layout.fragment_splash

    override fun getViewModels() = splashViewModel

    override fun onReadyAction() {

        launch {
            if (splashViewModel.displaySplashAsync().await()) {
//                val direction = SplashFragmentDirections.actionSplashFragmentToOnBoardingFragment()
//                mainNavController?.navigate(direction)
            } else {
//                val direction = SplashFragmentDirections.actionSplashFragmentToHomeFragment()
//                mainNavController?.navigate(direction)
            }
        }
    }
}