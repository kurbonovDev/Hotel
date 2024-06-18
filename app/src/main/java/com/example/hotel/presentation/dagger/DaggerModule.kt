package com.example.hotel.presentation.dagger

import com.example.hotel.data.remote.HomeAPI
import com.example.hotel.data.remote.RegistApi
import com.example.hotel.data.repository.BookApartmentsRepositoryImp
import com.example.hotel.data.repository.GetApartmentsHistoryRepositoryImp
import com.example.hotel.data.repository.GetApartmentsRepositoryImp
import com.example.hotel.data.repository.GetRoomInfoImagesImp
import com.example.hotel.data.repository.GetUserRepositoryImp
import com.example.hotel.data.repository.LoginRepositoryImp
import com.example.hotel.data.repository.PreRegistRepositoryImp
import com.example.hotel.data.repository.RegistRepositoryImp
import com.example.hotel.domain.useCases.BookApartmentUseCase
import com.example.hotel.domain.useCases.GetApartmentsHistoryUseCase
import com.example.hotel.domain.useCases.GetApartmentsUseCase
import com.example.hotel.domain.useCases.GetRoomInfoImagesUseCases
import com.example.hotel.domain.useCases.GetUserUseCase
import com.example.hotel.domain.useCases.LoginUseCase
import com.example.hotel.domain.useCases.PreRegistUseCase
import com.example.hotel.domain.useCases.RegistUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DaggerModule {

    @Provides
    fun provideBaseUrl(): String = "http://172.20.10.4:7777"

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): RegistApi {
        return retrofit.create(RegistApi::class.java)
    }

    @Provides
    @Singleton
    fun providePreRegistRepositoryImp(api: RegistApi) = PreRegistRepositoryImp(api)


    @Provides
    @Singleton
    fun providePreRegistUseCases(
        preRegistRepositoryImp: PreRegistRepositoryImp,
        retrofit: Retrofit
    ) = PreRegistUseCase(preRegistRepositoryImp, retrofit)


    @Provides
    @Singleton
    fun provideRegistUseCases(
        registRepositoryImp: RegistRepositoryImp,
        retrofit: Retrofit
    ) = RegistUseCase(registRepositoryImp, retrofit)

    @Provides
    @Singleton
    fun provideLoginRepositoryImp(api: RegistApi) = RegistRepositoryImp(api)

    @Provides
    @Singleton
    fun provideLoginUseCases(
        loginRepositoryImp: LoginRepositoryImp,
        retrofit: Retrofit
    ) = LoginUseCase(loginRepositoryImp, retrofit)


    @Provides
    @Singleton
    fun provideHomeApiService(retrofit: Retrofit): HomeAPI {
        return retrofit.create(HomeAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideHomeRepositoryImp(api: HomeAPI) = GetApartmentsRepositoryImp(api)

    @Provides
    @Singleton
    fun provideGetApartmentUseCases(
        getApartmentsRepositoryImp: GetApartmentsRepositoryImp,
        retrofit: Retrofit
    ) = GetApartmentsUseCase(getApartmentsRepositoryImp, retrofit)

    @Provides
    @Singleton
    fun provideGetRoomInfoImagesRepositoryImp(api: HomeAPI) = GetRoomInfoImagesImp(api)

    @Provides
    @Singleton
    fun provideGetRoomInfoImagesUseCases(
        getRoomInfoImagesImp: GetRoomInfoImagesImp,
        retrofit: Retrofit
    ) = GetRoomInfoImagesUseCases(getRoomInfoImagesImp, retrofit)


    @Provides
    @Singleton
    fun provideBookApartmentRepositoryImp(api: HomeAPI) = BookApartmentsRepositoryImp(api)

    @Provides
    @Singleton
    fun provideBookApartmentUseCases(
        bookApartmentsRepositoryImp:BookApartmentsRepositoryImp,
        retrofit: Retrofit
    ) = BookApartmentUseCase(bookApartmentsRepositoryImp, retrofit)

    @Provides
    @Singleton
    fun provideApartmentHistoryRepositoryImp(api: HomeAPI) = GetApartmentsHistoryRepositoryImp(api)

    @Provides
    @Singleton
    fun provideApartmentHistoryUseCases(
        getApartmentsHistoryRepositoryImp: GetApartmentsHistoryRepositoryImp,
        retrofit: Retrofit
    ) = GetApartmentsHistoryUseCase(getApartmentsHistoryRepositoryImp, retrofit)


    @Provides
    @Singleton
    fun provideGetUserRepositoryImp(api: HomeAPI) = GetUserRepositoryImp(api)

    @Provides
    @Singleton
    fun provideGetUserUseCases(
        getUserRepositoryImp: GetUserRepositoryImp,
        retrofit: Retrofit
    ) = GetUserUseCase(getUserRepositoryImp, retrofit)

}
