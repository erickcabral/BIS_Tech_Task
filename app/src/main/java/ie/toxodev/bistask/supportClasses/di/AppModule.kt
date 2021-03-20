package ie.toxodev.bistask.supportClasses.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ie.toxodev.bistask.supportClasses.Repository
import ie.toxodev.bistask.supportClasses.service.BisAPI
import ie.toxodev.bistask.supportClasses.service.BisService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun bodyInterceptor() = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun httpClient(bodyInterceptor: HttpLoggingInterceptor) =
        OkHttpClient().newBuilder()
            .connectTimeout(2, TimeUnit.SECONDS)
            .writeTimeout(2, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(bodyInterceptor)
            .build()

    @Singleton
    @Provides
    fun providesService(httpClient: OkHttpClient): BisAPI {
        return Retrofit.Builder().baseUrl("https://opsmonservicer.azurewebsites.net/api/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(BisAPI::class.java)
    }


    @Singleton
    @Provides
    fun providesRepository(service: BisService) = Repository(service)

}