package com.example.androidbootcampiwatepref.data.api

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.androidbootcampiwatepref.ui.uimodel.Book  // 正しいパッケージからBookをインポート
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class BookRepository(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "books")
        private val BOOKS_KEY = stringPreferencesKey("books")
    }

//    private val json = Json { ignoreUnknownKeys = true }

    val books: Flow<List<Book>> = context.dataStore.data.map { preferences ->
        val booksJson = preferences[BOOKS_KEY] ?: "[]"
        Json.decodeFromString<List<Book>>(booksJson)
    }

    suspend fun saveBooks(books: List<Book>) {
        context.dataStore.edit { preferences ->
            preferences[BOOKS_KEY] = Json.encodeToString(books)
        }
    }
}