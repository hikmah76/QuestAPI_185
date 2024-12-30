package com.example.meet12.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.meet12.MahasiswaApplications

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(aplikasiMahasiswa().container.MahasiswaRepository)
        }
        initializer {
            InsertViewModel(aplikasiMahasiswa().container.MahasiswaRepository)
        }
        initializer {
            DetailViewModel(
                aplikasiMahasiswa().container.MahasiswaRepository,
                createSavedStateHandle()
            )
        }
        initializer {
            UpdateViewModel(
                aplikasiMahasiswa().container.MahasiswaRepository,
                createSavedStateHandle()
            )
        }
    }

    fun CreationExtras.aplikasiMahasiswa(): MahasiswaApplications =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MahasiswaApplications)
}
