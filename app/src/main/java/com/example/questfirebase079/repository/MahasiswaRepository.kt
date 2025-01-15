package com.example.questfirebase079.repository

import com.example.questfirebase079.model.Mahasiswa
import kotlinx.coroutines.flow.Flow

interface MahasiswaRepository{
    suspend fun insertMhs(mahasiswa: Mahasiswa)

    //getALlmhs
    fun getAllMahasiswa(): Flow<List<Mahasiswa>>

    //getMhs
    fun getMahasiswa(nim: String): Flow<Mahasiswa>

    //deleteMhs
    suspend fun deleteMhs(mahasiswa: Mahasiswa)

    //updateMhs
    suspend fun updateMahasiswa(mahasiswa: Mahasiswa)

    fun getMhs(nim: String): Flow<Mahasiswa>
    suspend fun updateMhs(mahasiswa: Mahasiswa)
}