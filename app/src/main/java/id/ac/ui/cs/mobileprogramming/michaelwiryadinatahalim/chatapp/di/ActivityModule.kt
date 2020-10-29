package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.FragmentScoped
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(ActivityRetainedComponent::class)
object ActivityModule {

    @Provides
    fun provideFirebaseUserRepository() : UserRepository {
        return UserRepository()
    }
}