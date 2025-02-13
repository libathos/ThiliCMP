package compose.thili.demo

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import compose.thili.demo.theme.ThiliTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MakeStatusBarTransparent()
            ThiliTheme {
                App()
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}

@Composable
fun Activity.MakeStatusBarTransparent() {
    window.apply {
        val systemUiController = rememberSystemUiController()
        systemUiController.setSystemBarsColor(
            color = Color.White, darkIcons = true
        )
    }
}