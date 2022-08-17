package com.umain.fooddelivery.di


import android.content.Context
import com.umain.fooddelivery.BuildConfig
import com.umain.fooddelivery.data.api.ApiHelper
import com.umain.fooddelivery.data.api.ApiHelperImpl
import com.umain.fooddelivery.data.api.ApiService
import com.umain.fooddelivery.data.repository.Repository
import com.umain.fooddelivery.utils.Constants
import com.umain.fooddelivery.utils.network.NetworkHelper
import com.umain.fooddelivery.utils.network.NetworkHelperImpl
import com.umain.fooddelivery.utils.resource.ResourceUtilHelper
import com.umain.fooddelivery.utils.resource.ResourceUtilHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HiltModules {


    @Provides
    @Singleton
    fun provideBaseUrl() = BuildConfig.BASE_URL


    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext mContext: Context): OkHttpClient =
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            OkHttpClient.Builder().connectTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(loggingInterceptor)
                .build()
        } else OkHttpClient
            .Builder()
            .retryOnConnectionFailure(true)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(mOkHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .build()


    @Provides
    @Singleton
    fun provideNetworkHelperImpl(@ApplicationContext mContext: Context): NetworkHelper =
        NetworkHelperImpl(mContext)


    @Provides
    @Singleton
    fun provideApiService(mRetrofit: Retrofit): ApiService =
        mRetrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(mApiHelper: ApiHelperImpl): ApiHelper = mApiHelper

    @Provides
    @Singleton
    fun provideResourceUtilHelperImpl(@ApplicationContext mContext: Context): ResourceUtilHelper =
        ResourceUtilHelperImpl(mContext)

    @Provides
    @Singleton
    fun provideRepository(mApiHelper: ApiHelper): Repository = Repository(mApiHelper)


}