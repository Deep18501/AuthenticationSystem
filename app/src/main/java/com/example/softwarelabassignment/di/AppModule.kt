package com.example.softwarelabassignment.di

import com.example.softwarelabassignment.data.Api
import com.example.softwarelabassignment.data.LoginSignUpRepositoryImpl
import com.example.softwarelabassignment.data.RetrofitInstance
import com.example.softwarelabassignment.domain.repository.LoginSignUpRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi(): Api {
        return RetrofitInstance.api
    }

    @Provides
    @Singleton
    fun providesRepository(api:Api):LoginSignUpRepository{
        return LoginSignUpRepositoryImpl(api)
    }

}