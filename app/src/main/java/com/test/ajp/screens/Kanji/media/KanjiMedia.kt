package com.test.ajp.screens.Kanji.media

import android.content.res.Configuration
import android.net.Uri
import android.widget.VideoView
import androidx.compose.foundation.border
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.test.ajp.models.KanjiData

@Composable
fun KanjiMediaSkeleton(kanji: KanjiData, viewModel: MediaViewModel = viewModel()) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val videoScreen = remember {
        VideoView(context)
    }
    val isPlaying by viewModel.isPlaying.observeAsState()
    if (isLandscape) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AndroidView(
                factory = {
                    videoScreen.apply{
                        setVideoURI(Uri.parse(kanji.video))
                        setMediaController(null)
                        setOnCompletionListener {
                            viewModel.onCompletion()
                        }
                    }
                },
                update = { screen ->
                    when(isPlaying) {
                        true -> screen.start()
                        false -> screen.pause()
                        null -> TODO()
                    }
                },
                modifier = Modifier.size(360.dp).border(4.dp, MaterialTheme.colorScheme.onPrimary)
            )
            Spacer(modifier = Modifier.width(24.dp))
            IconButton(
                onClick = {
                    if (isPlaying == true) {
                        viewModel.pause()
                    } else {
                        viewModel.play()
                    }
                },
                modifier = Modifier.size(64.dp)
            ) {
                if (isPlaying == true) {
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
                        contentDescription = "Play Video",
                        tint = Color.Green
                    )
                }
            }
        }
    } else {
        AndroidView(
            factory = {
                videoScreen.apply {
                    setVideoURI(Uri.parse(kanji.video))
                    setMediaController(null)
                    setOnCompletionListener {
                        viewModel.onCompletion()
                    }
                }
            },
            update = { screen ->
                when (isPlaying) {
                    true -> screen.start()
                    false -> screen.pause()
                    null -> TODO()
                }
            },
            modifier = Modifier
                .size(360.dp)
                .border(4.dp, MaterialTheme.colorScheme.onPrimary)
        )
        Spacer(modifier = Modifier.height(24.dp))
        IconButton(
            onClick = {
                if (isPlaying == true) {
                    viewModel.pause()
                } else {
                    viewModel.play()
                }
            },
            modifier = Modifier.size(64.dp)
        ) {
            if (isPlaying == true) {
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
                    contentDescription = "Play Video",
                    tint = Color.Green
                )
            }
        }
    }
}