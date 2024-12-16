package com.security.securityapplication.pages.components.login

import android.support.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.security.securityapplication.data.DataRepositorySource
import com.security.securityapplication.data.Resource
import com.security.securityapplication.data.dto.login.LoginRequest
import com.security.securityapplication.data.dto.login.LoginResponse
import com.security.securityapplication.data.error.CHECK_YOUR_FIELDS
import com.security.securityapplication.data.error.PASSWORD_ERROR
import com.security.securityapplication.data.error.USER_NAME_ERROR
import com.security.securityapplication.pages.base.BaseViewModel
import com.security.securityapplication.utils.RegexUtils.isValidEmail
import com.security.securityapplication.utils.SingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginVM @Inject constructor(private val dataRepository: DataRepositorySource) : BaseViewModel() {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val loginLiveDataPrivate = MutableLiveData<Resource<LoginResponse>>()
    val loginLiveData: MutableLiveData<Resource<LoginResponse>> get() = loginLiveDataPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showSnackBarPrivate = MutableLiveData<SingleEvent<Any>>()
    val showSnackBar: LiveData<SingleEvent<Any>> get() = showSnackBarPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showToastPrivate = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = showToastPrivate

    fun doLogin(userName: String, passWord: String) {
        val isUsernameValid = isValidEmail(userName)
        val isPassWordValid = passWord.trim().length > 6
        if (isUsernameValid && !isPassWordValid) {
            loginLiveDataPrivate.value = Resource.Error(errorCode = PASSWORD_ERROR)
        } else if (!isUsernameValid && isPassWordValid) {
            loginLiveDataPrivate.value = Resource.Error(errorCode = USER_NAME_ERROR)
        } else if (!isUsernameValid && !isPassWordValid) {
            loginLiveDataPrivate.value = Resource.Error(errorCode = CHECK_YOUR_FIELDS)
        } else {
            viewModelScope.launch {
                loginLiveDataPrivate.value = Resource.Loading()
                dataRepository.login(loginRequest = LoginRequest(userName, passWord)).collect {
                    loginLiveDataPrivate.value = it
                }
            }
        }
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.value = SingleEvent(error.errorMessage)
    }
}