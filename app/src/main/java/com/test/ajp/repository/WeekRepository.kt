package com.test.ajp.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class WeekRepository {
    suspend fun getWeeks(): List<String> {
        val weeks: MutableList<String> = mutableListOf()
        val fb = Firebase.firestore
        val filed = fb.collection("fields").get().await()

        for (document in filed.documents) {
            val week = document.get("name")
            weeks.add(week.toString())
        }
        return weeks
    }
}