package com.test.ajp.screens.Home

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ViewWeek
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.test.ajp.ui.theme.customColor1ContainerLight
import com.test.ajp.ui.theme.customColor2ContainerLight
import com.test.ajp.ui.theme.onBackgroundLight


@Composable
fun HomeScreen(viewModel: HomeViewModel, gotoKanjiList: (String) -> Unit) {
    val context = LocalContext.current as ComponentActivity
    BackHandler {
        context.finish()
    }
    HomeScreenSkeleton(
        viewModel = viewModel,
        onClick = gotoKanjiList
    )
}

@Composable
fun HomeScreenSkeleton(modifier: Modifier = Modifier, viewModel: HomeViewModel,onClick: (String) -> Unit) {
    LaunchedEffect(Unit) {
        viewModel.fetchWeeks()
    }
    val weeks by viewModel.weeks.observeAsState()

    if (weeks == null) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier.align(Alignment.Center), color = MaterialTheme.colorScheme.primary)
        }
    } else {
        Scaffold(containerColor = MaterialTheme.colorScheme.primaryContainer) { innerPadding ->
            LazyVerticalGrid(
                modifier = modifier.padding(innerPadding),
                columns = GridCells.Adaptive(190.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                items(weeks!!){ week ->
                    WeekCard(text = week, position = weeks!!.indexOf(week) , onClick = onClick)
                }
            }
        }
    }
}

@Composable
fun WeekCard(text: String, position: Int, onClick: (String) -> Unit) {
    val containerColor: Color = if (position % 2 == 0) {
        customColor1ContainerLight
    } else {
        customColor2ContainerLight
    }
    val textColor: Color = if (position % 2 == 0) {
        onBackgroundLight
    } else {
        onBackgroundLight
    }

    OutlinedCard(
        modifier = Modifier
            .size(150.dp)
            .padding(6.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = containerColor
        ),
        onClick = { onClick(text) }
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(modifier = Modifier.size(80.dp), imageVector = Icons.Filled.CalendarMonth, contentDescription = null)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge,
                color = textColor
            )
        }
    }
}