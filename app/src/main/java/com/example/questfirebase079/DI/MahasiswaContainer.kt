package com.example.questfirebase079.DI

import com.example.questfirebase079.repository.MahasiswaRepository
import com.example.questfirebase079.repository.NetworkMahasiswaRepository
import com.google.firebase.firestore.FirebaseFirestore


interface AppContainer{
    val mahasiswaRepository: MahasiswaRepository
}

class MahasiswaContainer: AppContainer{
    private val firestore : FirebaseFirestore = FirebaseFirestore.getInstance()
    override val mahasiswaRepository: MahasiswaRepository by lazy {
        NetworkMahasiswaRepository(firestore)
    }
}