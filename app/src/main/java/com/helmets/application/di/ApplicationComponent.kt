package com.helmets.application.di

import android.app.Application
import com.helmets.application.AndroidApplication
import com.helmets.application.di.module.ActivityBuilder
import com.helmets.application.di.module.AndroidModule
import com.helmets.application.di.module.ApplicationModule
import com.helmets.application.di.module.FragmentBuilder
import com.helmets.data.di.DataModule
import com.helmets.data.di.NetworkModule
import com.helmets.data.di.RepositoryModule
import com.helmets.data.di.ServiceModule
import com.helmets.domain.di.DomainModule
import com.helmets.domain.di.UseCaseModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Component(
    modules = [
        ActivityBuilder::class,
        FragmentBuilder::class,
        ApplicationModule::class,
        AndroidModule::class,
        DomainModule::class,
        DataModule::class,
        RepositoryModule::class,
        ServiceModule::class,
        NetworkModule::class,
        UseCaseModule::class,
        AndroidSupportInjectionModule::class,
    ]
)

@Singleton
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: AndroidApplication)
}
