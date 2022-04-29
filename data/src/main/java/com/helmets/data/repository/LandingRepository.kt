package com.helmets.data.repository

import com.helmets.data.mapper.LandingMapper
import com.helmets.data.network.api.LandingApi
import com.helmets.domain.model.Mobile
import com.helmets.domain.repository.LandingRepositoryContractor
import io.reactivex.Single
import javax.inject.Inject

class LandingRepository @Inject constructor(
    private val api: LandingApi,
    private val mapper: LandingMapper
) : LandingRepositoryContractor {

    override fun getPhoneList(): Single<List<Mobile>> {
        return api.getMobileList().map {
            mapper.transformMobile(it)
        }
    }

}