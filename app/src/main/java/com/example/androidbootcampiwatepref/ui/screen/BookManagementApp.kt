package com.example.androidbootcampiwatepref.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androidbootcampiwatepref.ui.uimodel.Book
import com.example.androidbootcampiwatepref.ui.uimodel.BookStatus
import com.example.androidbootcampiwatepref.viewmodel.BookViewModel

@Composable
fun BookManagementApp(viewModel: BookViewModel) {
    val books by viewModel.books.collectAsState(initial = emptyList())
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        // Book list
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(books) { book ->
                BookItem(book, onStatusChange = { newStatus ->
                    viewModel.updateBookStatus(book.id, newStatus)
                })
            }
        }

        // Add new book
        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = author,
                onValueChange = { author = it },
                label = { Text("Author") },
                modifier = Modifier.weight(1f)
            )
        }
        Button(
            onClick = {
                if (title.isNotBlank() && author.isNotBlank()) {
                    viewModel.addBook(title, author)
                    title = ""
                    author = ""
                }
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Add Book")
        }
    }
}

@Composable
fun BookItem(book: Book, onStatusChange: (BookStatus) -> Unit) {
    Card(modifier = Modifier.padding(vertical = 4.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = book.title, style = MaterialTheme.typography.headlineSmall)
            Text(text = book.author, style = MaterialTheme.typography.bodyMedium)
            Row {
                Text("Status: ${book.status}")
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    val newStatus = when (book.status) {
                        BookStatus.UNREAD -> BookStatus.READING
                        BookStatus.READING -> BookStatus.READ
                        BookStatus.READ -> BookStatus.UNREAD
                    }
                    onStatusChange(newStatus)
                }) {
                    Text("Change Status")
                }
            }
        }
    }
}