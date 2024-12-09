package com.security.securityapplication.pages.components.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.security.securityapplication.R
import com.security.securityapplication.SPLASH_DELAY
import com.security.securityapplication.data.Resource
import com.security.securityapplication.data.dto.login.LoginResponse
import com.security.securityapplication.data.dto.profile.UserModel
import com.security.securityapplication.databinding.ActivitySplashBinding
import com.security.securityapplication.pages.base.BaseActivity
import com.security.securityapplication.pages.components.home.HomeActivity
import com.security.securityapplication.pages.components.login.LoginActivity
import com.security.securityapplication.utils.observe
import com.security.securityapplication.utils.toGone
import com.security.securityapplication.utils.toVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val splashViewModel: SplashVM by viewModels()
    override fun observeViewModel() {
        observe(splashViewModel.splashLiveData, ::handleSplashResult)
    }

    override fun initViewBinding() {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun handleSplashResult(status: Resource<UserModel>) {
        when (status) {
            is Resource.Success -> status.data?.let {
                Log.d("SplashActivity", "handleSplashResult: ${status.data}")
                navigateToMainScreen()
            }
            is Resource.Error -> {
                Log.d("SplashActivity", "handleSplashResult: ${status.errorCode}")
                navigateToLoginScreen()
            }
            is Resource.Loading -> {
                Log.d("SplashActivity", "handleSplashResult: else")
            }
        }
    }

    private fun navigateToLoginScreen() {
        Handler().postDelayed({
            val nextScreenIntent = Intent(this, LoginActivity::class.java)
            startActivity(nextScreenIntent)
            finish()
        }, SPLASH_DELAY.toLong())
    }

    private fun navigateToMainScreen() {
        val nextScreenIntent = Intent(this, HomeActivity::class.java)
        startActivity(nextScreenIntent)
        finish()
    }
}