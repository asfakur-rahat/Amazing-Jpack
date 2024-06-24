package com.test.ajp.screens.Kanji

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Draw
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Draw
import androidx.compose.material.icons.outlined.PictureAsPdf
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.test.ajp.navigation.Screen
import com.test.ajp.screens.Kanji.details.KanjiDetailsSkeleton
import com.test.ajp.screens.Kanji.media.KanjiMediaSkeleton
import com.test.ajp.screens.Kanji.pdf.KanjiPdfSkeleton
import com.test.ajp.screens.Kanji.tutorial.KanjiTutorialSkeleton
import com.test.ajp.ui.theme.AppTypography
import com.test.ajp.ui.theme.onPrimaryContainerDarkHighContrast
import com.test.ajp.ui.theme.primaryContainerDarkHighContrast

@Composable
fun KanjiScreen(
    viewModel: KanjiViewModel,
    week: String,
    id: String,
    navController: NavHostController,
    goBack: () -> Unit,
    selectedItem: Int
) {
    BackHandler(onBack = goBack)
    KanjiScreenSkeleton(
        viewModel = viewModel,
        week = week,
        id = id,
        navController = navController,
        selectedItem = selectedItem
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KanjiScreenSkeleton(
    modifier: Modifier = Modifier,
    viewModel: KanjiViewModel,
    week: String = "Week 1",
    id: String = "A1",
    navController: NavHostController,
    selectedItem: Int
) {
    LaunchedEffect(Unit) {
        viewModel.fetchKanjiDetails(week, id)
    }
    val kanji by viewModel.kanjiDetails.observeAsState()
    if (kanji == null) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.Blue
            )
        }
    } else {
        Scaffold(modifier = modifier.fillMaxSize(),
            topBar = {
                val text = when (navController.currentDestination?.route) {
                    Screen.Kanji.route -> "Kanji Details"

                    Screen.Media.route -> "Stroke Order"

                    Screen.Pdf.route -> "Pdf Example & Exercise"

                    Screen.Tutorial.route -> "YouTube Video Tutorial"
                    else -> "Nothing"
                }
                TopAppBar(
                    title = {
                        Text(
                            text = text,
                            style = AppTypography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 26.sp,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = primaryContainerDarkHighContrast,
                        titleContentColor = onPrimaryContainerDarkHighContrast
                    )
                )
            },
            bottomBar = {
                BottomBar(navController = navController, selectedItem, week = week, kanjiID = id)
            }) { ip ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(ip),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (navController.currentDestination?.route) {
                        Screen.Kanji.route -> KanjiDetailsSkeleton(kanji = kanji!!)

                        Screen.Media.route -> KanjiMediaSkeleton(kanji = kanji!!)

                        Screen.Pdf.route -> KanjiPdfSkeleton(kanji = kanji!!)

                        Screen.Tutorial.route -> KanjiTutorialSkeleton(kanji = kanji!!)
                    }
                }
            }

        }
    }

}

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val screen: String
)

@Composable
fun BottomBar(
    navController: NavHostController,
    selectedItem: Int = 0,
    week: String = "Week 1",
    kanjiID: String = "A1"
) {
    val items = listOf(
        NavigationItem(
            title = "Details",
            selectedIcon = Icons.Filled.Dashboard,
            unselectedIcon = Icons.Outlined.Dashboard,
            screen = "kanji"
        ),
        NavigationItem(
            title = "Media",
            selectedIcon = Icons.Filled.Draw,
            unselectedIcon = Icons.Outlined.Draw,
            screen = "media"
        ),
        NavigationItem(
            title = "PDF",
            selectedIcon = Icons.Filled.PictureAsPdf,
            unselectedIcon = Icons.Outlined.PictureAsPdf,
            screen = "pdf"
        ),
        NavigationItem(
            title = "Tutorials",
            selectedIcon = Icons.Filled.VideoLibrary,
            unselectedIcon = Icons.Outlined.VideoLibrary,
            screen = "tutorial"
        )
    )
    var selectedIndex by rememberSaveable { mutableStateOf(selectedItem) }
    NavigationBar(
        containerColor = primaryContainerDarkHighContrast,
        contentColor = onPrimaryContainerDarkHighContrast
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {
                    selectedIndex = index
                    println(item.screen)
                    navController.navigate(item.screen + "/$week/$kanjiID") {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (index == selectedIndex) item.selectedIcon else item.unselectedIcon,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        item.title,
                        style = AppTypography.labelMedium.copy(fontWeight = FontWeight.Bold)
                    )
                })
        }
    }
}