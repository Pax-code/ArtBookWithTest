package com.example.artbooktesting.dependencyinjection


import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.artbooktesting.R
import com.example.artbooktesting.databases.api.API
import com.example.artbooktesting.databases.room.ArtDatabase
import com.example.artbooktesting.databases.room.DAO
import com.example.artbooktesting.repo.ArtBookRepository
import com.example.artbooktesting.repo.ArtBookRepositoryInterface
import com.example.artbooktesting.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDB(

        @ApplicationContext
        context: Context

    ) = Room.databaseBuilder(

        context,
        ArtDatabase::class.java,
        "ArtBookRoomDB"

        ).build()

    @Singleton
    @Provides
    fun injectDAO(db : ArtDatabase) =  db.dao()

    @Singleton
    @Provides
    fun injectRetrofitAPI() : API{

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(API::class.java)
    }

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.img)
                .error(R.drawable.img)
        )

    @Singleton
    @Provides
    fun injectNormalRepo(dao: DAO, api: API) = ArtBookRepository(dao,api) as ArtBookRepositoryInterface

}