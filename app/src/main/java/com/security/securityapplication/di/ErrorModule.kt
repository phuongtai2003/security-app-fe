package com.security.securityapplication.di

import com.security.securityapplication.data.error.mapper.ErrorMapper
import com.security.securityapplication.data.error.mapper.ErrorMapperSource
import com.security.securityapplication.usecase.errors.ErrorManager
import com.security.securityapplication.usecase.errors.ErrorUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ErrorModule {
    @Binds
    @Singleton
    abstract fun provideErrorManager(errorManager: ErrorManager): ErrorUseCase

    @Binds
    @Singleton
    abstract fun provideErrorMapper(errorMapper: ErrorMapper) : ErrorMapperSource
}