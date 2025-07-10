package compose.thili.demo.thirdscreen.viewmodel

import androidx.lifecycle.ViewModel
import compose.thili.demo.data.repository.QuizRepository
import compose.thili.demo.thirdscreen.data.ThirdScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ThirdScreenViewModel(private val quizRepository: QuizRepository = QuizRepository()) :
    ViewModel() {

    private val _uiState = MutableStateFlow(ThirdScreenState())
    val uiState: StateFlow<ThirdScreenState> = _uiState.asStateFlow()

    fun getQuizData() = quizRepository.getQuizData(_uiState.value.questionIndex)

    fun getQuestionIndex() = _uiState.value.questionIndex

    fun getCorrectAnswersCount() = _uiState.value.correctAnswers

    fun updateQuestionIndex() {
        if (getQuestionIndex() == 9) {
            _uiState.update { currentState ->
                currentState.copy(shouldShowFinalScreen = true)
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(questionIndex = currentState.questionIndex + 1)
            }
        }
    }

    fun resetQuestionIndex() {
        _uiState.update { currentState ->
            currentState.copy(
                questionIndex = 0,
                shouldShowFinalScreen = false,
                shouldShowAnswerCard = false,
                correctAnswers = 0
            )
        }
    }

    fun updateCorrectAnswers(isAnswerCorrect: Boolean) {
        if (isAnswerCorrect) {
            _uiState.update { currentState ->
                currentState.copy(correctAnswers = currentState.correctAnswers + 1)
            }
        }
    }

    fun showAnswerCard(usersAnswer: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(shouldShowAnswerCard = true, usersAnswer = usersAnswer)
        }
    }

    fun showQuestionCard() {
        _uiState.update { currentState ->
            currentState.copy(shouldShowAnswerCard = false)
        }
    }


}