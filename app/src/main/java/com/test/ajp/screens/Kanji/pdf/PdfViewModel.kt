package com.test.ajp.screens.Kanji.pdf

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class PdfViewModel : ViewModel() {
    private val _pdfStream : MutableLiveData<ByteArray?> by lazy {
        MutableLiveData<ByteArray?>()
    }
    val pdfStream: LiveData<ByteArray?>
        get() = _pdfStream

    fun loadPdf(url: String) {
        viewModelScope.launch {
            val result = fetchPdf(url)
            _pdfStream.value = result
        }
    }

    private suspend fun fetchPdf(url: String): ByteArray? {
        return withContext(Dispatchers.IO) {
            val urlConnection = URL(url).openConnection() as HttpsURLConnection
            return@withContext try {
                if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = BufferedInputStream(urlConnection.inputStream)
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    inputStream.copyTo(byteArrayOutputStream)
                    byteArrayOutputStream.toByteArray()
                } else {
                    null
                }
            } catch (e: IOException) {
                e.printStackTrace()
                null
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }finally {
                urlConnection.disconnect()
            }
        }
    }
}