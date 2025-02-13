package compose.thili.demo

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import compose.thili.demo.firstscreen.FirstScreen
import compose.thili.demo.secondscreen.SecondScreen
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import thilicmp.composeapp.generated.resources.Res
import thilicmp.composeapp.generated.resources.actionbar_menu


enum class ThiliScreens(val title: StringResource?) {
    WelcomeScreen(null), MainHubScreen(Res.string.actionbar_menu)
}

@Composable
@Preview
fun App(navController: NavHostController = rememberNavController()) {


    NavHost(
        navController = navController, startDestination = ThiliScreens.WelcomeScreen.name
    ) {

        composable(route = ThiliScreens.WelcomeScreen.name) {
            FirstScreen(onNextButtonClick = { navController.navigate(ThiliScreens.MainHubScreen.name) })
        }

        composable(route = ThiliScreens.MainHubScreen.name) {
            SecondScreen(
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() })
        }

    }
}
