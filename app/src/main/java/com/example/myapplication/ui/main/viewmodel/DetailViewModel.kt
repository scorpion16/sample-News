package com.example.myapplication.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.myapplication.data.model.Article
import com.example.myapplication.data.repository.Repository
import com.example.myapplication.utils.Resource
import kotlinx.coroutines.Dispatchers


class DetailViewModel(private val repository: Repository) : ViewModel() {

    val isProgressing  = MutableLiveData<Boolean>(false)
    val details  = MutableLiveData<Article>()

    fun getDetail(movieId:Int) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
//            emit(Resource.success(data = repository.getMovieDetail(movieId) ))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}