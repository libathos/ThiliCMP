package compose.thili.demo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringResource
import thilicmp.composeapp.generated.resources.Res
import thilicmp.composeapp.generated.resources.continue_button

@Composable
fun BottomButtonComposable(text: String? = null, onNextButtonClick: () -> Unit) {

    TextButton(
        onClick = {
            onNextButtonClick()
        },
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFFA5E73))

    ) {
        Text(
            modifier = Modifier.padding(top = 12.dp, bottom = 12.dp), style = TextStyle(
                fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White
            ), text = (text ?: stringResource(Res.string.continue_button)).uppercase()
        )
    }
}