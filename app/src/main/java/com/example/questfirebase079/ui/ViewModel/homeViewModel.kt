package com.example.questfirebase079.ui.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questfirebase079.model.Mahasiswa
import com.example.questfirebase079.repository.MahasiswaRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel (
    private val repoMhs: MahasiswaRepository
) : ViewModel() {

    var mhsUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getMhs()
    }

    fun getMhs(){
        viewModelScope.launch {
            repoMhs.getAllMahasiswa().onStart {
                mhsUiState = HomeUiState.Loading
            }
                .catch {
                    mhsUiState = HomeUiState.Error(e = it)
                }
                .collect {
                    mhsUiState = if(it.isEmpty()) {
                        HomeUiState.Error(Exception("Belum ada data Mahasiswa"))
                    }else {
                        HomeUiState.Success(it)
                        //it = data mahasiswa
                    }
                }
        }
    }

    fun deleteMhs(mahasiswa: Mahasiswa){
        viewModelScope.launch {
            try {
                repoMhs.deleteMhs(mahasiswa)
            } catch (e: Exception){
                mhsUiState = HomeUiState.Error(e)
            }
        }
    }
}



sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val data: List<Mahasiswa>) : HomeUiState()
    data class Error(val e: Throwable) : HomeUiState()
}