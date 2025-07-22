package com.dreamcoder.jettriviaapp.repository

import android.util.Log
import com.dreamcoder.jettriviaapp.data.DataOrException
import com.dreamcoder.jettriviaapp.model.QuestionItem
import com.dreamcoder.jettriviaapp.network.QuestionAPI
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val questionAPI: QuestionAPI) {

    private val dataOrException = DataOrException<ArrayList<QuestionItem>, Boolean, Exception>()

    suspend fun getAllQuestions(): DataOrException<ArrayList<QuestionItem>, Boolean, Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = questionAPI.getAllQuestions()
            if (dataOrException.data.toString().isNotEmpty()) dataOrException.loading = false
        } catch (e: Exception) {
            dataOrException.loading = false
            dataOrException.e = e
            Log.d("QuestionRepository", "getAllQuestions: exception:" + e.localizedMessage)
        }
        return dataOrException
    }
}