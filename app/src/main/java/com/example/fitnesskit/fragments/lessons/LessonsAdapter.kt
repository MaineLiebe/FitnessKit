package com.example.fitnesskit.fragments.lessons

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitnesskit.databinding.ItemDateBinding
import com.example.fitnesskit.databinding.ItemLessonBinding
import com.example.fitnesskit.model.ListItem
import com.example.fitnesskit.model.TrainingDay
import com.example.fitnesskit.model.UiLesson
import java.lang.System.load

class LessonsAdapter(
private val onItemClick: (lesson: UiLesson) -> Unit
) : ListAdapter<ListItem, RecyclerView.ViewHolder>(LessonDiffUtil()) {

    class LessonsViewHolder(
        private val binding: ItemLessonBinding,
        private val onItemClick: (lesson: UiLesson) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(lesson: UiLesson) {
            binding.root.setOnClickListener { onItemClick(lesson) }
            binding.timeStart.text = lesson.startTime
            binding.timeEnd.text = lesson.endTime
            binding.classRoom.text = lesson.place
            binding.trainerName.text = lesson.coachName
            binding.trainingName.text = lesson.name
            binding.time.text = lesson.time
        }
    }

    class DateViewHolder(
        private val binding: ItemDateBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(day: TrainingDay) {
            binding.textView.text = day.day

        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UiLesson -> TYPE_TRAINING
            is TrainingDay -> TYPE_DATE
            else -> -1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_DATE -> {
                val binding = ItemDateBinding.inflate(inflater, parent, false)
                DateViewHolder(binding)
            }
            TYPE_TRAINING -> {
                val binding = ItemLessonBinding.inflate(inflater, parent, false)
                LessonsViewHolder(binding, onItemClick)
            }
            else -> {
                throw Exception()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LessonsViewHolder -> {
                holder.bind(getItem(position) as UiLesson)
            }
            is DateViewHolder -> {
                holder.bind(getItem(position) as TrainingDay)
            }
        }

    }

    class LessonDiffUtil : DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            if (oldItem::class != newItem::class) return false
            return if (oldItem is UiLesson) {
                oldItem.name == (newItem as UiLesson).name
            } else {
                (oldItem as TrainingDay).day == (newItem as TrainingDay).day
            }
        }

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return if (oldItem is UiLesson) {
                oldItem == (newItem as UiLesson)
            } else {
                (oldItem as TrainingDay) == (newItem as TrainingDay)
            }
        }
    }

    companion object {
        private const val TYPE_TRAINING = 0
        private const val TYPE_DATE = 1
    }
}
