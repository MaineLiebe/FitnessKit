package com.example.fitnesskit.usecase

import android.icu.text.SimpleDateFormat
import android.util.Log
import com.example.fitnesskit.model.Coach
import com.example.fitnesskit.model.ListItem
import com.example.fitnesskit.model.Response
import com.example.fitnesskit.model.TrainingDay
import com.example.fitnesskit.model.UiLesson
import com.example.fitnesskit.repository.TrainingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetTrainingUseCaseImpl @Inject constructor(
    private val repository: TrainingRepository
): GetTrainingUseCase {
    override suspend fun getTraining(): List<ListItem> {
        val response = repository.getInfo()
        return if (response != null)
            createList(response)
        else emptyList()

    }
    private suspend fun createList(response: Response): List<ListItem> =
        withContext(Dispatchers.Default) {
            val lessons: ArrayList<UiLesson> = arrayListOf()
            val coaches: HashMap<String, Coach> = hashMapOf()
            val items: ArrayList<ListItem> = arrayListOf()


            response.trainers.forEach { coach ->
                coaches[coach.id] = coach
            }



            response.lessons.forEach { lesson ->
                val startTime = convertTime(lesson.startTime, "HH:mm")
                val endTime = convertTime(lesson.endTime, "HH:mm")
                val duration = convertTime((endTime - startTime), "Hч. mmмин.")
                val uiLesson = UiLesson(
                    name = lesson.name,
                    startTime = lesson.startTime,
                    endTime = lesson.endTime,
                    place = lesson.place,
                    coachId = lesson.coachId,
                    color = lesson.color,
                    time = duration,
                    coachName = coaches[lesson.coachId]?.name ?: "неизвестно",
                    dateMills = convertTime(lesson.date, "yyyy-MM-dd"),
                    startTimeInMills = startTime,
                    description = lesson.description,
                    imageUrl = coaches[lesson.coachId]?.imageUrl ?: ""
                )
                lessons.add(uiLesson)
            }
            val sortedLessons = lessons.sortedBy { it.dateMills }
            val groupLessons: ArrayList<UiLesson> = arrayListOf()
            sortedLessons.forEachIndexed { index, uiLesson ->
                if (index == 0 || uiLesson.dateMills == sortedLessons[index - 1].dateMills) {
                    groupLessons.add(uiLesson)
                } else {
                    items.add(
                        TrainingDay(
                            convertTime(
                                groupLessons.first().dateMills,
                                "EEEE, dd MMMM"
                            )
                        )
                    )
                    items.addAll(groupLessons.sortedBy { it.startTimeInMills })
                    groupLessons.clear()
                    groupLessons.add(uiLesson)
                }
            }
            items
        }

    private fun convertTime(time: String, pattern: String): Long {
        val sdf = SimpleDateFormat(pattern)
        return try {
            val date = sdf.parse(time)
            val timeInMillis = date.time
            Log.d("", timeInMillis.toString())
            timeInMillis
        } catch (e: Exception) {
            0
        }
    }

    private fun convertTime(mills: Long, pattern: String): String {
        val sdf = SimpleDateFormat(pattern)
        return sdf.format(mills)
    }
}
