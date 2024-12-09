package com.security.securityapplication.pages.components.home

import android.support.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.security.securityapplication.data.DataRepository
import com.security.securityapplication.data.Resource
import com.security.securityapplication.data.dto.profile.UserModel
import com.security.securityapplication.pages.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(private val dataRepository: DataRepository) : BaseViewModel() {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val privateHomeLiveData = MutableLiveData<Resource<UserModel>>()
    val homeLiveData: MutableLiveData<Resource<UserModel>> get() = privateHomeLiveData

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val privateLogoutLiveData = MutableLiveData<Resource<Boolean>>()
    val logoutLiveData: MutableLiveData<Resource<Boolean>> get() = privateLogoutLiveData

    init {
        getProfile()
    }

    private fun getProfile() {
        privateHomeLiveData.value = Resource.Loading()
        viewModelScope.launch {
            dataRepository.getProfile().collect {
                privateHomeLiveData.value = it
            }
        }
    }

    fun doLogout() {
        privateLogoutLiveData.value = Resource.Loading()
        viewModelScope.launch {
            dataRepository.logout().collect {
                privateLogoutLiveData.value = it
            }
        }
    }
}