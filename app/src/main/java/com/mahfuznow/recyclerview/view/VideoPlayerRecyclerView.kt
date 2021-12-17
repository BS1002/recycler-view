package com.mahfuznow.recyclerview.view

import android.content.Context
import android.graphics.Point
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
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
import com.mahfuznow.recyclerview.adapter.VideoListAdapter
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
    private var playerView: PlayerView
    private var videoPlayer: SimpleExoPlayer

    // vars
    private var videos: List<Video> = ArrayList<Video>()
    private var videoSurfaceDefaultHeight = 0
    private var screenDefaultHeight = 0
    private var playPosition = -1
    private var isVideoViewAdded = false

    // Initial Volume state is ON
    private var volumeState: VolumeState = VolumeState.ON

    //Video list must be provided to this class
    fun setVideoList(videos: List<Video>) {
        this.videos = videos
    }

    init {
        // getting the display measures
        // Screen Height = Device height, Video Height = Device Width
        // This is required to determine which video is taking the maximum area in the display (targeted video)
        val display = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        val point = Point()
        display.getSize(point)
        videoSurfaceDefaultHeight = point.x
        screenDefaultHeight = point.y

        // Setting up the playerView
        playerView = PlayerView(context)
        playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
        val bandwidthMeter: BandwidthMeter = DefaultBandwidthMeter()
        val videoTrackSelectionFactory: TrackSelection.Factory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        val trackSelector: TrackSelector = DefaultTrackSelector(videoTrackSelectionFactory)

        // Create the player
        videoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector)
        // Bind the player to the view.
        playerView.player = videoPlayer
        // Setting weather to show Video Controller UI (seekbar, play-pause, next, forward... etc.)
        playerView.useController = false


        //Listeners for RecyclerView
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //We must wait until the scrolling finishes (in idle state), otherwise it causes crash
                if (newState == SCROLL_STATE_IDLE) {
                    // show the old thumbnail if it's not null
                    thumbnail?.visibility = VISIBLE

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
        addOnChildAttachStateChangeListener(object : OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(view: View) {}

            //remove the playerView as soon as the item is scrolled away from the visible area(detached)
            override fun onChildViewDetachedFromWindow(view: View) {
                if (viewHolderParent != null && viewHolderParent == view) {
                    resetVideoView()
                }
            }
        })

        //Listener for the videoPlayer
        videoPlayer.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when (playbackState) {
                    Player.STATE_BUFFERING -> {
                        Log.e(TAG, "onPlayerStateChanged: Buffering video.")
                        progressBar?.visibility = VISIBLE
                    }
                    Player.STATE_ENDED -> {
                        Log.d(TAG, "onPlayerStateChanged: Video ended.")
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
        val targetPosition: Int
        if (!isEndOfList) {
            //finding the target position on which video should be played
            val startPosition = (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            var endPosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

            // if there is more than 2 list-items on the screen, set the difference to be 1
            if (endPosition - startPosition > 1) {
                endPosition = startPosition + 1
            }

            // something is wrong. return.
            if (startPosition < 0 || endPosition < 0) {
                return
            }

            // if there is more than 1 list-item on the screen
            targetPosition = if (startPosition != endPosition) {
                val startPositionVideoHeight = getVisibleVideoSurfaceHeight(startPosition)
                val endPositionVideoHeight = getVisibleVideoSurfaceHeight(endPosition)
                if (startPositionVideoHeight > endPositionVideoHeight) startPosition else endPosition
            } else {
                startPosition
            }
        } else {
            targetPosition = videos.size - 1
        }

        Log.d(TAG, "playVideo: target position: $targetPosition")

        // video is already playing so return
        if (targetPosition == playPosition) {
            return
        }

        // set the position of the list-item that is to be played
        playPosition = targetPosition

        // remove any old player views from previously playing videos
        playerView.visibility = INVISIBLE
        removeVideoView(playerView)

        //Finding the view holder at the targeted position
        // IMPORTANT: In the ViewHolder class tag must be provided (itemView.tag = this)
        val currentPosition = targetPosition - (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        val child = getChildAt(currentPosition)
        val holder = child.tag as VideoListAdapter.VideoViewHolder
        thumbnail = holder.thumbnail
        progressBar = holder.progressBar
        volumeControl = holder.volumeControl
        viewHolderParent = holder.itemView
        frameLayout = holder.frameLayout
        playerView.player = videoPlayer

        //Handling onClick Event in the video item
        viewHolderParent?.setOnClickListener {
            toggleVolume()
        }

        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(context, Util.getUserAgent(context, "AppName"))
        val mediaUrl = videos[targetPosition].url
        val videoSource: MediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse(mediaUrl))
        videoPlayer.prepare(videoSource)
        videoPlayer.playWhenReady = true

        thumbnail?.visibility = INVISIBLE
    }


    /*
     * Returns the visible region of the video surface on the screen.
     * if some is cut off, it will return less than the @videoSurfaceDefaultHeight
     * @param playPosition
     * @return
     */
    private fun getVisibleVideoSurfaceHeight(playPosition: Int): Int {
        val at = playPosition - (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        Log.d(TAG, "getVisibleVideoSurfaceHeight: at: $at")
        val child = getChildAt(at) ?: return 0
        val location = IntArray(2)
        child.getLocationInWindow(location)
        return if (location[1] < 0) {
            location[1] + videoSurfaceDefaultHeight
        } else {
            screenDefaultHeight - location[1]
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

    private fun addVideoView() {
        frameLayout?.addView(playerView)
        isVideoViewAdded = true
        playerView.requestFocus()
        playerView.visibility = VISIBLE
        playerView.alpha = 1f
        thumbnail?.visibility = GONE
    }

    private fun resetVideoView() {
        if (isVideoViewAdded) {
            removeVideoView(playerView)
            playPosition = -1
            playerView.visibility = INVISIBLE
            thumbnail?.visibility = VISIBLE
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