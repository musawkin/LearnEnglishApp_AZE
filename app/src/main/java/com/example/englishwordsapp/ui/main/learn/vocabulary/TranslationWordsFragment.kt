package com.example.englishwordsapp.ui.main.learn.vocabulary

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.englishwordsapp.databinding.FragmentTranslationWordsBinding
import com.example.englishwordsapp.ui.main.learn.FilterWordsDialogFragment
import com.example.englishwordsapp.ui.main.learn.SimpleWordsModel
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class TranslationWordsFragment : Fragment() {

    private var binding: FragmentTranslationWordsBinding? = null
    private val adapterForWords by lazy { VocabularyListAdapter() }
    private val listOfWords = mutableListOf<SimpleWordsModel>()
    private lateinit var textToSpeech: TextToSpeech
    private val viewModel by viewModels<VocabularyTranslationViewModel>()
    private var lastLoadedItem: SimpleWordsModel? = null

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


        binding?.rcView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                Log.d("MUSA222", "isLoading:${viewModel.isLoading} ")
                Log.d("MUSA222", "isLastPage:${viewModel.isLastPage} ")

                if (!viewModel.isLoading ) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                        // Вызов метода загрузки следующей страницы данных во вьюмодели
                        viewModel.loadNextPageOfWords("beginner_level", adapterForWords.currentList.lastOrNull())
                    }
                }
            }
        })

        viewModel.wordsModelData.observe(viewLifecycleOwner) { result ->
            result?.let {
                when (result) {
                    is VocabularyState.Success -> {
                        adapterForWords.addItems(result.listOfWords)
//                        listOfWords.addAll(result.listOfWords)
                        lastLoadedItem = result.listOfWords.lastOrNull()

                        binding?.progressBarLoadingData?.isVisible = false
                    }
                    is VocabularyState.Error -> {
                        Toast.makeText(
                            requireContext(),
                            "Error: ${result.errorException}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is VocabularyState.Loading -> {
                        binding?.progressBarLoadingData?.isVisible = result.isLoading
                    }
                }
            }
        }
        viewModel.getWordsList("beginner_level")

        adapterForWords.onItemClickListener { word->
            textToSpeech.setLanguage(Locale.US)
            textToSpeech.speak(word.word, TextToSpeech.QUEUE_FLUSH, null, null)
        }

//        adapterForWords.setOnClickListener(object : VocabularyAdapter.RvOnClickListener {
//            override fun onClick(data: SimpleWordsModel) {
//            }
//            override fun playSpeaker(text: String, position: Int) {
//                textToSpeech.setLanguage(Locale.US)
//                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
//            }
//        })

        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { query ->
                    val filteredList = listOfWords.filter { item ->
                        item.word?.contains(
                            query,
                            ignoreCase = true
                        ) == true || item.translationToAze?.contains(
                            query,
                            ignoreCase = true
                        ) == true
                    }.toMutableList()
                    adapterForWords.submitList(filteredList)
                    return true
                }
                return false
            }
        })

        binding?.ivFilterginSearch?.setOnClickListener {
            showFilterDialog()
        }
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