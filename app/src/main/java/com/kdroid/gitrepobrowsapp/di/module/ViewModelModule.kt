package com.kdroid.gitrepobrowsapp.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kdroid.gitrepobrowsapp.di.annotations.ViewModelKey
import com.kdroid.gitrepobrowsapp.ui.githubrepo.github_repo.GithubRepoViewModel
import com.kdroid.gitrepobrowsapp.ui.repodetails.RepoDetailViewModel
import com.kdroid.gitrepobrowsapp.ui.repository.GitRepository
import com.kdroid.gitrepobrowsapp.viewmodelfactory.GitViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Provider
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Provides
    @Singleton
    fun providesViewModelFactory(
        map: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>,
    ): ViewModelProvider.Factory {
        return GitViewModelFactory(map)
    }

    @Provides
    @IntoMap
    @ViewModelKey(GithubRepoViewModel::class)
    fun providesRepositoriesViewModel(repository: GitRepository): ViewModel {
        return GithubRepoViewModel(repository)
    }

    @Provides
    @IntoMap
    @ViewModelKey(RepoDetailViewModel::class)
    fun providesRepoDetailViewModel(
        githubRepository: GitRepository,
    ): ViewModel {
        return RepoDetailViewModel(githubRepository)
    }


}