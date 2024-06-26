package com.example.englishwordsapp.ui.main.learn.vocabulary

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
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
class TranslationWordsFragment : Fragment() {

    private var binding: FragmentTranslationWordsBinding? = null
    private val adapterForWords by lazy { VocabularyListAdapter() }
    private val loadingAdapter by lazy { LoaderStateAdapter() }
    private lateinit var textToSpeech: TextToSpeech
    private val viewModel by viewModels<VocabularyTranslationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentTranslationWordsBinding.inflate(layoutInflater, container, false)

        textToSpeech = TextToSpeech(requireContext()) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = textToSpeech.setLanguage(Locale.US)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    // Обработка ошибки
                }
            } else {
                // Обработка ошибки
            }
        }
        textToSpeech = TextToSpeech(requireContext(), null)

        return binding?.root
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
                textToSpeech.setLanguage(Locale.US)
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