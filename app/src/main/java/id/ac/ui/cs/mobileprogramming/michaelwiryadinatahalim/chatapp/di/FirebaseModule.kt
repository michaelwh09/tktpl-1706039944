package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase.FcmRepository
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase.LoginRepository
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase.UserFirestoreRepository
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(ActivityRetainedComponent::class)
object FirebaseModule {

    @Provides
    fun provideFirebaseUserRepository() : UserRepository {
        return UserRepository()
    }

    @Provides
    fun provideFirebaseLoginRepository() : LoginRepository {
        return LoginRepository()
    }

    @Provides
    fun provideFcmRepository() : FcmRepository {
        return FcmRepository()
    }

    @Provides
    fun provideUserFirestoreRepository() : UserFirestoreRepository {
        return UserFirestoreRepository()
    }
}
