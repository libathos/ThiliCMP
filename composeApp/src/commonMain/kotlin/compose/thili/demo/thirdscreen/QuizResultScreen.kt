package compose.thili.demo.thirdscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.thili.demo.ui.components.BottomButtonComposable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import thilicmp.composeapp.generated.resources.Res
import thilicmp.composeapp.generated.resources.finished_quiz_banner
import thilicmp.composeapp.generated.resources.quiz_disclaimer
import thilicmp.composeapp.generated.resources.quiz_final_result
import thilicmp.composeapp.generated.resources.quiz_share_score
import thilicmp.composeapp.generated.resources.quiz_try_again


@Composable
fun QuizResultScreen(
    onShareButtonClicked: () -> Unit,
    onTryAgainButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    correctAnswers: Int
) {

    Box(modifier = modifier.fillMaxSize().padding(20.dp)) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically)
                .border(2.dp, Color(0xFFFA5E73)).align(Alignment.TopCenter)
                .background(Color.White), contentAlignment = Alignment.CenterStart
        ) {
            Column(
                modifier = modifier.fillMaxWidth().background(Color.White).padding(15.dp)
            ) {


                Box(
                    modifier = Modifier.fillMaxWidth().paint(
                        painterResource(Res.drawable.finished_quiz_banner), contentScale = ContentScale.FillBounds
                    )
                ) {

                    Row (Modifier.fillMaxWidth().padding(10.dp)){
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = stringResource(Res.string.quiz_final_result, correctAnswers),
                            modifier = Modifier.weight(1f).padding(end = 15.dp),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            ),
                            textAlign = TextAlign.Start
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

                BottomButtonComposable(
                    text = stringResource(Res.string.quiz_share_score),
                    onNextButtonClick = onShareButtonClicked
                )

                Spacer(Modifier.height(20.dp))


                Text(
                    text = stringResource(Res.string.quiz_disclaimer),
                    modifier = Modifier.padding(10.dp),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFA5E73)
                    ),
                    textAlign = TextAlign.Start
                )

                Spacer(Modifier.height(20.dp))


                BottomButtonComposable(
                    text = stringResource(Res.string.quiz_try_again),
                    onNextButtonClick = onTryAgainButtonClicked
                )

            }

        }
    }
}