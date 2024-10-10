package com.example.androidbootcampiwatepref.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.example.androidbootcampiwatepref.R
import com.example.androidbootcampiwatepref.databinding.ActivityArticlesBinding
import com.example.androidbootcampiwatepref.viewmodel.ArticlesViewModel

class ArticlesActivity : AppCompatActivity() {

    private val articlesViewModel: ArticlesViewModel by viewModels()
    private lateinit var binding: ActivityArticlesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)
        binding = ActivityArticlesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // uiModelを監視し、UIを更新
        articlesViewModel.uiModel.asLiveData().observe(this) {              // 記事でUIを更新
        }
    }
}