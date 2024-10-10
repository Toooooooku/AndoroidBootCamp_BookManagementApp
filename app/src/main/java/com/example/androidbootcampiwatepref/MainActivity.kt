package com.example.androidbootcampiwatepref

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidbootcampiwatepref.ui.screen.AddBookScreen
import com.example.androidbootcampiwatepref.ui.screen.BookListScreen
import com.example.androidbootcampiwatepref.ui.screen.BookManagementApp
import com.example.androidbootcampiwatepref.ui.theme.AndroidBootcampIwatePrefTheme
import com.example.androidbootcampiwatepref.viewmodel.BookViewModel


class MainActivity : ComponentActivity() {
    private val viewModel: BookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidBootcampIwatePrefTheme {
                val navController = rememberNavController()
                 NavHost(navController, startDestination = "bookList") {
                    composable("bookList") { BookListScreen(navController,viewModel) }
                    composable("addBook") { AddBookScreen(navController, viewModel) }
                    composable("bookList") { BookManagementApp(viewModel) }
                     //test
            }
        }
    }

}}