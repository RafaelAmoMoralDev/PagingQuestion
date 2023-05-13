package learning.rafaamo.pagingquestion.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import learning.rafaamo.pagingquestion.domain.AppDatabase
import learning.rafaamo.pagingquestion.domain.Item
import learning.rafaamo.pagingquestion.domain.ItemDao
import java.util.*
import java.util.concurrent.Executors
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

  @Singleton
  @Provides
  fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
    val appDatabase: AppDatabase =  Room.databaseBuilder(
      context,
      AppDatabase::class.java,
      "app_database"
    ).build()

    Executors.newSingleThreadScheduledExecutor().execute {
      appDatabase.itemDao().insertAll(Item.getData())
    }

    return appDatabase
  }

  @Singleton
  @Provides
  fun provideItemDao(database: AppDatabase): ItemDao {
    return database.itemDao()
  }

}
