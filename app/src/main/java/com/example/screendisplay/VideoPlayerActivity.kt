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

        // 🎥 Setup player
        val videoUrl = intent.getStringExtra("VIDEO_URL") ?: return
        player = ExoPlayer.Builder(this).build()
        binding.playerView.player = player
        val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = true

        // 📝 Set video title
        val videoTitle = intent.getStringExtra("VIDEO_TITLE") ?: "No Title"
        binding.videoTitle.text = videoTitle

        // 🔽 Setup course chapters (Spinner dropdown)
        val chapters = intent.getStringArrayListExtra("CHAPTERS")
        if (chapters != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, chapters)
            binding.chapterSpinner.adapter = adapter
        }

        // 🎬 Load related videos below
        val relatedCourses = listOf(
            Course("Firebase Auth", "Sign up & login securely", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"),
            Course("Camera Integration", "Use camera API in Android", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/Sintel.mp4"),
            Course("Push Notifications", "Send alerts via FCM", "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4")
        )

        val adapter = CourseAdapter(relatedCourses) { selectedCourse ->
            val intent = Intent(this, VideoPlayerActivity::class.java)
            intent.putExtra("VIDEO_URL", selectedCourse.videoUrl)
            intent.putExtra("VIDEO_TITLE", selectedCourse.title)
            intent.putExtra("CHAPTERS", arrayListOf("Intro", "Setup", "Use", "Review"))
            startActivity(intent)
            finish()
        }
//        Video samples
        val sampleChapters = arrayListOf("Introduction", "Getting Started", "Core Concepts", "Practical Demo", "Conclusion")

        courseAdapter = CourseAdapter(courseList) { selectedCourse ->
            val intent = Intent(this, VideoPlayerActivity::class.java)
            intent.putExtra("VIDEO_URL", selectedCourse.videoUrl)
            intent.putExtra("VIDEO_TITLE", selectedCourse.title)
            intent.putStringArrayListExtra("CHAPTERS", sampleChapters)
            startActivity(intent)
        }


        binding.relatedVideosRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.relatedVideosRecyclerView.adapter = adapter
    }

    override fun onStop() {
        super.onStop()
        player.release()
    }
}
