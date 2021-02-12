//package com.example.myapplication.ui.base
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.example.myapplication.data.repository.Repository
//import com.example.myapplication.ui.main.viewmodel.DetailViewModel
//import com.example.myapplication.ui.main.viewmodel.MainViewModel
//import org.koin.android.ext.android.inject
//
//class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
//            return MainViewModel(Repository(apiHelper)) as T
//        }
//        else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
//            return DetailViewModel(Repository(apiHelper)) as T
//        }
//        throw IllegalArgumentException("Unknown class name")
//    }
//
//}