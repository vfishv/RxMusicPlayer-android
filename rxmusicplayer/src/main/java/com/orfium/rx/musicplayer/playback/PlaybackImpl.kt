package com.orfium.rx.musicplayer.playback

import android.content.Context
import android.media.AudioManager
import android.net.wifi.WifiManager
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.orfium.rx.musicplayer.R

internal class PlaybackImpl(
    context: Context,
    audioManager: AudioManager,
    wifiLock: WifiManager.WifiLock
) : BasePlayback(context, audioManager, wifiLock), Player.Listener {

    private var player = ExoPlayer.Builder(context).build()

    override val isPlaying: Boolean
        get() = player.playWhenReady

    override val position: Long
        get() = player.currentPosition

    override val duration: Long
        get() = player.duration

    override fun startPlayer() {
        play()
    }

    override fun pausePlayer() {
        player.playWhenReady = false
    }

    override fun stopPlayer() {
        player.release()
        player.removeListener(this)
        player.playWhenReady = false
    }

    override fun resumePlayer() {
        player.playWhenReady = true
    }

    override fun seekTo(position: Long) {
        player.seekTo(position)
    }

    var playWhenReady = false
    override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
        this.playWhenReady = playWhenReady
        super.onPlayWhenReadyChanged(playWhenReady, reason)
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        val playWhenReady = player.playWhenReady
        when (playbackState) {
            Player.STATE_IDLE -> playbackCallback?.onIdle()
            Player.STATE_READY -> if (playWhenReady) playbackCallback?.onPlay() else playbackCallback?.onPause()
            Player.STATE_BUFFERING -> if(playWhenReady) playbackCallback?.onBuffer() else playbackCallback?.onPause()
            Player.STATE_ENDED -> playbackCallback?.onCompletion()
        }
    }

    /*
    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        when (playbackState) {
            Player.STATE_IDLE -> playbackCallback?.onIdle()
            Player.STATE_READY -> if (playWhenReady) playbackCallback?.onPlay() else playbackCallback?.onPause()
            Player.STATE_BUFFERING -> if(playWhenReady) playbackCallback?.onBuffer() else playbackCallback?.onPause()
            Player.STATE_ENDED -> playbackCallback?.onCompletion()
        }
    }
    */

    override fun onPlayerError(error: PlaybackException) {
        playbackCallback?.onError()
    }

    private fun play() {
        /*
        val dataSourceFactory = DefaultDataSourceFactory(
            context, Util.getUserAgent(context, context.getString(R.string.app_name)), null
        )
        val mediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
            .setExtractorsFactory(DefaultExtractorsFactory())
            .createMediaSource(Uri.parse(currentMedia?.streamUrl))
        */

        /*
        val mediaSource = ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
            .createMediaSource(MediaItem.fromUri(Uri.parse(currentMedia?.streamUrl)))
            */
        //val mediaItem = MediaItem.fromUri(Uri.parse(currentMedia?.streamUrl))

        val mediaItem = MediaItem.Builder()
            .setUri(currentMedia?.streamUrl)
            .build()
        player.addListener(this)
        player.setMediaItem(mediaItem)
        //player.setMediaSource(mediaSource)
        player.prepare()
        player.playWhenReady = true
    }
}