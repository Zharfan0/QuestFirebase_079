package com.example.questfirebase079.ui.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questfirebase079.model.Mahasiswa
import com.example.questfirebase079.repository.MahasiswaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val mahasiswa: Mahasiswa) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}

class DetailViewModel(private val repository: MahasiswaRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState

    fun getMhs(nim: String) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            repository.getMhs(nim)
                .catch { exception ->
                    _uiState.value = DetailUiState.Error("Error: ${exception.message}")
                }
                .collect { mahasiswa ->
                    _uiState.value = DetailUiState.Success(mahasiswa)
                }
        }
    }
}