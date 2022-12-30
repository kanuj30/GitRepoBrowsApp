package com.kdroid.gitrepobrowsapp.di.subcomponent

import com.example.githubdemo.di.annotations.ActivityScope
import com.kdroid.gitrepobrowsapp.ui.githubrepo.github_repo.GithubRepoFragment
import com.kdroid.gitrepobrowsapp.ui.repodetails.RepoDetailFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface DashboardComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): DashboardComponent
    }

    fun inject(fragment: GithubRepoFragment)

    fun inject(fragment: RepoDetailFragment)


}