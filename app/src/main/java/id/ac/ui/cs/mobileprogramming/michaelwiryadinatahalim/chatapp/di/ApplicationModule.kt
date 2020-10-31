package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ChatAppDatabase
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.dao.FriendDao
import id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.dao.RoomChatDao
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideChatAppDatabase(@ApplicationContext appContext: Context): ChatAppDatabase {
        return Room
            .databaseBuilder(appContext,
                ChatAppDatabase::class.java,
                "chat-app-db")
            .build()
    }

    @Provides
    @Singleton
    fun provideFriendDao(db: ChatAppDatabase): FriendDao {
        return db.friendDao()
    }

    @Provides
    @Singleton
    fun provideRoomChatDao(db: ChatAppDatabase): RoomChatDao {
        return db.roomChatDao()
    }
}
