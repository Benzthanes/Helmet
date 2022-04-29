package com.helmets.presentation.controller

import com.helmets.domain.BaseUseCase
import com.helmets.domain.model.Mobile
import com.helmets.domain.usecase.MainUseCase
import com.helmets.presentation.BaseController
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class MainController
@Inject constructor(
    private val useCase: MainUseCase,
) : BaseController() {

    fun callGetPhone(
        onSuccess: (List<Mobile>) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        useCase.execute(
            onSuccess,
            onError,
            BaseUseCase.VoidParams(),
            CompositeDisposable()
        )
    }

//    fun callGetPhoneCache(
//        onSuccess: (List<Mobile>) -> Unit,
//        onError: (Throwable) -> Unit
//    ) {
//        getDataBaseUseCase.execute(
//            onSuccess,
//            onError,
//            BaseUseCase.VoidParams(),
//            CompositeDisposable()
//        )
//    }
//
//    fun insertPhoneCache(
//        request: List<Mobile>,
//        onSuccess: () -> Unit,
//        onError: (Throwable) -> Unit
//    ) {
//        insertDataBaseUseCase.execute(
//            onSuccess,
//            onError,
//            InsertDataBaseUseCase.Param(request),
//            CompositeDisposable()
//        )
//    }

}