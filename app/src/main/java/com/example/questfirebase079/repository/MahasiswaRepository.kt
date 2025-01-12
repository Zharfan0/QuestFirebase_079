package com.example.questfirebase079.repository

import com.example.questfirebase079.model.Mahasiswa
import kotlinx.coroutines.flow.Flow

interface MahasiswaRepository{
    suspend fun getMahasiswa(): Flow<List<Mahasiswa>>


}