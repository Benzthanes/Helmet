package com.helmets.domain.usecase

import com.helmets.domain.BaseSingleUseCase
import com.helmets.domain.BaseUseCase
import com.helmets.domain.ThreadExecutor
import com.helmets.domain.ThreadExecutors
import com.helmets.domain.repository.LandingRepositoryContractor
import com.helmets.domain.model.Mobile
import javax.inject.Inject
import javax.inject.Named

class MainUseCase @Inject constructor(
    @Named(ThreadExecutors.SUBSCRIBER_ON_IO) subscriberOn: ThreadExecutor,
    @Named(ThreadExecutors.OBSERVER_ON) observerOn: ThreadExecutor,
    private val repository: LandingRepositoryContractor
) : BaseSingleUseCase<List<Mobile>, BaseUseCase.VoidParams>(
    subscriberOn,
    observerOn,
    builder = { repository.getPhoneList() }
)