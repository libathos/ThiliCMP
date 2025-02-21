package compose.thili.demo.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.thili.demo.ThiliScreens
import compose.thili.demo.getPlatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import thilicmp.composeapp.generated.resources.Res
import thilicmp.composeapp.generated.resources.ic_launcher_foreground
import thilicmp.composeapp.generated.resources.ic_launcher_foreground_toolbar
import thilicmp.composeapp.generated.resources.quiz_disclaimer
import thilicmp.composeapp.generated.resources.quiz_disclaimer2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThiliAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    currentScreen: ThiliScreens
) {
    TopAppBar(title = { CenteredTextWithEndIcon(currentScreen) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = ""
                    )
                }
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenteredTextWithEndIcon(currentScreen: ThiliScreens) {
    currentScreen.title?.let {
        Box(
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text(
                stringResource(currentScreen.title),
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                color = Color.Black
            )

            Icon(
                painter = if (getPlatform().name.contains("Java")) painterResource(Res.drawable.ic_launcher_foreground_toolbar)
                else painterResource(Res.drawable.ic_launcher_foreground),
                contentDescription = "App Icon",
                modifier = Modifier.align(Alignment.CenterEnd),
                tint = null
            )
        }
    }
}
