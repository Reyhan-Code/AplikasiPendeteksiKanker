package com.dicoding.asclepius.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dicoding.asclepius.R
import com.dicoding.asclepius.api.NewsItem
import com.dicoding.asclepius.databinding.ItemNewsBinding


class NewAdapter : ListAdapter<NewsItem, NewAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }


    class MyViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(news: NewsItem) {
            with(binding) {
                Glide.with(itemView)
                    .load(news.imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imgNew)
                tvTitle.text = news.title

                itemView.findViewById<TextView>(R.id.tvLink).apply {
                    text = "Read more"
                    setTag(R.id.tvLink, news.url)
                    visibility = if (news.url != null) View.VISIBLE else View.GONE
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NewsItem>() {
            override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}