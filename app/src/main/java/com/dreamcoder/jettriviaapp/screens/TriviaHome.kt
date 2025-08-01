package com.dreamcoder.jettriviaapp.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamcoder.jettriviaapp.component.Questions


@Composable
fun TriviaHome(viewModel: QuestionsViewModel = hiltViewModel(), modifier: Modifier) {
    Questions(viewModel, modifier)
}