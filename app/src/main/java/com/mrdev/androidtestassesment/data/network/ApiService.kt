package com.mrdev.androidtestassesment.data.network

import com.mrdev.androidtestassesment.post.postModel.Post
import retrofit2.http.*


/**
 * Changes has been done on 21 March 2024 by MrDev(Mahesh)
 *
 * */
interface ApiService {

    @GET("/Posts")
    suspend fun getPostsList(@Query("page") page: Int): List<Post>
}







