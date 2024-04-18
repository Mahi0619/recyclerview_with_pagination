/*
package com.mrdev.androidtestassesment.post.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mrdev.androidtestassesment.R
import com.mrdev.androidtestassesment.other.CallBack
import com.mrdev.androidtestassesment.post.postModel.Post

class PostAdapter(
    postsList: ArrayList<Post>,
    private val onItemClick: CallBack<Post>
) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private val originalData: ArrayList<Post> = ArrayList()
    private val filteredData: ArrayList<Post> = ArrayList()

    init {
        // Initialize both originalData and filteredData with the same data initially
        originalData.addAll(postsList)
        filteredData.addAll(postsList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.item_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return filteredData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filteredData[position]
        holder.bind(item)
    }

    fun updateData(newData: List<Post>) {
        originalData.clear()
        originalData.addAll(newData)
        filterData("")
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tvTitle)
        private val cvMain: CardView = itemView.findViewById(R.id.card_view)

        fun bind(post: Post) {
            title.text = post.title ?: "N/A"
            cvMain.setOnClickListener {
                onItemClick.onSuccess(post)
            }
        }
    }

    fun filterData(query: String): Boolean {
        filteredData.clear()
        if (query.isEmpty()) {
            // If the query is empty, show all items
            filteredData.addAll(originalData)
        } else {
            // Filter items that start with the query (case-insensitive)
            for (item in originalData) {
                if (item.title.contains(
                        query,
                        ignoreCase = true
                    )
                ) {
                    filteredData.add(item)
                }
            }
        }

        notifyDataSetChanged()

        // Return true if items were found, false if no items match the filter
        return filteredData.isNotEmpty()
    }
}
*/


package com.mrdev.androidtestassesment.post.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mrdev.androidtestassesment.R
import com.mrdev.androidtestassesment.other.CallBack
import com.mrdev.androidtestassesment.post.postModel.Post

/*

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mrdev.androidtestassesment.R
import com.mrdev.androidtestassesment.other.CallBack
import com.mrdev.androidtestassesment.post.postModel.Post

class PostAdapter(
    private var postsList: ArrayList<Post>,
    private val onItemClick: CallBack<Post>
) : RecyclerView.Adapter<PostAdapter.ViewHolder>()
{


    private val originalData: ArrayList<Post> = ArrayList()
    private val filteredData: ArrayList<Post> = ArrayList()

    init {
        // Initialize both originalData and filteredData with the same data initially
        originalData.addAll(postsList)
        filteredData.addAll(postsList)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.item_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = postsList[position]
        holder.bind(item)
    }

    fun updateData(newData: List<Post>) {
        postsList.clear()
        postsList.addAll(newData)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tvTitle)
        private val cvMain: CardView = itemView.findViewById(R.id.card_view)

        fun bind(post: Post) {
            title.text = post.title ?: "N/A"
            cvMain.setOnClickListener {
                onItemClick.onSuccess(post)
            }
        }
    }



    fun filterData(query: String): Boolean {
        filteredData.clear()

        if (query.isEmpty()) {
            // If the query is empty, show all items
            filteredData.addAll(originalData)
        } else {
            // Filter items that start with the query (case-insensitive)
            for (item in originalData) {
                if (item.title.contains(query, ignoreCase = true) ) {
                    filteredData.add(item)
                }
            }
        }

        notifyDataSetChanged()

        // Return true if items were found, false if no items match the filter
        return filteredData.isNotEmpty()
    }

}
*/



class PostAdapter(
    private val onItemClick: CallBack<Post>
) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private val originalData: ArrayList<Post> = ArrayList()
    private val filteredData: ArrayList<Post> = ArrayList()
    private var searchQuery: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.item_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return filteredData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filteredData[position]
        holder.bind(item)
    }

    fun updateData(newData: List<Post>) {
        originalData.clear()
        originalData.addAll(newData)
        filterData(searchQuery)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tvTitle)
        private val cvMain: CardView = itemView.findViewById(R.id.card_view)

        fun bind(post: Post) {
            title.text = post.title ?: "N/A"
            cvMain.setOnClickListener {
                onItemClick.onSuccess(post)
            }
        }
    }

    fun filterData(query: String): Boolean {
        filteredData.clear()

        if (query.isEmpty()) {
            // If the query is empty, show all items
            filteredData.addAll(originalData)
        } else {
            // Filter items that contain the query (case-insensitive)
            for (item in originalData) {
                if (item.title.contains(query, ignoreCase = true)) {
                    filteredData.add(item)
                }
            }
        }

        notifyDataSetChanged()

        // Return true if items were found, false if no items match the filter
        return filteredData.isNotEmpty()
    }

    fun setSearchQuery(query: String) {
        searchQuery = query
        filterData(searchQuery)
    }
}
