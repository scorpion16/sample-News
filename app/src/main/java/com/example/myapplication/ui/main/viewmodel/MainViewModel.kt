package com.example.myapplication.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.myapplication.data.repository.Repository
import com.example.myapplication.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newFixedThreadPoolContext
import java.util.*


class MainViewModel(private val repository: Repository) : ViewModel() {

    val isProgressing  = MutableLiveData<Boolean>(false)
    private var datePoint : Long = 0;

    companion object {
        const val ONE_DAY : Long = 1000 * 60 * 60 * 1;
    }

    fun getLastMovie() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = listOf(repository.fetchNews("bitcoin" , "2021-01-01" , "2021-02,01")) ))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
    fun fetchList(fetchMore:Boolean = true) = liveData(Dispatchers.IO) {
        if (fetchMore || datePoint == 0L) {
            datePoint = calculateDatePoint();
        }
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(repository.fetchNews("Bitcoin" , getDateString(datePoint - ONE_DAY) , getDateString(datePoint))))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    private fun getDateString(timestamp: Long): String {
        var cal = Calendar.getInstance()
        cal.timeInMillis = timestamp
        return "%04d-%02d-%02d".format(Locale.US , cal.get(Calendar.YEAR) , cal.get(Calendar.MONTH)+1 , cal.get(Calendar.DAY_OF_MONTH))
    }

    private fun calculateDatePoint(): Long {
        if(datePoint ==0L)
            return Date().time;
        else
        {
            return datePoint - ONE_DAY;
        }
    }
}