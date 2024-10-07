package com.example.androidbootcampiwatepref.ui.uimodel

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val status: BookStatus = BookStatus.UNREAD
)

@Serializable
enum class BookStatus {
    UNREAD, READING, READ
}