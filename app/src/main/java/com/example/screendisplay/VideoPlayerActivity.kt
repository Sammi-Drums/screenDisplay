package com.example.screendisplay

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.screendisplay.databinding.ActivityVideoPlayerBinding
import com.example.screendisplay.model.Course

class VideoPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoPlayerBinding
    private lateinit var player: ExoPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🎥 Video Setup
        val videoUrl = intent.getStringExtra("VIDEO_URL") ?: return
        val videoTitle = intent.getStringExtra("VIDEO_TITLE") ?: "No Title"
        val chapters = intent.getStringArrayListExtra("CHAPTERS") ?: arrayListOf()

        binding.videoTitle.text = videoTitle

        // ✅ Spinner setup
        if (chapters.isNotEmpty()) {
            val chapterAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, chapters)
            binding.chapterSpinner.adapter = chapterAdapter
        }

        // ✅ ExoPlayer setup
        player = ExoPlayer.Builder(this).build()
        binding.playerView.player = player
        val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = true

        // ✅ Related videos setup
        val relatedCourses = listOf(
            Course("Firebase Auth", "Sign up & login securely", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"),
            Course("Camera Integration", "Use camera API in Android", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4"),
            Course("Push Notifications", "Send alerts via FCM", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4")
        )

        val relatedCourseAdapter = CourseAdapter(relatedCourses) { selectedCourse ->
            val intent = Intent(this, VideoPlayerActivity::class.java)
            intent.putExtra("VIDEO_URL", selectedCourse.videoUrl)
            intent.putExtra("VIDEO_TITLE", selectedCourse.title)
            intent.putStringArrayListExtra("CHAPTERS", arrayListOf("Intro", "Setup", "Use", "Review"))
            startActivity(intent)
            finish()
        }

        binding.relatedVideosRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.relatedVideosRecyclerView.adapter = relatedCourseAdapter
    }



    override fun onStop() {
        super.onStop()
        player.release() // properly stop playback
    }
}
