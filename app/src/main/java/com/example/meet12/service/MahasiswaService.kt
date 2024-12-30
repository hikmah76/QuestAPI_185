package com.example.meet12.service

import com.example.meet12.model.Mahasiswa
import com.example.meet12.repository.MahasiswaRepository
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface MahasiswaService {
    // Mengatur header untuk setiap request agar menerima dan mengirimkan data dalam format JSON
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("bacamahasiswa.php")
    suspend fun getAllMahasiswa(): List<Mahasiswa>

    @GET("baca1mahasiswa.php/{nim}")
    suspend fun getMahasiswabyNim(@Query("nim") nim: String): Mahasiswa

    @POST("insertmahasiswa.php")
    suspend fun insertMahasiswa(@Body mahasiswa: Mahasiswa): Response

    @PUT("editmahasiswa.php")
    suspend fun updateMahasiswa(@Query("nim") nim: String, @Body mahasiswa: Mahasiswa)

    @DELETE("deletemahasiswa.php")
    suspend fun deleteMahasiswa(@Query("nim") nim: String): Response
}


