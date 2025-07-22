package com.dreamcoder.jettriviaapp.network

import com.dreamcoder.jettriviaapp.model.Question
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionAPI {

    @GET("world.json")
    suspend fun getAllQuestions(): Question
}