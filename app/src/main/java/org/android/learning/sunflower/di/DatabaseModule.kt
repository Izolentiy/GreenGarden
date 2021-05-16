package org.android.learning.sunflower.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.android.learning.sunflower.data.AppDatabase
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        callback: AppDatabase.Callback
    ) = Room.databaseBuilder(context, AppDatabase::class.java, "green_garden_db")
        .addCallback(callback).build()

    @Provides
    fun providePlantDao(appDatabase: AppDatabase) = appDatabase.plantDao()

    @Provides
    fun provideGardenPlantDao(appDatabase: AppDatabase) = appDatabase.gardenPlantDao()

    @Provides
    @Singleton
    @ApplicationScope
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class ApplicationScope
}