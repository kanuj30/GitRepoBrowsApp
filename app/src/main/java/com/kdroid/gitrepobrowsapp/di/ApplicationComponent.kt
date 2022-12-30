package com.kdroid.gitrepobrowsapp.di

import android.content.Context
import com.kdroid.gitrepobrowsapp.di.module.NetworkModule
import com.kdroid.gitrepobrowsapp.di.module.ViewModelModule
import com.kdroid.gitrepobrowsapp.di.subcomponent.DashboardComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ViewModelModule::class, SubComponentsModule::class])
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }

    fun addDashboardComponent(): DashboardComponent.Factory

}

@Module(subcomponents = [DashboardComponent::class])
object SubComponentsModule