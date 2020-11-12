package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase.FcmRepository
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase.LoginRepository
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.firebase.StorageRepository


@Module
@InstallIn(ActivityRetainedComponent::class)
object FirebaseModule {

    @Provides
    fun provideFirebaseLoginRepository() : LoginRepository {
        return LoginRepository()
    }

    @Provides
    fun provideFcmRepository() : FcmRepository {
        return FcmRepository()
    }
}
