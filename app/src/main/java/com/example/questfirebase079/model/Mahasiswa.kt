package com.example.questfirebase079.model

data class Mahasiswa (
    val nim: String,
    val nama: String,
    val jenis_kelamin: String,
    val alamat: String,
    val kelas: String,
    val angkatan: String
){
    constructor(): this("20220140079","Zharfan","Laki-laki","Jakarta","B","2022")
}