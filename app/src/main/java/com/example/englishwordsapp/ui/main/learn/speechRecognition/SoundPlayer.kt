package com.example.englishwordsapp.ui.main.learn.speechRecognition

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale
import javax.inject.Inject

class SoundPlayer @Inject constructor() {
    private lateinit var textToSpeech: TextToSpeech

    fun playSound(word: String, context: Context){
        textToSpeech = TextToSpeech(context, null)
        textToSpeech.setLanguage(Locale.US)
        textToSpeech.speak(word, TextToSpeech.QUEUE_FLUSH, null, null)
    }
}