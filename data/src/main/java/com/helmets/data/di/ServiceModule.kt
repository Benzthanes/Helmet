package com.helmets.data.di

import com.helmets.data.network.service.LandingService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ServiceModule {
    @Provides
    @Singleton
    internal fun provideLandingService(retrofit: Retrofit): LandingService {
        return retrofit.create(LandingService::class.java)
    }
}