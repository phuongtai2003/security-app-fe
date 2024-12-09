package com.security.securityapplication.pages.base

import androidx.lifecycle.ViewModel
import com.security.securityapplication.data.error.mapper.ErrorMapperSource
import com.security.securityapplication.usecase.errors.ErrorUseCase
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {
    @Inject
    lateinit var errorManager : ErrorUseCase
}