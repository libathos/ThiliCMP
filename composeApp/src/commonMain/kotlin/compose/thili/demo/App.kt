package compose.thili.demo

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import compose.thili.demo.firstscreen.FirstScreen
import compose.thili.demo.fourthscreen.FourthScreen
import compose.thili.demo.secondscreen.SecondScreen
import compose.thili.demo.thirdscreen.ThirdScreen
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import thilicmp.composeapp.generated.resources.Res
import thilicmp.composeapp.generated.resources.actionbar_menu
import thilicmp.composeapp.generated.resources.menu_fifth
import thilicmp.composeapp.generated.resources.menu_fourth
import thilicmp.composeapp.generated.resources.menu_second
import thilicmp.composeapp.generated.resources.menu_third


enum class ThiliScreens(val title: StringResource?) {
    WelcomeScreen(null),
    MainHubScreen(Res.string.actionbar_menu),
    ScheduleAppointmentSelfExamScreen(Res.string.menu_second),
    ScheduleAppointmentSelfClinicalExamScreen(Res.string.menu_third),
    ScheduleAppointmentSelfMammogramScreen(Res.string.menu_fourth),
    QuizScreen(Res.string.menu_fifth)

}

@Composable
@Preview
fun App(navController: NavHostController = rememberNavController()) {


    NavHost(navController = navController,
        startDestination = ThiliScreens.WelcomeScreen.name,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(700)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(700)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(700)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(700)
            )
        }) {

        composable(route = ThiliScreens.WelcomeScreen.name) {
            FirstScreen(onNextButtonClick = { navController.navigate(ThiliScreens.MainHubScreen.name) })
        }

        composable(route = ThiliScreens.MainHubScreen.name) {
            SecondScreen(canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                onQuizSelected = { navController.navigate(ThiliScreens.QuizScreen.name) },
                onScheduleAppointmentForSelfExamSelected = { navController.navigate(ThiliScreens.ScheduleAppointmentSelfExamScreen.name) },
                onScheduleAppointmentForClinicalExamSelected = { navController.navigate(ThiliScreens.ScheduleAppointmentSelfClinicalExamScreen.name) },
                onScheduleAppointmentForMammogramSelected = { navController.navigate(ThiliScreens.ScheduleAppointmentSelfMammogramScreen.name) }
            )
        }

        composable(route = ThiliScreens.QuizScreen.name) {
            ThirdScreen(canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() })
        }

        composable(route = ThiliScreens.ScheduleAppointmentSelfExamScreen.name) {
            FourthScreen(currentScreen = ThiliScreens.ScheduleAppointmentSelfExamScreen,canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                {})
        }

        composable(route = ThiliScreens.ScheduleAppointmentSelfClinicalExamScreen.name) {
            FourthScreen(currentScreen = ThiliScreens.ScheduleAppointmentSelfClinicalExamScreen,canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                {})
        }

        composable(route = ThiliScreens.ScheduleAppointmentSelfMammogramScreen.name) {
            FourthScreen(currentScreen = ThiliScreens.ScheduleAppointmentSelfMammogramScreen, canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                {})
        }

    }
}
