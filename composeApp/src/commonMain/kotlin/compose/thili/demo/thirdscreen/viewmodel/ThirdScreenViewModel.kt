package compose.thili.demo.thirdscreen.viewmodel

import androidx.lifecycle.ViewModel
import compose.thili.demo.data.repository.QuizRepository
import compose.thili.demo.thirdscreen.data.ThirdScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ThirdScreenViewModel(private val quizRepository: QuizRepository = QuizRepository()): ViewModel() {

    private val _uiState = MutableStateFlow(ThirdScreenState())
    val uiState: StateFlow<ThirdScreenState> = _uiState.asStateFlow()

    fun getQuizData() = try {
        quizRepository.getQuizData(_uiState.value.questionIndex)
    } catch (e: NoSuchElementException){
        resetQuestionIndex()
        quizRepository.getQuizData(_uiState.value.questionIndex)
    }

    fun getQuestionIndex() = _uiState.value.questionIndex

    fun updateQuestionIndex(){
        _uiState.update { currentState ->
            currentState.copy(questionIndex = currentState.questionIndex + 1)
        }
    }

    private fun resetQuestionIndex(){
        _uiState.update { currentState ->
            currentState.copy(questionIndex = 0)
        }
    }

    fun updateCorrectAnswers(){
        _uiState.update { currentState ->
            currentState.copy(correctAnswers = currentState.correctAnswers + 1)
        }
    }
    fun showAnswerCard(usersAnswer: Boolean){
        _uiState.update { currentState ->
            currentState.copy(shouldShowAnswerCard = true, usersAnswer = usersAnswer)
        }
    }
    fun showQuestionCard(){
        _uiState.update { currentState ->
            currentState.copy(shouldShowAnswerCard = false)
        }
    }



}