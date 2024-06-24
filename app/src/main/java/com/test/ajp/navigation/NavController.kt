package com.test.ajp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.test.ajp.screens.Home.HomeScreen
import com.test.ajp.screens.Home.HomeViewModel
import com.test.ajp.screens.Kanji.KanjiScreen
import com.test.ajp.screens.Kanji.KanjiViewModel
import com.test.ajp.screens.KanjiList.KanjiListScreen
import com.test.ajp.screens.KanjiList.KanjiListViewModel
import com.test.ajp.screens.Splash.SplashScreen


sealed class Screen(
    val route: String
){
    data object Splash : Screen("splash")
    data object Home : Screen("home")
    data object KanjiList : Screen("kanjiList/{week}")
    data object Kanji : Screen("kanji/{week}/{kanjiID}")
    data object Media : Screen("media/{week}/{kanjiID}")
    data object Pdf : Screen("pdf/{week}/{kanjiID}")
    data object Tutorial : Screen("tutorial/{week}/{kanjiID}")
}

@Composable
fun MainNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination =  "main"){
        mainGraph(navController)
    }
}
private fun NavGraphBuilder.mainGraph(
    navController: NavHostController
){
    navigation(startDestination = Screen.Splash.route, route = "main"){
        composable(Screen.Splash.route){
            SplashScreen(gotoHome = {
                navController.navigate(route = Screen.Home.route)
            })
        }
        composable(route = Screen.Home.route){
            val viewModel = it.sharedViewModel<HomeViewModel>(navController)
            HomeScreen(viewModel = viewModel, gotoKanjiList = { week ->
                navController.navigate("kanjiList/$week")
            })
        }
        composable(Screen.KanjiList.route){
            val week = it.arguments?.getString("week") ?: "Week 1"
            val viewModel = it.sharedViewModel<KanjiListViewModel>(navController)
            KanjiListScreen(viewModel = viewModel, week = week, gotoKanjiScreen = { weekName, kanjiID ->
                navController.navigate("kanji/$weekName/$kanjiID")
            })
        }
        composable(Screen.Kanji.route){
            val week = it.arguments?.getString("week") ?: "Week 1"
            val kanjiID = it.arguments?.getString("kanjiID") ?: "A1"
            val viewModel = it.sharedViewModel<KanjiViewModel>(navController)
            KanjiScreen(navController = navController, goBack = {
                navController.popBackStack(Screen.KanjiList.route, inclusive = false)
            }, selectedItem = 0, viewModel = viewModel, week = week, id = kanjiID)
        }
        composable(Screen.Media.route){
            val week = it.arguments?.getString("week") ?: "Week 1"
            val kanjiID = it.arguments?.getString("kanjiID") ?: "A1"
            val viewModel = it.sharedViewModel<KanjiViewModel>(navController)
            KanjiScreen(navController = navController, goBack = {
                navController.popBackStack(Screen.KanjiList.route, inclusive = false)
            }, selectedItem = 1, viewModel = viewModel, week = week, id = kanjiID)
        }
        composable(Screen.Pdf.route){
            val week = it.arguments?.getString("week") ?: "Week 1"
            val kanjiID = it.arguments?.getString("kanjiID") ?: "A1"
            val viewModel = it.sharedViewModel<KanjiViewModel>(navController)
            KanjiScreen(navController = navController, goBack = {
                navController.popBackStack(Screen.KanjiList.route, inclusive = false)
            }, selectedItem = 2, viewModel = viewModel, week = week, id = kanjiID)
        }
        composable(Screen.Tutorial.route){
            val week = it.arguments?.getString("week") ?: "Week 1"
            val kanjiID = it.arguments?.getString("kanjiID") ?: "A1"
            val viewModel = it.sharedViewModel<KanjiViewModel>(navController)
            KanjiScreen(navController = navController, goBack = {
                navController.popBackStack(Screen.KanjiList.route, inclusive = false)
            }, selectedItem = 3, viewModel = viewModel, week = week, id = kanjiID)
        }
    }
}



@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavHostController): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}