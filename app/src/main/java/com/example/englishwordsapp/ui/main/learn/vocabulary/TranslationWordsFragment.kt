package com.example.englishwordsapp.ui.main.learn.vocabulary

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.englishwordsapp.databinding.FragmentTranslationWordsBinding
import com.example.englishwordsapp.extensions.collectFlow
import com.example.englishwordsapp.ui.main.learn.dialogFragments.FilterWordsDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class TranslationWordsFragment : Fragment(), TextToSpeech.OnInitListener {

    private var binding: FragmentTranslationWordsBinding? = null
    private val adapterForWords by lazy { VocabularyListAdapter() }
    private val loadingAdapter by lazy { LoaderStateAdapter() }
    private lateinit var textToSpeech: TextToSpeech
    private val viewModel by viewModels<VocabularyTranslationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentTranslationWordsBinding.inflate(layoutInflater, container, false)
        textToSpeech = TextToSpeech(requireContext(), this)
        return binding?.root
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS){
            textToSpeech.language = Locale.US
            val voices = textToSpeech.voices
            val selectedVoice = voices.firstOrNull { it.locale == Locale.US && it.quality == Voice.QUALITY_HIGH}
            selectedVoice?.let {
                textToSpeech.voice = it
            }
        }else{
            Toast.makeText(requireContext(), "A sound error occurred", Toast.LENGTH_LONG).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.rcView?.adapter = adapterForWords.withLoadStateFooter(loadingAdapter)

        collectFlow(adapterForWords.loadStateFlow, ::onAdapterLoadingChange)
        viewModel.wordsModelData.observe(viewLifecycleOwner) { result ->
            when(result){
                is VocabularyState.Success -> {
                    adapterForWords.submitData(viewLifecycleOwner.lifecycle, result.listOfWords)
                }
                is VocabularyState.Error -> {}
                is VocabularyState.Loading -> {}
            }

        }
        viewModel.getWordsWithPaging()

        adapterForWords.onItemClickListener { word->
            word?.let {
                textToSpeech.speak(word.word, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }


        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { query ->
                    viewModel.getWordsWithPaging()
                    return true
                }
                return false
            }
        })

        binding?.ivFilterginSearch?.setOnClickListener {
            showFilterDialog()
        }
    }

    private fun onAdapterLoadingChange(combinedLoadStates: CombinedLoadStates) {
        binding?.progressBar?.isVisible = (combinedLoadStates.refresh == LoadState.Loading)
    }

    override fun onDestroy() {
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }

    private fun showFilterDialog(){
        val dialog = FilterWordsDialogFragment()
        dialog.show(childFragmentManager, "FilterDialog")
    }


}