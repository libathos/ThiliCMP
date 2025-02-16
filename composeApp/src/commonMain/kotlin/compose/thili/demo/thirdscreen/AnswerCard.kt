package compose.thili.demo.thirdscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.thili.demo.ui.components.BottomButtonComposable
import org.jetbrains.compose.resources.stringResource
import thilicmp.composeapp.generated.resources.Res
import thilicmp.composeapp.generated.resources.button_next


@Composable
fun AnswerCard(
    title: String,
    questionDescription: String,
    answerStatus: String,
    answerStatusColor: Color,
    explanation: String,
    onNextButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()).padding(8.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = title,
                    modifier = Modifier.fillMaxWidth().padding(start = 10.dp, top = 15.dp),
                    textAlign = TextAlign.Start,
                    style = TextStyle(fontSize = 16.sp, color = Color.Black)
                )

                Text(
                    text = questionDescription,
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 10.dp, top = 15.dp, end = 10.dp),
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = answerStatus,
                    modifier = Modifier.fillMaxWidth().padding(start = 10.dp, top = 15.dp),
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontSize = 14.sp, color = answerStatusColor, fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = explanation,
                    modifier = Modifier.fillMaxWidth().padding(15.dp),
                    textAlign = TextAlign.Start,
                    style = TextStyle(fontSize = 14.sp, color = Color.Black)
                )

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BottomButtonComposable(
                        text = stringResource(Res.string.button_next),
                        onNextButtonClick = onNextButtonClicked)
                }
            }
        }
    }
}