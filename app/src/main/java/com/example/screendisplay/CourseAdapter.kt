package com.example.screendisplay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.screendisplay.model.Course

class CourseAdapter(
    private val courseList: List<Course>,
    private val onItemClick: (Course) -> Unit
) : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.courseTitle)
        val description: TextView = itemView.findViewById(R.id.courseDescription)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(courseList[position])
                }
            }
        }
        val liveIcon = itemView.findViewById<ImageView>(R.id.liveIcon)
        liveIcon.setOnClickListener {
            Toast.makeText(context, "Join Live Stream with Lecturer", Toast.LENGTH_SHORT).show()
            // You can open a LiveStreamActivity or show a dialog if needed
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_course, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courseList[position]
        holder.title.text = course.title
        holder.description.text = course.description
    }

    override fun getItemCount() = courseList.size
}
