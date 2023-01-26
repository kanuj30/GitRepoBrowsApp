package com.kdroid.gitrepobrowsapp.di.module

import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class SearchQueryModule {

    @Provides
    @Named("search_query")
    fun provideSearchQuery(): String {
        return ""
    }
}