package com.example.androidbootcampiwatepref.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidbootcampiwatepref.data.api.BookApi
import com.example.androidbootcampiwatepref.data.api.BookRepository
import com.example.androidbootcampiwatepref.ui.uimodel.Book
import com.example.androidbootcampiwatepref.ui.uimodel.BookStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = BookRepository(application)
    private val api = BookApi()

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books.asStateFlow()

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState


    init {
        loadBooks()
    }

    private fun loadBooks() {
        viewModelScope.launch {
            try {
                repository.books.collect { bookList ->
                    _books.value = bookList
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to load books: ${e.message}") }
            }
        }
    }

    fun addBook(title: String, author: String) {
        viewModelScope.launch {
            try {
                val newBook = api.addBook(title, author)
                val updatedList = _books.value + newBook
                repository.saveBooks(updatedList)
                _uiState.update { it.copy(message = "Book added successfully", error = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to add book: ${e.message}", message = null) }
            }
        }
    }

    fun updateBookStatus(id: Int, newStatus: BookStatus) {
        viewModelScope.launch {
            try {
                val updatedBook = api.updateBookStatus(id, newStatus)
                val updatedList = _books.value.map { if (it.id == id) updatedBook else it }
                repository.saveBooks(updatedList)
                _uiState.update { it.copy(message = "Book status updated successfully", error = null) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Failed to update book status: ${e.message}", message = null) }
            }
        }
    }

    data class UiState(
        val error: String? = null,
        val message: String? = null
    )
}