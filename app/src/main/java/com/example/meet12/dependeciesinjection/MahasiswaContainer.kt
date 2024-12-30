package com.example.meet12.dependeciesinjection

import com.example.meet12.repository.MahasiswaRepository
import com.example.meet12.repository.NetworkMahasiswaRepository
import com.example.meet12.service.MahasiswaService

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface
AppContainer {
    val MahasiswaRepository: MahasiswaRepository
}

class MahasiswaContainer :
    AppContainer {
    // Base URL untuk koneksi ke server
    private val baseUrl = "http://10.0.2.2:8000/umyTI/"

    //localhost diganti ip kalo run di hp
    private val json =
        Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(
            json.asConverterFactory(
                "application/json".toMediaType()
            )
        )
        .baseUrl(baseUrl).build()
    private val mahasiswaService:
            MahasiswaService by
    lazy {
        retrofit.create(
            MahasiswaService::class.java
        )
    }
    override val MahasiswaRepository:
            MahasiswaRepository by
    lazy {
        NetworkMahasiswaRepository(mahasiswaService)
    }
}