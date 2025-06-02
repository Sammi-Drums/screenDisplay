package com.example.screendisplay
import android.content.Intent

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.screendisplay.databinding.ActivityMainBinding
import com.example.screendisplay.model.Course

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var courseAdapter: CourseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Step 1: Create dummy list of courses
        val courseList = listOf(
            Course(
                "Intro to Android Development",
                "Learn the basics of Android apps using Kotlin.",
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
            ),
            Course(
                "Firebase Authentication",
                "Secure your apps with email and password login.",
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
            ),
            Course(
                "Using ExoPlayer in Android",
                "Implement custom video playback with ExoPlayer.",
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/TearsOfSteel.mp4"
            )
        )

        // Step 2: Set up RecyclerView
        courseAdapter = CourseAdapter(courseList) { selectedCourse ->
            // This is called when a course is clicked
            val intent = Intent(this, VideoPlayerActivity::class.java)
            intent.putExtra("VIDEO_URL", selectedCourse.videoUrl)
            startActivity(intent)
//            Updating my intent so that I can have a list of related video drop down and their chapters
            intent.putExtra("VIDEO_TITLE", selectedCourse.title)
            intent.putExtra("CHAPTERS", arrayListOf("Introduction", "Chapter 1", "Chapter 2", "Conclusion"))


            // Next step: open VideoPlayerActivity to play video
        }



        binding.courseRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.courseRecyclerView.adapter = courseAdapter
    }
}
