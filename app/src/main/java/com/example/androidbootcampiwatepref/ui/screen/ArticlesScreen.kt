package com.example.androidbootcampiwatepref.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidbootcampiwatepref.data.api.BookApi
import com.example.androidbootcampiwatepref.ui.uimodel.Book
import com.example.androidbootcampiwatepref.ui.uimodel.BookStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookViewModel(private val api: BookApi) : ViewModel() {
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadBooks()
    }

    private fun loadBooks() {
        viewModelScope.launch {
            try {
                _books.value = api.getBooks()
            } catch (e: Exception) {
                _error.value = "Failed to load books: ${e.message}"
            }
        }
    }

    fun updateBookStatus(id: Int, newStatus: BookStatus) {
        viewModelScope.launch {
            try {
                val updatedBook = api.updateBookStatus(id, newStatus)
                _books.value = books.value.map { if (it.id == id) updatedBook else it }
            } catch (e: Exception) {
                _error.value = "Failed to update book status: ${e.message}"
            }
        }
    }
}