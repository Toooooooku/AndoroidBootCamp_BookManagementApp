package com.example.androidbootcampiwatepref.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.androidbootcampiwatepref.ui.uimodel.Book
import com.example.androidbootcampiwatepref.ui.uimodel.BookStatus
import com.example.androidbootcampiwatepref.viewmodel.BookViewModel
@Composable
fun BookManagementApp(viewModel: BookViewModel) {
    val books by viewModel.books.collectAsState(initial = emptyList())
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        DisplayMessages(uiState = uiState)

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(books) { book ->
                BookItem(
                    book = book,
                    onStatusChange = { newStatus: BookStatus ->
                        viewModel.updateBookStatus(book.id, newStatus)
                    }
                )
            }
        }

        AddBookForm(onAddBook = viewModel::addBook)
    }
}

@Composable
fun DisplayMessages(uiState: BookViewModel.UiState) {
    uiState.error?.let { error: String ->
        Text(text = error, color = Color.Red, modifier = Modifier.padding(bottom = 8.dp))
    }

    uiState.message?.let { message: String ->
        Text(
            text = message,
            color = Color.Green,
            modifier = Modifier.padding(bottom = 8.dp)
        )
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

@Composable
fun AddBookForm(onAddBook: (String, String) -> Unit) {
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }

    Column {
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
                    onAddBook(title, author)
                    title = ""
                    author = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Add Book")
        }
    }
}