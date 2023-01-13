package com.masterluck.giffyviewer.di

import android.content.Context
import androidx.room.Room
import com.masterluck.giffyviewer.Utils
import com.masterluck.giffyviewer.data.database.GifDAO
import com.masterluck.giffyviewer.data.database.GiffyViewerDatabase
import com.masterluck.giffyviewer.domain.GifAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.giphy.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val url = chain
                        .request()
                        .url()
                        .newBuilder()
                        .addQueryParameter("api_key", Utils.API_KEY)
                        .build()
                    chain.proceed(chain.request().newBuilder().url(url).build())
                }
                .build()
        )
        .build()


    @Provides
    @Singleton
    fun provideGifAPI(retrofit: Retrofit): GifAPI = retrofit.create(GifAPI::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): GiffyViewerDatabase =
        Room.databaseBuilder(context, GiffyViewerDatabase::class.java, "GiffyViewerDatabase")
            .allowMainThreadQueries().build()

    @Provides
    @Singleton
    fun provideGifDAO(database: GiffyViewerDatabase): GifDAO = database.gifDao()

}