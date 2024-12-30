package com.example.meet12.ui.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meet12.model.Mahasiswa
import com.example.meet12.repository.MahasiswaRepository
import kotlinx.coroutines.launch


class DetailViewModel(
    private val mahasiswaRepository: MahasiswaRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val nimParameter: String = checkNotNull(savedStateHandle["nim"])

    var detailUiState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    init {
        getMhsById()
    }

    private fun getMhsById() {
        viewModelScope.launch {
            detailUiState = DetailUiState.Loading
            detailUiState = try {
                val result = mahasiswaRepository.getMahasiswaByNim(nimParameter)
                DetailUiState.Success(result)
            } catch (e: Exception) {
                DetailUiState.Error
            }
        }
    }

    fun deleteMhs() {
        viewModelScope.launch {
            try {
                mahasiswaRepository.deleteMahasiswa(nimParameter)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

sealed class DetailUiState {
    data class Success(val mahasiswa: Mahasiswa) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}