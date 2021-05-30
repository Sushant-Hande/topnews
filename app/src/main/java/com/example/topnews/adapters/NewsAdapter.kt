package com.example.topnews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.topnews.databinding.NewsItemBinding
import com.example.topnews.extensions.toFormattedDate
import com.example.topnews.models.NewsModel

/**
 * Created by shande on 10-05-2021
 */
class NewsAdapter(
    private var newsList: List<NewsModel>,
    private val onNewsSelected: (news: NewsModel) -> Unit
) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: NewsItemBinding =
            NewsItemBinding.inflate(layoutInflater, parent, false)
        return NewsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position], onNewsSelected)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class NewsViewHolder(private val itemBinding: NewsItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(news: NewsModel, onNewsSelected: (news: NewsModel) -> Unit) {
            with(itemBinding) {
                itemBinding.ivNews.load(news.urlToImage) {
                    transformations(RoundedCornersTransformation(10f, 10f, 10f, 10f))
                }
                tvTitle.text = news.title
                tvDate.text = news.publishedAt.toFormattedDate()
                tvSource.text = news.source?.name
                cardView.setOnClickListener {
                    onNewsSelected(news)
                }
                executePendingBindings()
            }
        }
    }
}