package com.kdroid.gitrepobrowsapp

import android.app.Application
import com.kdroid.common.logger.DefaultTree
import com.kdroid.gitrepobrowsapp.di.ApplicationComponent
import com.kdroid.gitrepobrowsapp.di.DaggerApplicationComponent
import timber.log.Timber

class GitApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent


    companion object {
        lateinit var instance: GitApplication
        fun get(): GitApplication {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        applicationComponent = DaggerApplicationComponent.factory().create(this)

        // Plant timber
        if (BuildConfig.DEBUG) {
            Timber.plant(object : DefaultTree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    super.log(priority, tag, message, t)
                }
            })
        }
        // retrofit init
        //ApiClient.init()
    }
}