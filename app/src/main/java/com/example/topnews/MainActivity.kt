package com.example.topnews

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import com.example.topnews.models.NewsModel


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GetNewsRequest(handler).start()
    }

    private val handler = object : Handler(Looper.getMainLooper()) {

        override fun handleMessage(msg: Message) {
            val newsData = msg.data
            newsData?.let {
                val newsList = newsData.getParcelableArrayList<NewsModel>("NewList")
                println("News List Size ${newsList?.size}")
            }
        }
    }

}
