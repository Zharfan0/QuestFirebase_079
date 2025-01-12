package com.example.questfirebase079

import android.app.Application
import com.example.questfirebase079.DI.AppContainer
import com.example.questfirebase079.DI.MahasiswaContainer

class MahasiswaApplication: Application(){
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = MahasiswaContainer()
    }
}