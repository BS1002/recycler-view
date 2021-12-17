package com.mahfuznow.recyclerview.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahfuznow.recyclerview.R
import com.mahfuznow.recyclerview.adapter.VideoListAdapter
import com.mahfuznow.recyclerview.model.Video

class VideoActivity : AppCompatActivity() {

    private lateinit var videoPlayerRecyclerView: VideoPlayerRecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        val videoList: List<Video> = getDummyVideoList()
        val adapter = VideoListAdapter(this, videoList)

        videoPlayerRecyclerView = findViewById(R.id.recyclerView)
        videoPlayerRecyclerView.setVideoList(videoList) // This is important
        videoPlayerRecyclerView.setHasFixedSize(true)
        videoPlayerRecyclerView.layoutManager = LinearLayoutManager(this)
        videoPlayerRecyclerView.adapter = adapter
    }

    override fun onPause() {
        videoPlayerRecyclerView.pausePlayer()
        super.onPause()
    }

    override fun onResume() {
        videoPlayerRecyclerView.resumePlayer()
        super.onResume()
    }

    override fun onDestroy() {
        videoPlayerRecyclerView.releasePlayer()
        super.onDestroy()
    }

    private fun getDummyVideoList(): List<Video> {
        return arrayListOf(
            Video(
                "Sending Data to a New Activity with Intent Extras",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Sending+Data+to+a+New+Activity+with+Intent+Extras.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Sending+Data+to+a+New+Activity+with+Intent+Extras.png",
                "Description for media object #1"
            ),
            Video(
                "REST API, Retrofit2, MVVM Course SUMMARY",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/REST+API+Retrofit+MVVM+Course+Summary.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/REST+API%2C+Retrofit2%2C+MVVM+Course+SUMMARY.png",
                "Description for media object #2"
            ),
            Video(
                "MVVM and LiveData",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/MVVM+and+LiveData+for+youtube.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/mvvm+and+livedata.png",
                "Description for media object #3"
            ),
            Video(
                "Swiping Views with a ViewPager",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/SwipingViewPager+Tutorial.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Swiping+Views+with+a+ViewPager.png",
                "Description for media object #4"
            ),
            Video(
                "Database Cache, MVVM, Retrofit, REST API demo for upcoming course",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Rest+api+teaser+video.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Rest+API+Integration+with+MVVM.png",
                "Description for media object #5"
            ),
        )
    }
}