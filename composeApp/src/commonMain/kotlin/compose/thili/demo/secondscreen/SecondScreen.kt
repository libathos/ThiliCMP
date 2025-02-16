package compose.thili.demo.secondscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.thili.demo.ThiliScreens
import compose.thili.demo.ui.components.ThiliAppBar
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import thilicmp.composeapp.generated.resources.Res
import thilicmp.composeapp.generated.resources.back
import thilicmp.composeapp.generated.resources.calendar
import thilicmp.composeapp.generated.resources.fb
import thilicmp.composeapp.generated.resources.help
import thilicmp.composeapp.generated.resources.info
import thilicmp.composeapp.generated.resources.mail
import thilicmp.composeapp.generated.resources.menu_eighth
import thilicmp.composeapp.generated.resources.menu_fifth
import thilicmp.composeapp.generated.resources.menu_first
import thilicmp.composeapp.generated.resources.menu_fourth
import thilicmp.composeapp.generated.resources.menu_ninth
import thilicmp.composeapp.generated.resources.menu_second
import thilicmp.composeapp.generated.resources.menu_seventh
import thilicmp.composeapp.generated.resources.menu_sixth
import thilicmp.composeapp.generated.resources.menu_third
import thilicmp.composeapp.generated.resources.tick
import thilicmp.composeapp.generated.resources.tick2
import thilicmp.composeapp.generated.resources.user
import thilicmp.composeapp.generated.resources.woman


@Composable
fun SecondScreen(canNavigateBack: Boolean, navigateUp: () -> Unit, onQuizSelected: () -> Unit) {

    Scaffold(topBar = {
        ThiliAppBar(
            canNavigateBack = canNavigateBack,
            navigateUp = navigateUp,
            currentScreen = ThiliScreens.MainHubScreen
        )
    }) { innerPadding ->


        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {

            Box(
                modifier = Modifier.matchParentSize().paint(
                    painterResource(Res.drawable.back), contentScale = ContentScale.FillBounds
                )
            ) {


                Box(Modifier.padding(20.dp)) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .wrapContentHeight(Alignment.CenterVertically)
                            .border(2.dp, Color(0xFFFA5E73)).align(Alignment.TopCenter)
                            .background(Color.White), contentAlignment = Alignment.CenterStart
                    ) {
                        Column(
                            Modifier.padding(
                                start = 20.dp, top = 15.dp, end = 10.dp, bottom = 15.dp
                            )
                        ) {
                            StartTextWithStartIcon(
                                stringResource(Res.string.menu_first),
                                true,
                                painterResource(Res.drawable.tick)
                            )

                            StartTextWithStartIcon(
                                stringResource(Res.string.menu_second),
                                true,
                                painterResource(Res.drawable.woman)
                            )

                            StartTextWithStartIcon(
                                stringResource(Res.string.menu_third),
                                true,
                                painterResource(Res.drawable.info)
                            )

                            StartTextWithStartIcon(
                                stringResource(Res.string.menu_fourth),
                                true,
                                painterResource(Res.drawable.tick2)
                            )

                            StartTextWithStartIcon(
                                stringResource(Res.string.menu_fifth),
                                true,
                                painterResource(Res.drawable.help),
                                onSelected = onQuizSelected
                            )

                            StartTextWithStartIcon(
                                stringResource(Res.string.menu_sixth),
                                true,
                                painterResource(Res.drawable.calendar)
                            )

                            StartTextWithStartIcon(
                                stringResource(Res.string.menu_seventh),
                                true,
                                painterResource(Res.drawable.user)
                            )

                            StartTextWithStartIcon(
                                stringResource(Res.string.menu_eighth),
                                true,
                                painterResource(Res.drawable.fb)
                            )

                            StartTextWithStartIcon(
                                stringResource(Res.string.menu_ninth),
                                false,
                                painterResource(Res.drawable.mail)
                            )
                        }
                    }
                }
            }
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartTextWithStartIcon(text: String, isVisible: Boolean, icon: Painter, onSelected: () -> Unit = {}) {

    var isSelected by remember { mutableStateOf(false) }

    val textColor = if (isSelected) Color.White else Color.Black
    val backgroundColor = if (isSelected) Color.LightGray else Color.Transparent


    Row(
        modifier = Modifier.background(backgroundColor).fillMaxWidth().height(60.dp)
            .pointerInput(Unit) {
                detectTapGestures(onPress = {
                    try {
                        isSelected = true
                        awaitRelease()
                    } finally {
                        isSelected = false
                    }
                },
                    onTap = {onSelected.invoke()}


                )
            }, verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = icon, contentDescription = "", tint = null
        )

        Spacer(modifier = Modifier.size(15.dp))
        Text(
            text = text, style = TextStyle(fontSize = 16.sp), color = textColor
        )
    }

    if (isVisible) {
        Spacer(
            modifier = Modifier.fillMaxWidth().padding(start = 35.dp, end = 2.dp).height(2.dp)
                .background(Color(0xFFc7d2e8))
        )
    }

}


