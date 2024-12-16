package com.security.securityapplication.pages.components.splash

import android.support.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.security.securityapplication.data.DataRepositorySource
import com.security.securityapplication.data.Resource
import com.security.securityapplication.data.dto.profile.UserModel
import com.security.securityapplication.pages.base.BaseViewModel
import com.security.securityapplication.utils.SingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashVM @Inject constructor(private val dataRepository: DataRepositorySource) : BaseViewModel() {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val splashLiveDataPrivate = MutableLiveData<Resource<UserModel>>()
    val splashLiveData: MutableLiveData<Resource<UserModel>> get() = splashLiveDataPrivate

    init {
        getProfile()
    }

    private fun getProfile() {
        viewModelScope.launch {
            splashLiveDataPrivate.value = Resource.Loading()
            dataRepository.getProfile().collect {
                splashLiveDataPrivate.value = it
            }
        }
    }
}