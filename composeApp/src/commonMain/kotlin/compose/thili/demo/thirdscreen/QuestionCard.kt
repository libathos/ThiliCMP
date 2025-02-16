package compose.thili.demo.thirdscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringResource
import thilicmp.composeapp.generated.resources.Res
import thilicmp.composeapp.generated.resources.button_false
import thilicmp.composeapp.generated.resources.button_true

@Composable
fun QuestionCard(
    title: String,
    questionDescription: String,
    onFalseButtonClicked:() -> Unit,
    onTrueButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .background(Color(0xFFFFFFFF))
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
                    .padding(start = 10.dp, top = 30.dp, end = 10.dp, bottom = 10.dp),
                textAlign = TextAlign.Start,
                style = TextStyle(fontSize = 14.sp, color = Color.Black)
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = onFalseButtonClicked, modifier = Modifier.weight(1f).clip(
                        RoundedCornerShape(16.dp)
                    ).background(Color(0xFFFA5E73))

                ) {
                    Text(
                        text = stringResource(Res.string.button_false).uppercase(),
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))


                TextButton(
                    onClick = onTrueButtonClicked, modifier = Modifier.weight(1f).clip(
                        RoundedCornerShape(16.dp)
                    ).background(Color(0xFFFA5E73))
                ) {
                    Text(
                        text = stringResource(Res.string.button_true).uppercase(),
                        color = Color.White
                    )
                }
            }
        }
    }
}
