package compose.thili.demo.firstscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import thilicmp.composeapp.generated.resources.welcome
import thilicmp.composeapp.generated.resources.welcome_message_first
import thilicmp.composeapp.generated.resources.welcome_message_fourth
import thilicmp.composeapp.generated.resources.welcome_message_second
import thilicmp.composeapp.generated.resources.welcome_message_third


@Composable
fun FirstScreen(
    onNextButtonClick: () -> Unit
) {
    Column(Modifier.fillMaxSize().padding(20.dp).background(Color.White)) {
        Spacer(modifier = Modifier.size(20.dp))

        Box(
            modifier = Modifier.fillMaxWidth().border(2.dp, Color(0xFFFA5E73))
                .padding(8.dp), contentAlignment = Alignment.CenterStart
        ) {
            Text(
                style = TextStyle(
                    fontSize = 18.sp, color = Color(0xFF282a2e), fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Start,
                text = stringResource(Res.string.welcome),
            )
        }

        Spacer(modifier = Modifier.size(20.dp))

        Box(
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically)
                .border(2.dp, Color(0xFFFA5E73))
                .padding(8.dp),
            contentAlignment = Alignment.CenterStart
        ) {

            Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                WelcomeTextComposable(stringResource(Res.string.welcome_message_first))
                WelcomeTextComposable(stringResource(Res.string.welcome_message_second))
                WelcomeTextComposable(stringResource(Res.string.welcome_message_third))
                WelcomeTextComposable(stringResource(Res.string.welcome_message_fourth))

                Spacer(modifier = Modifier.size(50.dp))

                BottomButtonComposable(onNextButtonClick = onNextButtonClick)

                Spacer(modifier = Modifier.size(50.dp))


            }
        }


    }


}

@Composable
private fun WelcomeTextComposable(title: String) {
    Text(
        style = TextStyle(
            fontWeight = FontWeight.Normal, fontSize = 18.sp, lineHeight = 20.sp
        ),
        textAlign = TextAlign.Justify,
        text = title,
    )

    Spacer(modifier = Modifier.size(10.dp))
}