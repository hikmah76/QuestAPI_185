package com.example.meet12.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meet12.model.Mahasiswa
import com.example.meet12.repository.MahasiswaRepository
import kotlinx.coroutines.launch

class UpdateViewModel(
    private val mahasiswaRepository: MahasiswaRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val nimParameter: String = checkNotNull(savedStateHandle["nim"])

    var updateUiState: InsertUiState by mutableStateOf(InsertUiState())
        private set

    var errorMessage: String? by mutableStateOf(null)
        private set

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                val result = mahasiswaRepository.getMahasiswaByNim(nimParameter)
                updateUiState = InsertUiState(insertUiEvent = result.toInsertUiEvent())
            } catch (e: Exception) {
                errorMessage = "Failed to load data: ${e.message}"
            }
        }
    }

    fun updateState(insertUiEvent: InsertUiEvent) {
        updateUiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    fun updateData() {
        viewModelScope.launch {
            try {
                mahasiswaRepository.updateMahasiswa(nimParameter, updateUiState.insertUiEvent.toMhs())
            } catch (e: Exception) {
                errorMessage = "Failed to update: ${e.message}"
                throw e
            }
        }
    }

    fun resetSnackBarMessage() {
        errorMessage = null
    }
}

