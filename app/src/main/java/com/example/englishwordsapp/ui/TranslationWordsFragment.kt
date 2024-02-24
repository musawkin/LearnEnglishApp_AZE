package com.example.englishwordsapp.ui

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import com.example.englishwordsapp.databinding.FragmentTranslationWordsBinding
import com.example.englishwordsapp.SimpleWordsModel
import com.example.englishwordsapp.Words
import java.util.Locale

class TranslationWordsFragment : Fragment() {

    private var binding: FragmentTranslationWordsBinding? = null
    private val adapterForWords = RcAdapterForWordsTranslation()
    lateinit var textToSpeech: TextToSpeech


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentTranslationWordsBinding.inflate(layoutInflater, container, false)

//        textToSpeech = TextToSpeech(requireContext()) { status ->
//            if (status == TextToSpeech.SUCCESS) {
//                val result = textToSpeech.setLanguage(Locale.US)
//                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                    // Обработка ошибки
//                }
//            } else {
//                // Обработка ошибки
//            }
//        }
        textToSpeech = TextToSpeech(requireContext(), null)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.rcView?.adapter = adapterForWords
        adapterForWords.updateData(listOfWords)

        adapterForWords.setOnClickListener(object : RcAdapterForWordsTranslation.RvOnClickListener {
            override fun onClick(data: SimpleWordsModel) {

            }
            override fun playSpeaker(text: String, position: Int) {
                textToSpeech.setLanguage(Locale.US)
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        })

        binding?.searchView?.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { query->
                    val filteredList = listOfWords.filter { item ->
                        item.wordInEnglish?.contains(query, ignoreCase = true) == true
                    }.toMutableList()
                    adapterForWords.updateData(filteredList)
                    return true
                }
                return false
            }

        })

    }

    private val listOfWords = Words.listOfWords

    override fun onDestroy() {
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }


}