package com.example.topnews

import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.example.topnews.models.NewsModel
import com.example.topnews.models.Source
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/**
 * Created by shande on 29-05-2021
 */
class GetNewsRequest(private val handler: Handler) : Thread() {

    override fun run() {
        super.run()

        val url: URL

        var response: String? = ""

        val message = Message()

        try {
            url =
                URL("https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json")
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
            conn.readTimeout = 30000
            conn.connectTimeout = 60000
            conn.requestMethod = "GET"

            when (conn.responseCode) {
                HttpsURLConnection.HTTP_OK -> {
                    var line: String
                    val br = BufferedReader(InputStreamReader(conn.inputStream))
                    while (br.readLine().also { line = it } != null) {
                        response += line
                    }
                }

                500 -> {
                    var line: String
                    val br = BufferedReader(InputStreamReader(conn.inputStream))
                    while (br.readLine().also { line = it } != null) {
                        response += line
                    }
                }

                else -> {
                    response = ""
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (response != null && response.isNotEmpty()) {

            val newsModelList = arrayListOf<NewsModel>()
            val jsonObject = JSONObject(response)
            val articles: JSONArray = jsonObject.getJSONArray("articles")

            for (i in 0 until articles.length()) {

                val newsJsonObject: JSONObject = articles.getJSONObject(i)
                val sourceJsonObject = newsJsonObject.getJSONObject("source")

                val newsModel = NewsModel(
                    author = newsJsonObject.getString("author"),
                    content = newsJsonObject.getString("content"),
                    description = newsJsonObject.getString("description"),
                    publishedAt = newsJsonObject.getString("publishedAt"),
                    title = newsJsonObject.getString("title"),
                    url = newsJsonObject.getString("url"),
                    urlToImage = newsJsonObject.getString("urlToImage"),
                    source = Source(
                        id = sourceJsonObject.getString("id"),
                        name = sourceJsonObject.getString("name")
                    )
                )

                newsModelList.add(newsModel)
            }

            val bundle = Bundle().apply {
                putParcelableArrayList("NewList", newsModelList)
            }
            message.data = bundle
        }

        handler.dispatchMessage(message)
    }

}