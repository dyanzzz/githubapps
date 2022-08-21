package com.accenture.githubapps.di

import com.accenture.githubapps.BuildConfig
import com.accenture.githubapps.api.AppService
import com.accenture.githubapps.api.AuthInterceptor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, CoreDataModule::class])
class AppModule {

    /* --------------------------------- Provide Remote Data Source ------------------------------*/
    @Singleton
    @Provides
    fun provideSurveyService(@AppAPI okhttpClient: OkHttpClient,
                             converterFactory: GsonConverterFactory
    ) = provideService(okhttpClient, converterFactory, AppService::class.java)

    @AppAPI
    @Provides
    fun providePrivateOkHttpClient(
        upstreamClient: OkHttpClient
    ): OkHttpClient {
        return upstreamClient.newBuilder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(AuthInterceptor(BuildConfig.API_DEVELOPER_TOKEN))
            .build()
    }

    private fun createRetrofit(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okhttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun <T> provideService(okhttpClient: OkHttpClient,
                                   converterFactory: GsonConverterFactory, clazz: Class<T>): T {
        return createRetrofit(okhttpClient, converterFactory).create(clazz)
    }

    /* --------------------------------- Provide Local Database Data Source ----------------------*/
    /*@Singleton
    @Provides
    fun provideDb(app: Application) = AppDatabase.getInstance(app)*/

    /* --------------------------------- Provide DAO FastOne ----------------------*/
    /*@Singleton
    @Provides
    fun provideInsertMasterData(db: AppDatabase) = db.insertMasterData()*/

    @CoroutineScopeIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)

}
