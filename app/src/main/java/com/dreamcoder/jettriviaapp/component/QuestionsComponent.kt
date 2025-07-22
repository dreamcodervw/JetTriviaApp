package com.dreamcoder.jettriviaapp.component

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dreamcoder.jettriviaapp.model.QuestionItem
import com.dreamcoder.jettriviaapp.screens.QuestionsViewModel
import com.dreamcoder.jettriviaapp.util.AppColors

@Composable
fun Questions(viewModel: QuestionsViewModel, modifier: Modifier) {
    val questions = viewModel.data.value.data?.toMutableList()
    if (viewModel.data.value.loading == true) {
        CircularProgressIndicator(modifier = modifier)
        Log.d("MainActivity", "Questions: Loading")
    } else {
        questions?.forEach {
            Log.d("MainActivity", "Questions: ${it.question}")
            QuestionDisplay(questionItem = questions.first())
        }
    }
}

//@Preview
@Composable
fun QuestionDisplay(
    modifier: Modifier = Modifier,
    questionItem: QuestionItem,
//    questionIndex: MutableState<Int>,
//    viewModel: QuestionsViewModel,
//    onNextClicked: (Int) -> Unit
) {
    val choicesState = remember(questionItem) {
        questionItem.choices.toMutableList()
    }
    val answerState = remember(questionItem) {
        mutableStateOf<Int?>(null)
    }
    val correctAnswerState = remember(questionItem) {
        mutableStateOf<Boolean?>(null)
    }
    val updateAnswer: (Int) -> Unit = remember(questionItem) {
        {
            answerState.value = it
            correctAnswerState.value = choicesState[it] == questionItem.answer
        }
    }
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(4.dp),
        color = AppColors.mDarkPurple
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            QuestionTracker()
            val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            DrawDottedLine(pathEffect)
            QuestionColumn(modifier, questionItem)
            // choices
            choicesState.forEachIndexed { index, answerText ->
                Row(
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                        .height(45.dp)
                        .border(
                            width = 4.dp,
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    AppColors.mOffDarkPurple,
                                    AppColors.mOffDarkPurple
                                )
                            ),
                            shape = RoundedCornerShape(15.dp)
                        )
                        .clip(
                            RoundedCornerShape(
                                topStartPercent = 50,
                                topEndPercent = 50,
                                bottomStartPercent = 50,
                                bottomEndPercent = 50
                            )
                        )
                        .background(Color.Transparent),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (answerState.value == index),
                        onClick = {
                            updateAnswer(index)
                        },
                        modifier = Modifier.padding(start = 16.dp),
                        colors = RadioButtonDefaults.colors(
                            selectedColor = if (correctAnswerState.value == true && index == answerState.value) Color.Green.copy(
                                alpha = 0.2f
                            ) else Color.Red.copy(alpha = 0.2f)
                        )
                    )
                    Text(text = answerText)
                }
            }
        }
    }
}

@Composable
fun QuestionTracker(counter: Int = 10, outOff: Int = 100) {
    Text(text = buildAnnotatedString {
        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
            withStyle(
                style = SpanStyle(
                    color = AppColors.mLightGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 27.sp
                )
            ) {
                append("Question $counter/")
                withStyle(style = SpanStyle(color = AppColors.mLightGray,
                    fontWeight = FontWeight.Light,
                    fontSize = 27.sp
                )) {
                    append("$outOff")
                }
            }
        }
    }, modifier = Modifier.padding(20.dp))
}


@Composable
fun DrawDottedLine(pathEffect: PathEffect) {
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(1.dp)) {
        drawLine(
            color = AppColors.mLightGray,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect
        )
    }
}

@Composable
fun QuestionColumn(modifier: Modifier, questionItem: QuestionItem) {
    Column {
        Text(
            text = questionItem.question,
            modifier = Modifier
                .padding(6.dp)
                .align(Alignment.Start)
                .fillMaxHeight(0.3f),
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 22.sp,
            color = AppColors.mLightGray
        )
    }
}
