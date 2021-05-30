package com.example.topnews

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.topnews.adapters.NewsAdapter
import com.example.topnews.databinding.MainActivityBinding
import com.example.topnews.models.NewsModel


class NewsActivity : AppCompatActivity() {
    lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        GetNewsRequest(handler).start()
    }

    private val handler = object : Handler(Looper.getMainLooper()) {

        override fun handleMessage(msg: Message) {
            val newsData = msg.data
            runOnUiThread {
                newsData?.let {
                    val newsList = newsData.getParcelableArrayList<NewsModel>("NewList")
                    newsList?.let {
                        setNewsData(newsList)
                    }
                }
            }
        }
    }

    fun setNewsData(newsList: List<NewsModel>) {
        val adapter = NewsAdapter(newsList) {newsModel->
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(newsModel.url))
            startActivity(browserIntent)
        }
        binding.rvNews.adapter = adapter
    }

}
