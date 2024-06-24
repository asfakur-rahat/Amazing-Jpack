package com.test.ajp.screens.Kanji.details


import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PauseCircleFilled
import androidx.compose.material.icons.outlined.PlayCircleFilled
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.test.ajp.models.KanjiData
import com.test.ajp.ui.theme.AppTypography

@Composable
fun KanjiDetailsSkeleton(kanji: KanjiData, viewModel: AudioPlayerViewModel = viewModel()) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val isPlaying by viewModel.isPlaying.collectAsState()
    if (isLandscape) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = kanji.kanji,
                style = AppTypography.displayLarge.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 200.sp  
                ),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = kanji.word,
                style = AppTypography.headlineLarge.copy(
                    fontSize = 64.sp,
                    textAlign = TextAlign.Center
                ),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = kanji.meaning,
                style = AppTypography.headlineLarge.copy(
                    fontSize = 64.sp,
                    textAlign = TextAlign.Center
                )
            )
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(
                onClick = {
                    if (isPlaying) {
                        viewModel.pausePlayback()
                    } else {
                        val audio = kanji.audio
                        viewModel.startPlayback(audio)
                    }
                },
                modifier = Modifier
                    .size(48.dp)
            ) {
                if (isPlaying) {
                    Icon(
                        modifier = Modifier.size(48.dp),
                        imageVector = Icons.Outlined.PauseCircleFilled,
                        contentDescription = "Pause Video",
                        tint = Color.Red
                    )
                } else {
                    Icon(
                        modifier = Modifier.size(48.dp),
                        imageVector = Icons.Outlined.PlayCircleFilled,
                        contentDescription = "Pause Video",
                        tint = Color.Green
                    )
                }
            }
        }
    } else {
        Text(
            text = kanji.kanji,
            style = AppTypography.displayLarge.copy(
                textAlign = TextAlign.Center,
                fontSize = 220.sp
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = kanji.word,
            style = AppTypography.headlineLarge.copy(
                fontSize = 64.sp,
                textAlign = TextAlign.Center
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = kanji.meaning,
            style = AppTypography.headlineLarge.copy(
                fontSize = 64.sp,
                textAlign = TextAlign.Center
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        IconButton(
            onClick = {
                if (isPlaying) {
                    viewModel.pausePlayback()
                } else {
                    val audio = kanji.audio
                    viewModel.startPlayback(audio)
                }
            },
            modifier = Modifier
                .size(48.dp)
        ) {
            if (isPlaying) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    imageVector = Icons.Outlined.PauseCircleFilled,
                    contentDescription = "Pause Video",
                    tint = Color.Red
                )
            } else {
                Icon(
                    modifier = Modifier.size(48.dp),
                    imageVector = Icons.Outlined.PlayCircleFilled,
                    contentDescription = "Pause Video",
                    tint = Color.Green
                )
            }
        }
    }

}