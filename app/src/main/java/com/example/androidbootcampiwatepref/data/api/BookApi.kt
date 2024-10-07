package com.example.androidbootcampiwatepref.data.api

import com.example.androidbootcampiwatepref.ui.uimodel.Book
import com.example.androidbootcampiwatepref.ui.uimodel.BookStatus
import kotlinx.coroutines.delay
import kotlin.random.Random

class BookApi {
    private val fakeBooks = listOf(
        Book(1, "The Great Gatsby", "F. Scott Fitzgerald", BookStatus.UNREAD),
        Book(2, "To Kill a Mockingbird", "Harper Lee", BookStatus.READING),
        Book(3, "1984", "George Orwell", BookStatus.READ)
    )

    suspend fun getBooks(): List<Book> {
        delay(1000) // Simulate network delay
        return fakeBooks
    }

    suspend fun addBook(title: String, author: String): Book {
        delay(500) // Simulate network delay
        return Book(
            id = Random.nextInt(100, 10000),
            title = title,
            author = author,
            status = BookStatus.UNREAD
        )
    }

    suspend fun updateBookStatus(bookId: Int, newStatus: BookStatus): Book {
        delay(500) // Simulate network delay
        val book = fakeBooks.find { it.id == bookId } ?: throw Exception("Book not found")
        return book.copy(status = newStatus)
    }
}