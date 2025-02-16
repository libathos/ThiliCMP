package compose.thili.demo.thirdscreen.data

data class ThirdScreenState(
    var questionIndex: Int = 0,
    var correctAnswers: Int = 0,
    var shouldShowAnswerCard: Boolean = false,
    var usersAnswer: Boolean = false
)