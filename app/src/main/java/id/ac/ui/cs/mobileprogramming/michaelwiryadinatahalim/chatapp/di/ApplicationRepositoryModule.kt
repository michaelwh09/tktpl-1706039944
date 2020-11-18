package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.repositories.db.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(ApplicationComponent::class)
interface ApplicationRepositoryModule {

    @Binds
    fun bindRoomChatRepository(roomChatRepository: RoomChatRepository): IRoomChatRepository

    @Binds
    fun bindMessageRepository(messageRepository: MessageRepository): IMessageRepository

    @Binds
    fun bindFriendRepository(friendRepository: FriendRepository): IFriendRepository
}
