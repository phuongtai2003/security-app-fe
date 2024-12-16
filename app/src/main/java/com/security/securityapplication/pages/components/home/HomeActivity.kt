package com.security.securityapplication.pages.components.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.security.securityapplication.R
import com.security.securityapplication.data.Resource
import com.security.securityapplication.data.dto.profile.UserModel
import com.security.securityapplication.data.dto.rates.ExchangeRates
import com.security.securityapplication.databinding.ActivityHomeBinding
import com.security.securityapplication.pages.base.BaseActivity
import com.security.securityapplication.pages.components.login.LoginActivity
import com.security.securityapplication.utils.observe
import com.security.securityapplication.utils.toGone
import com.security.securityapplication.utils.toVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeVM by viewModels()
    override fun observeViewModel() {
        observe(homeViewModel.homeLiveData, ::handleHomeResult)
        observe(homeViewModel.logoutLiveData, ::handleLogoutResult)
        observe(homeViewModel.exchangeRatesLiveData, ::handleExchangeRatesResult)
    }

    override fun initViewBinding() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.logoutButton.setOnClickListener { doLogout() }
    }

    private fun handleHomeResult(status: Resource<UserModel>) {
        when (status) {
            is Resource.Loading -> binding.loaderView.toVisible()
            is Resource.Success -> status.data?.let {
                binding.loaderView.toGone()
                binding.username.text = it.email
                binding.name.text = it.name ?: "No Name"
            }
            is Resource.Error -> {
                binding.loaderView.toGone()
            }
        }
    }

    private fun handleExchangeRatesResult(status: Resource<ExchangeRates>) {
        when (status) {
            is Resource.Loading -> binding.loaderView.toVisible()
            is Resource.Success -> {
                val ratesData = status.data?.rates
                binding.vietnameseExchangeRate.text = "${String.format("%.2f", ratesData?.vnd)} : 1 Euro"
                binding.usdExchangeRate.text = "${String.format("%.2f", ratesData?.usd)} : 1 Euro"
                binding.loaderView.toGone()
            }
            is Resource.Error -> {
                binding.loaderView.toGone()
            }
        }
    }

    private fun doLogout() {
        homeViewModel.doLogout()
    }

    private fun handleLogoutResult(status: Resource<Boolean>) {
        when (status) {
            is Resource.Loading -> binding.loaderView.toVisible()
            is Resource.Success -> {
                binding.loaderView.toGone()
                goToLogin()
            }
            is Resource.Error -> {
                binding.loaderView.toGone()
            }
        }
    }

    private fun goToLogin() {
        val loginIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginIntent)
        finish()
    }
}