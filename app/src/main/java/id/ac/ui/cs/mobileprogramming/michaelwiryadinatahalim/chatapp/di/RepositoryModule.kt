package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindFriendRepository(friendRepository: FriendRepository): IFriendRepository

    @Binds
    abstract fun bindRoomChatRepository(roomChatRepository: RoomChatRepository): IRoomChatRepository

    @Binds
    abstract fun bindMessageRepository(messageRepository: MessageRepository): IMessageRepository
}
