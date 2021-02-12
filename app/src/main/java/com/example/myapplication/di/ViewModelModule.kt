package com.example.myapplication.di

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.main.viewmodel.DetailViewModel
import com.example.myapplication.ui.main.viewmodel.MainViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { MainViewModel(get()) }
    factory { DetailViewModel(get()) }
}
