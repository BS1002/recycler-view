package com.mahfuznow.recyclerview.view

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.*
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.mahfuznow.recyclerview.adapter.VideoAdapterDelegate
import com.mahfuznow.recyclerview.model.Video
import java.util.ArrayList

class VideoPlayerRecyclerView(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {

    companion object {
        private const val TAG = "VideoPlayerRecyclerView"
    }

    private enum class VolumeState {
        ON, OFF
    }

    // UI (these will be initialized later from the viewHolder of targeted video position
    private var thumbnail: ImageView? = null
    private var volumeControl: ImageView? = null
    private var progressBar: ProgressBar? = null
    private var viewHolderParent: View? = null
    private var frameLayout: FrameLayout? = null

    //Exoplayer
    private var playerView: PlayerView = PlayerView(context)
    private var videoPlayer: SimpleExoPlayer

    // vars
    private var listItems: List<Any> = ArrayList<Any>()
    private var playPosition = -1
    private var isVideoViewAdded = false
    private var lastPlayedVideoIndex: Int? = null

    // Initial Volume state is ON
    private var volumeState: VolumeState = VolumeState.ON

    //Video list must be provided to this class
    fun setListItems(items: List<Any>) {
        this.listItems = items
    }

    init {

        val bandwidthMeter: BandwidthMeter = DefaultBandwidthMeter()
        val videoTrackSelectionFactory: TrackSelection.Factory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        val trackSelector: TrackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
        // Create the player
        videoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector)

        // Setting up the playerView
        playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
        // Bind the player to the view.
        playerView.player = videoPlayer
        // Setting weather to show Video Controller UI (seekbar, play-pause, next, forward... etc.)
        playerView.useController = true


        //Listeners for RecyclerView Scrolling
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //We must wait until the scrolling finishes (in idle state), otherwise it causes crash
                if (newState == SCROLL_STATE_IDLE) {
                    //save old video's SeekPosition
                    if (lastPlayedVideoIndex != null) {
                        saveVideoSeekPosition(lastPlayedVideoIndex!!)
                    }

                    // There's a special case when the end of the list has been reached.
                    // Need to handle that with this bit of logic
                    if (!recyclerView.canScrollVertically(1)) {
                        playVideo(true)
                    } else {
                        playVideo(false)
                    }
                }
            }

        })

        //Listener for the videoPlayer
        videoPlayer.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when (playbackState) {
                    Player.STATE_BUFFERING -> {
                        Log.e(TAG, "onPlayerStateChanged: Buffering video.")
                        //show circular progressbar
                        progressBar?.visibility = VISIBLE
                        progressBar?.bringToFront()
                    }
                    Player.STATE_ENDED -> {
                        Log.d(TAG, "onPlayerStateChanged: Video ended.")
                        //replay
                        videoPlayer.seekTo(0)
                    }
                    Player.STATE_IDLE -> {}
                    Player.STATE_READY -> {
                        Log.e(TAG, "onPlayerStateChanged: Ready to play.")
                        progressBar?.visibility = GONE
                        if (!isVideoViewAdded) {
                            addVideoView()
                        }
                    }
                    else -> {}
                }
            }

            // The following overridden methods are empty
            override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {}
            override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {}
            override fun onLoadingChanged(isLoading: Boolean) {}
            override fun onRepeatModeChanged(repeatMode: Int) {}
            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {}
            override fun onPlayerError(error: ExoPlaybackException?) {}
            override fun onPositionDiscontinuity(reason: Int) {}
            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {}
            override fun onSeekProcessed() {}
        })
    }


    fun playVideo(isEndOfList: Boolean) {

        //finding the target position of the video which should be played
        val firstCompletelyVisibleItemPosition = (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
        val lastCompletelyVisibleItemPosition = (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
        var targetPosition = 0
        if (!isEndOfList) {
            for (i in firstCompletelyVisibleItemPosition..lastCompletelyVisibleItemPosition) {
                if (listItems[i] is Video) {
                    targetPosition = i
                    break
                }
            }
        } else {
            for (i in (listItems.size - 1) downTo firstCompletelyVisibleItemPosition) {
                if (listItems[i] is Video) {
                    targetPosition = i
                    break
                }
            }
        }

        Log.d(TAG, "playVideo: target position: $targetPosition")

        // video is already playing so return
        if (targetPosition == playPosition) {
            return
        }

        // set the position of the list-item that is to be played
        playPosition = targetPosition

        // remove previously playing video's player views,progressbar and restore that's thumbnail
        removeVideoView(playerView)
        progressBar?.visibility = View.GONE
        playerView.visibility = View.GONE
        thumbnail?.visibility = View.VISIBLE


        if (listItems[targetPosition] is Video) {
            //Finding the view holder of this position
            // IMPORTANT: In the ViewHolder class tag must be provided (itemView.tag = this)
            var positionInVisibleScreen = targetPosition - (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            Log.d(TAG, "playVideo: positionInVisibleScreen: $positionInVisibleScreen")
            //This is a safety check to prevent crash for negative position
            if(positionInVisibleScreen < 0)
                positionInVisibleScreen = 0
            val child = getChildAt(positionInVisibleScreen)
            val holder = child.tag as VideoAdapterDelegate.VideoViewHolder
            thumbnail = holder.thumbnail
            progressBar = holder.progressBar
            volumeControl = holder.volumeControl
            viewHolderParent = holder.itemView
            frameLayout = holder.frameLayout

            //Handling onClick Event in the video item
            // Click on the Title or description of the video to toggle volume
            viewHolderParent?.setOnClickListener {
                toggleVolume()
            }

            //Setting the new video
            val video = listItems[targetPosition] as Video
            val url = video.url
            val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(context, Util.getUserAgent(context, "AppName"))
            val videoSource: MediaSource = ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(url))
            videoPlayer.prepare(videoSource)

            //If video was played previously, start playing from that seekPosition
            val storedSeekPosition = video.seekPosition
            if (storedSeekPosition != null) {
                videoPlayer.seekTo(storedSeekPosition)
            }

            playerView.player = videoPlayer
            //This will not immediately playing video, it will play when the videoPlayer fetches a portion of the video and
            //when it's ready to play.
            videoPlayer.playWhenReady = true

            //Showing circular progressbar until player is ready to play.
            // "onPlayerStateChanged" handles the actions after the videoPlayer is being ready
            progressBar?.visibility = View.VISIBLE

            //saving index of last played video
            lastPlayedVideoIndex = targetPosition
        }
    }


    // Remove the old player
    private fun removeVideoView(playerView: PlayerView) {
        val parent = playerView.parent //getting the FrameLayout of the last video
        if (parent != null) {
            parent as ViewGroup // or we can use "parent as FrameLayout"
            val index = parent.indexOfChild(playerView)
            if (index >= 0) {
                parent.removeViewAt(index)
                isVideoViewAdded = false
                viewHolderParent?.setOnClickListener(null)
            }
        }
    }

    //Adding a Player view on the Frame Layout
    private fun addVideoView() {
        frameLayout?.addView(playerView)
        isVideoViewAdded = true
        playerView.requestFocus()
        playerView.visibility = VISIBLE
        playerView.alpha = 1f
        thumbnail?.visibility = GONE
    }

    private fun saveVideoSeekPosition(index: Int) {
        if (listItems[index] is Video) {
            (listItems[index] as Video).seekPosition = videoPlayer.currentPosition
        }
    }

    private fun toggleVolume() {
        if (volumeState == VolumeState.OFF) {
            Log.d(TAG, "togglePlaybackState: enabling volume.")
            setVolumeControl(VolumeState.ON)
        } else if (volumeState == VolumeState.ON) {
            Log.d(TAG, "togglePlaybackState: disabling volume.")
            setVolumeControl(VolumeState.OFF)
        }
    }

    private fun setVolumeControl(state: VolumeState) {
        volumeState = state
        if (state == VolumeState.OFF) {
            videoPlayer.volume = 0f
            animateVolumeControl()
        } else if (state == VolumeState.ON) {
            videoPlayer.volume = 1f
            animateVolumeControl()
        }
    }

    private fun animateVolumeControl() {
        if (volumeControl != null) {
            volumeControl!!.bringToFront()
            if (volumeState == VolumeState.OFF) {
                volumeControl!!.setImageDrawable(ContextCompat.getDrawable(context, com.mahfuznow.recyclerview.R.drawable.ic_volume_off))
            } else if (volumeState == VolumeState.ON) {
                volumeControl!!.setImageDrawable(ContextCompat.getDrawable(context, com.mahfuznow.recyclerview.R.drawable.ic_volume_on))
            }
            volumeControl!!.animate().cancel()
            volumeControl!!.alpha = 1f
            volumeControl!!.animate()
                .alpha(0f)
                .setDuration(600)
                .startDelay = 1000
        }
    }


    fun pausePlayer() {
        videoPlayer.playWhenReady = false
    }

    fun resumePlayer() {
        videoPlayer.playWhenReady = true
    }

    fun releasePlayer() {
        videoPlayer.release()
        viewHolderParent = null
    }
}