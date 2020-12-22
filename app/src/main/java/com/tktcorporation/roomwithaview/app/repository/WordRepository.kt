package com.tktcorporation.roomwithaview.app.repository

import androidx.annotation.WorkerThread
import com.tktcorporation.roomwithaview.infrastructure.dao.WordDao
import com.tktcorporation.roomwithaview.infrastructure.entity.WordEntity
import kotlinx.coroutines.flow.Flow

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */
class WordRepository(private val wordDao: WordDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allWords: Flow<List<WordEntity>> = wordDao.getAlphabetizedWords()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(wordEntity: WordEntity) {
        wordDao.insert(wordEntity)
    }
}
