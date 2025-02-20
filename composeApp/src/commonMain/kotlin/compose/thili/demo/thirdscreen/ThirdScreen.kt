package compose.thili.demo.thirdscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.viewmodel.compose.viewModel
import compose.thili.demo.ThiliScreens
import compose.thili.demo.sharemanager.rememberShareManager
import compose.thili.demo.thirdscreen.viewmodel.ThirdScreenViewModel
import compose.thili.demo.ui.components.ThiliAppBar
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import thilicmp.composeapp.generated.resources.Res
import thilicmp.composeapp.generated.resources.answer_right
import thilicmp.composeapp.generated.resources.answer_wrong
import thilicmp.composeapp.generated.resources.back
import thilicmp.composeapp.generated.resources.quiz_question_title
import thilicmp.composeapp.generated.resources.share_text

@Composable
fun ThirdScreen(
    viewModel: ThirdScreenViewModel = viewModel { ThirdScreenViewModel() },
    canNavigateBack: Boolean,
    navigateUp: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()
    val shareManager = rememberShareManager()


    Scaffold(topBar = {
        ThiliAppBar(
            canNavigateBack = canNavigateBack,
            navigateUp = navigateUp,
            currentScreen = ThiliScreens.QuizScreen
        )
    }) { innerPadding ->

        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {

            Box(
                modifier = Modifier.matchParentSize().paint(
                    painterResource(Res.drawable.back), contentScale = ContentScale.FillBounds
                )
            ) {

                if(uiState.shouldShowFinalScreen) {
                    val textToShare = stringResource(Res.string.share_text,viewModel.getCorrectAnswersCount())
                    showResult(viewModel) { shareManager.share(textToShare) }
                } else if (uiState.shouldShowAnswerCard) {
                    showAnswer(viewModel = viewModel, uiState.usersAnswer)
                } else {
                    showQuestion(viewModel = viewModel)
                }
            }

        }


    }


}

@Composable
fun showQuestion(viewModel: ThirdScreenViewModel) {
    with(viewModel) {
        QuestionCard(title = stringResource(Res.string.quiz_question_title, getQuestionIndex()+1),
            questionDescription = stringResource(getQuizData().question),
            onFalseButtonClicked = { showAnswerCard(false) },
            onTrueButtonClicked = { showAnswerCard(true) })
    }
}

@Composable
fun showAnswer(viewModel: ThirdScreenViewModel, usersAnswer: Boolean) {
    with(viewModel) {
        AnswerCard(title = stringResource(Res.string.quiz_question_title, getQuestionIndex()+1),
            questionDescription = stringResource(getQuizData().question),
            answerStatus = getAnswerStatus(usersAnswer, getQuizData().isItTrue),
            answerStatusColor = getAnswerStatusColor(usersAnswer, getQuizData().isItTrue),
            explanation = stringResource(getQuizData().answer),
            onNextButtonClicked = {
                updateCorrectAnswers(usersAnswer == getQuizData().isItTrue)
                updateQuestionIndex()
                showQuestionCard()
            })
    }
}

@Composable
fun showResult(viewModel: ThirdScreenViewModel,onShareButtonClicked: () -> Unit) {
    with(viewModel){
        QuizResultScreen(onShareButtonClicked = onShareButtonClicked ,
            onTryAgainButtonClicked = { viewModel.resetQuestionIndex() },
            modifier = Modifier,getCorrectAnswersCount())
    }
}

@Composable
fun getAnswerStatus(usersAnswer: Boolean, correctAnswer: Boolean) =
    if (usersAnswer == correctAnswer)
        stringResource(Res.string.answer_right).uppercase()
    else stringResource(Res.string.answer_wrong).uppercase()


@Composable
fun getAnswerStatusColor(usersAnswer: Boolean, correctAnswer: Boolean) =
    if (usersAnswer == correctAnswer)
        Color.Green
    else Color.Red




