package com.mrdev.androidtestassesment.data.util


import com.mrdev.androidtestassesment.base.HiltPostItemClickListener
import com.mrdev.androidtestassesment.post.postModel.Post

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object AppModule {

    @Provides
    @Singleton
    fun provideOnItemClickListener(): HiltPostItemClickListener<Post> {
        return object : HiltPostItemClickListener<Post> {
            override fun onPostItemClick(item: Post) {
                // Toaster.shortToast(item.body)
            }
        }
    }
}
