package com.test.ajp.screens.KanjiList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.ajp.models.KanjiData
import com.test.ajp.ui.theme.AppTypography
import com.test.ajp.ui.theme.customColor1ContainerDarkMediumContrast
import com.test.ajp.ui.theme.customColor2ContainerDark
import com.test.ajp.ui.theme.customColor2Dark
import com.test.ajp.ui.theme.onBackgroundLight
import com.test.ajp.ui.theme.onPrimaryContainerDarkHighContrast
import com.test.ajp.ui.theme.primaryContainerDarkHighContrast
import com.test.ajp.ui.theme.tertiaryContainerDarkHighContrast

@Composable
fun KanjiListScreen(
    viewModel: KanjiListViewModel,
    week: String,
    gotoKanjiScreen: (String, String) -> Unit
) {
    KanjiListScreenSkeleton(viewModel = viewModel, week = week, onClick = gotoKanjiScreen)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KanjiListScreenSkeleton(
    viewModel: KanjiListViewModel,
    modifier: Modifier = Modifier,
    week: String,
    onClick: (String, String) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.fetchKanjiList(week)
    }
    val kanjis by viewModel.kanjiList.observeAsState()
    if (kanjis == null) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.Blue
            )
        }
    } else {
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = week,
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
            }
        ) { ip ->
            LazyVerticalGrid(
                modifier = modifier.padding(ip),
                columns = GridCells.Adaptive(150.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(kanjis!!) { kanji ->
                    KanjiCard(
                        week = week,
                        kanji = kanji,
                        position = kanjis!!.indexOf(kanji),
                        onClick = onClick
                    )
                }
            }
        }
    }

}

@Composable
fun KanjiCard(
    week: String,
    kanji: KanjiData,
    position: Int,
    onClick: (String, String) -> Unit
) {
    val colorList = listOf(
        customColor2ContainerDark,
        tertiaryContainerDarkHighContrast,
        customColor2Dark,
        customColor1ContainerDarkMediumContrast
    )
    val containerColor: Color = colorList[position % 4]

    OutlinedCard(
        modifier = Modifier
            .size(150.dp)
            .padding(6.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = containerColor
        ),
        onClick = { onClick(week, kanji.id) }
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = kanji.kanji,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold,
                    color = onBackgroundLight,
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = kanji.word,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = onBackgroundLight,
                )
            )
        }
    }
}