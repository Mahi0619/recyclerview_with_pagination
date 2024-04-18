package com.mrdev.androidtestassesment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList
import java.util.LinkedList

/*
class PaginationAdapter(context: Context, postList: ArrayList<Posts.PostsItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val context: Context = context
    private var postsList: MutableList<Posts.PostsItem>? = null
    private var isLoadingAdded = false

    init {
        this.postsList = LinkedList()
    }

    fun setPostsList(postsList: MutableList<Posts.PostsItem>?) {
        this.postsList = postsList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            LOADING -> {
                val viewLoading: View = inflater.inflate(R.layout.item_progress, parent, false)
                viewHolder = LoadingViewHolder(viewLoading)
            }
            else -> {
                val viewItem: View = inflater.inflate(R.layout.item_list, parent, false)
                viewHolder = PostViewHolder(viewItem)
            }
        }
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = postsList!![position]
        when (getItemViewType(position)) {
            ITEM -> {
                val movieViewHolder = holder as PostViewHolder
                movieViewHolder.mTitle.text = post.title
                */
/*Glide.with(context).load(post.data.body)
                    .apply(RequestOptions.centerCropTransform()).into(movieViewHolder.movieImage)*//*

            }
            LOADING -> {
                val loadingViewHolder = holder as LoadingViewHolder
                loadingViewHolder.progressBar.visibility = View.VISIBLE
            }
        }

    }

    override fun getItemCount(): Int {
        return postsList?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == postsList!!.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    */
/*fun addLoadingFooter() {
        isLoadingAdded = true
        postsList?.let { addAll(it) }
    }*//*


    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position = postsList!!.size - 1
        val result = getItem(position)
        if (result != null) {
            postsList!!.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun add(post: Posts) {
        postsList!!.add(postsList)
        notifyItemInserted(postsList!!.size - 1)
    }

    fun addAll(moveResults: List<Posts>) {
        for (result in moveResults) {
            add(result)
        }
    }

    fun getItem(position: Int): Posts {
        return postsList!![position]
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mTitle: TextView = itemView.findViewById(R.id.tvTitle)
    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar: ProgressBar = itemView.findViewById(R.id.loadmore_progress)
    }

    companion object {
        private const val LOADING = 0
        private const val ITEM = 1
    }
}
*/
