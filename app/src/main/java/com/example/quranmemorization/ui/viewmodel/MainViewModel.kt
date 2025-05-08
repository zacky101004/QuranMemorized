package com.example.quranmemorization.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _surahList = MutableLiveData<List<String>>(
        listOf(
            "Al-Fatihah",
            "An-Nas",
            "Al-Falaq",
            "Al-Ikhlas",
            "Al-Lahab",
            "asep"
        )
    )
    val surahList: LiveData<List<String>> = _surahList
}