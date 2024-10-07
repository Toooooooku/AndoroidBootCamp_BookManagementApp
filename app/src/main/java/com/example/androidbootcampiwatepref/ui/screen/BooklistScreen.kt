package com.example.androidbootcampiwatepref.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.androidbootcampiwatepref.viewmodel.BookViewModel
import com.example.androidbootcampiwatepref.ui.uimodel.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    navController: NavController,
    viewModel: BookViewModel = viewModel()
) {
    val books by viewModel.books.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book List") },
                actions = {
                    IconButton(onClick = { navController.navigate("addBook") }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Book")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(books) { book ->
                BookItem(book)
            }
        }
    }
}

@Composable
fun BookItem(book: Book) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = book.title, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "by ${book.author}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}