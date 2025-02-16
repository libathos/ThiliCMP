package compose.thili.demo.data.quiz

import org.jetbrains.compose.resources.StringResource

data class QuizInfo(
    val question: StringResource,
    val answer: StringResource,
    val questionNumber: Int,
    val isItTrue: Boolean
)