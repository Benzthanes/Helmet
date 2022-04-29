package com.helmets.data.di

import com.helmets.data.mapper.LandingMapper
import com.helmets.data.network.api.LandingApi
import com.helmets.data.repository.LandingRepository
import com.helmets.domain.repository.LandingRepositoryContractor
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    @Provides
    internal fun provideLandingRepository(
        api: LandingApi,
        mapper: LandingMapper
    ): LandingRepositoryContractor {
        return LandingRepository(api, mapper)
    }
}