package com.dreamcoder.jettriviaapp.di

import com.dreamcoder.jettriviaapp.network.QuestionAPI
import com.dreamcoder.jettriviaapp.repository.QuestionRepository
import com.dreamcoder.jettriviaapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideQuestionAPI(): QuestionAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuestionAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideQuestionRepository(api: QuestionAPI): QuestionRepository {
        return QuestionRepository(api)
    }
}