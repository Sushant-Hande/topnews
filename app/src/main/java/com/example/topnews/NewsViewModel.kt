package com.example.topnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.topnews.models.NewsModel

/**
 * Created by shande on 30-05-2021
 */
class NewsViewModel : ViewModel() {

    val _newsData = MutableLiveData<NewsViewModel>()

    val newsData: LiveData<NewsModel>
        get() = newsData

    fun getNewsData() {
    }

}