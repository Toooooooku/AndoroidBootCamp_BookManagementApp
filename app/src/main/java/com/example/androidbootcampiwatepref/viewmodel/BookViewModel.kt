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
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = BookRepository(application)
    private val api = BookApi()

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books

    init {
        loadBooks()
    }

    private fun loadBooks() {
        viewModelScope.launch {
            repository.books.collect { bookList ->
                _books.value = bookList
            }
        }
    }

    fun addBook(title: String, author: String) {
        viewModelScope.launch {
            val newBook = api.addBook(title, author)
            val updatedList = _books.value + newBook
            repository.saveBooks(updatedList)
        }
    }

    fun updateBookStatus(id: Int, newStatus: BookStatus) {
        viewModelScope.launch {
            val updatedBook = api.updateBookStatus(id, newStatus)
            val updatedList = _books.value.map { if (it.id == id) updatedBook else it }
            repository.saveBooks(updatedList)
        }
    }
}