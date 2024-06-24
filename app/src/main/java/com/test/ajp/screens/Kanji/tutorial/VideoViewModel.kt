package com.test.ajp.screens.Kanji.tutorial

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class VideoViewModel : ViewModel() {
    private var _youTubePlayer: YouTubePlayer? = null
    private val _youTubePlayerView: MutableLiveData<YouTubePlayerView?> by lazy {
        MutableLiveData<YouTubePlayerView?>(null)
    }
    val youTubePlayerView: LiveData<YouTubePlayerView?>
        get() = _youTubePlayerView

    fun initializePlayer(context: Context, videoURL: String) {
        if (_youTubePlayerView.value == null) {
            val videoId = getVideoID(videoURL)
            _youTubePlayerView.value = YouTubePlayerView(context).apply {
                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        super.onReady(youTubePlayer)
                        _youTubePlayer = youTubePlayer
                        _youTubePlayer?.loadVideo(videoId, 0f)
                    }
                })
            }
        }
    }

    fun endPlayer() {
        _youTubePlayer = null
        _youTubePlayerView.value?.release()
        _youTubePlayerView.value = null
    }

    override fun onCleared() {
        super.onCleared()
        endPlayer()
    }

    private fun getVideoID(uri: String): String {
        return uri.substring(uri.lastIndexOf("=") + 1)
    }
}