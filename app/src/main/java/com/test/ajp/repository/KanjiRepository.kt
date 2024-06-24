package com.test.ajp.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.test.ajp.models.KanjiData
import kotlinx.coroutines.tasks.await

class KanjiRepository {
    suspend fun getKanjiList(week: String) : List<KanjiData> {
        val kanjiList: MutableList<KanjiData> = mutableListOf()
        val fb = Firebase.firestore
        val kanjiScope = fb.collection("fields").document(week).collection("kanji").get().await()
        for(kanji in kanjiScope){
            val kanjiData = KanjiData(
                id = kanji.id,
                audio = kanji.get("audio").toString(),
                kanji = kanji.get("kanji").toString(),
                meaning = kanji.get("meaning").toString(),
                pdf = kanji.get("pdf").toString(),
                usage = kanji.get("usage").toString(),
                video = kanji.get("video").toString(),
                word = kanji.get("word").toString()
            )
            kanjiList.add(kanjiData)
        }
        return kanjiList
    }
    suspend fun fetchKanjiDetails(week: String,id: String): KanjiData {
        val fb = Firebase.firestore
        val kanji = fb.collection("fields").document(week).collection("kanji").document(id).get().await()
        val kanjiData = KanjiData(
            id = kanji.id,
            audio = kanji.get("audio").toString(),
            kanji = kanji.get("kanji").toString(),
            meaning = kanji.get("meaning").toString(),
            pdf = kanji.get("pdf").toString(),
            usage = kanji.get("usage").toString(),
            video = kanji.get("video").toString(),
            word = kanji.get("word").toString()
        )
        return kanjiData
    }
}