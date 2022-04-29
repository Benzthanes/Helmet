package com.helmets.data.network.api

import com.helmets.data.model.MobileEntity
import com.helmets.data.network.service.LandingService
import io.reactivex.Single
import javax.inject.Inject

class LandingApi @Inject constructor(
        private val service: LandingService
) {
    fun getMobileList(): Single<List<MobileEntity>> {
        return service.getMobileList()
    }
}