package com.example.project.ui.timeline

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.project.model.Post
import com.example.project.repositoty.timeline.TimelineRepository
import com.google.firebase.firestore.ktx.toObject

class TimelineViewModel: ViewModel() {
    private val repository = TimelineRepository()

    private val _posts = MutableLiveData<List<Post>>() // repository와만 통신하며 데이터를 저장하는 변수
    var posts: LiveData<List<Post>> = _posts // ui에서 사용되는 변수 (observe, binding 등)

    init {
        loadPosts()
    }


    private fun loadPosts() {
        val queryPostKeys = repository.getPostKeys()
        queryPostKeys.addOnSuccessListener { documents ->
            val tempPosts = mutableListOf<Post>()
            for(document in documents) {
                val postValue = document.toObject<Post>()
                Log.d("exists", "dd ${postValue.exists}")
                tempPosts.add(postValue)
            }

            _posts.value = tempPosts
        }
    }
}