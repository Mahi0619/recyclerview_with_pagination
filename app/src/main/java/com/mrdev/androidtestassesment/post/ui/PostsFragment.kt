/*
package com.mrdev.androidtestassesment.post.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mrdev.androidtestassesment.data.util.ApiState
import com.mrdev.androidtestassesment.databinding.FragmentPostBinding
import com.mrdev.androidtestassesment.other.CallBack
import com.mrdev.androidtestassesment.post.postModel.Post
import com.mrdev.androidtestassesment.post.PostVM
import com.mrdev.androidtestassesment.post.adapter.PostAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostsFragment : Fragment() {
    private val viewModel: PostVM by viewModels()
    private lateinit var binding: FragmentPostBinding
    private val postAdapter by lazy { PostAdapter(ArrayList(), onItemClick) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(inflater, container, false)
        binding.postModel = viewModel
        binding.lifecycleOwner = this
        binding.rcvPost.layoutManager = LinearLayoutManager(requireContext())
        binding.rcvPost.adapter = postAdapter

        viewModel.getPlants()
        observeApiState()

        return binding.root
    }

    private fun observeApiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.apiStateFlow.collect { state ->
                    when (state) {
                        is ApiState.Loading -> showLoadingState()
                        is ApiState.Success -> showSuccessState(state.data)
                        is ApiState.Failure -> showErrorState(state.error)
                        is ApiState.Empty -> hideLoadingState()
                    }
                }
            }
        }
    }

    private fun showSuccessState(data: List<Post>) {
        postAdapter.updateData(data)
    }

    private fun showLoadingState() {
        // Show loading state
    }

    private fun showErrorState(error: Throwable) {
        // Show error state
    }

    private fun hideLoadingState() {
        // Hide loading state
    }

    private val onItemClick = object : CallBack<Post>() {
        override fun onSuccess(t: Post) {
            // Handle item click
        }

        override fun onError(error: String?) {
            // Handle error
        }
    }
}
*/







package com.mrdev.androidtestassesment.post.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mrdev.androidtestassesment.MainActivity
import com.mrdev.androidtestassesment.R
import com.mrdev.androidtestassesment.data.util.ApiState
import com.mrdev.androidtestassesment.databinding.FragmentPostBinding
import com.mrdev.androidtestassesment.other.CallBack
import com.mrdev.androidtestassesment.post.postModel.Post
import com.mrdev.androidtestassesment.post.PostVM
import com.mrdev.androidtestassesment.post.adapter.PostAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PostsFragment : Fragment() {

    private val viewModel: PostVM by viewModels()
    private lateinit var binding: FragmentPostBinding
    private val postAdapter by lazy { PostAdapter(onItemClick) }
    private var isLoading = false
    private var isLastPage = false
    private var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(inflater, container, false)
        binding.postModel = viewModel
        binding.lifecycleOwner = this
        binding.rcvPost.layoutManager = LinearLayoutManager(requireContext())
        binding.rcvPost.adapter = postAdapter

        viewModel.getPosts()
        observeApiState()
        setupPaginationScrollListener()
        binding.refreshLayout.setOnRefreshListener {
            // Perform the refresh action here, such as reloading data from the ViewModel
            binding.progressbar.visibility = View.VISIBLE
            binding.rcvPost.visibility = View.GONE
            viewModel.getPosts()
        }

        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { postAdapter.setSearchQuery(it) }
                return true
            }
        })




        return binding.root
    }

    private fun observeApiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.apiStateFlow.collect { state ->
                    when (state) {
                        is ApiState.Loading -> showLoadingState()
                        is ApiState.Success -> showSuccessState(state.data)
                        is ApiState.Failure -> showErrorState(state.error)
                        is ApiState.Empty -> hideLoadingState()
                    }
                }
            }
        }
    }

    private fun showSuccessState(data: List<Post>) {

        binding.progressbar.visibility = View.GONE
        binding.rcvPost.visibility = View.VISIBLE


        binding.refreshLayout.isRefreshing = false
        postAdapter.updateData(data)
        isLoading = false
        currentPage++
    }

    private fun showLoadingState() {
        binding.progressbar.visibility = View.VISIBLE
        binding.rcvPost.visibility = View.GONE
        isLoading = true
    }

    private fun showErrorState(error: Throwable) {
        binding.refreshLayout.isRefreshing = false

        isLoading = false

        binding.progressbar.visibility = View.GONE
        binding.rcvPost.visibility = View.VISIBLE

        // Show error state
    }

    private fun hideLoadingState() {
        isLoading = false
        // Hide loading state
        binding.progressbar.visibility = View.GONE
        binding.rcvPost.visibility = View.VISIBLE
        binding.refreshLayout.isRefreshing = false
    }

    private fun setupPaginationScrollListener() {
        val layoutManager = binding.rcvPost.layoutManager as LinearLayoutManager
        binding.rcvPost.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                // Check if scrolling down
                if (dy > 0 && !isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE
                    ) {
                        loadMoreItems()
                    }
                }
            }
        })
    }


    private fun loadMoreItems() {
        isLoading = true
        viewModel.getPosts(currentPage)
    }

    private val onItemClick = object : CallBack<Post>() {
        override fun onSuccess(t: Post) {
            val frg = (requireActivity() as MainActivity)
            frg.navigateToFragment(PostDetailFragment.newInstance(t))
        }

        override fun onError(error: String?) {
            Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val PAGE_SIZE = 10 // Change this according to your pagination requirements
    }
}

