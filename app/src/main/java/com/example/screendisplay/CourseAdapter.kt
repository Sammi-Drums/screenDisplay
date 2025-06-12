package com.example.screendisplay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.screendisplay.model.Course

class CourseAdapter(
    private val courseList: List<Course>,
    private val onItemClick: (Course) -> Unit
) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.courseTitle)
        val description: TextView = itemView.findViewById(R.id.courseDescription)
        val liveIcon: ImageView = itemView.findViewById(R.id.liveIcon)

        fun bind(course: Course) {
            title.text = course.title
            description.text = course.description

            // Click on entire card
            itemView.setOnClickListener {
                onItemClick(course)
            }

            // Hover/Click Live Icon
            liveIcon.setOnClickListener {
                Toast.makeText(itemView.context, "Join Live Stream with Lecturer", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_course, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(courseList[position])
    }

    override fun getItemCount(): Int = courseList.size
}
