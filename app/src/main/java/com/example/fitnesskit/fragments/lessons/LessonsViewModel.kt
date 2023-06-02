package com.example.fitnesskit.fragments.lessons

import android.icu.text.CaseMap
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnesskit.api.ApiCore
import com.example.fitnesskit.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class LessonsViewModel : ViewModel() {

    private val _lessons: MutableLiveData<List<ListItem>> = MutableLiveData()
    val lessons: LiveData<List<ListItem>> get() = _lessons

    init {
        getInfo()
    }

    fun getInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiCore.retrofit.getInfo(2)
                _lessons.postValue(createList(response))
            } catch (t: Throwable) {
                Log.d("", t.toString())
            }
        }
    }

    private suspend fun createList(response: Response): List<ListItem> = withContext(Dispatchers.Default) {
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
                items.add(TrainingDay(convertTime(groupLessons.first().dateMills, "EEEE, dd MMMM")))
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