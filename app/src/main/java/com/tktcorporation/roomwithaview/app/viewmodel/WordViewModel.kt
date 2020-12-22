package com.tktcorporation.roomwithaview.app.viewmodel

import com.tktcorporation.roomwithaview.app.repository.WordRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tktcorporation.roomwithaview.infrastructure.entity.WordEntity
import kotlinx.coroutines.launch

/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */

class WordViewModel(private val repository: WordRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<WordEntity>> = repository.allWords.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(wordEntity: WordEntity) = viewModelScope.launch {
        repository.insert(wordEntity)
    }
}

class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
