package com.example.meet12.ui.viewmodel

import android.provider.Contacts.Intents.Insert
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.meet12.model.Mahasiswa
import com.example.meet12.repository.MahasiswaRepository
import kotlinx.coroutines.launch


class InsertViewModel (private val mhs: MahasiswaRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertUiState())

    //Memperbarui state berdasarkan input pengguna
    fun updateInsertMhsState(insertUiEvent: InsertUiEvent) { //event: suatu kejadian/aksi, state: hasil dari aksi/perubahan
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertMhs(){
        viewModelScope.launch {
            try {
                mhs.insertMahasiswa(uiState.insertUiEvent.toMhs())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }


}
// untuk membungkus formerrorstate, mahasiswaevent, snackbarmessage dalam suatu data class
data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()


)

// memberikan sebuah validasi apakah textfield sudah sesuai atau belum


//data class variabel yang menyimpan data input form
data class InsertUiEvent( // menyimpan data dari sebuah textfield
    val nim: String = "",
    val nama: String = "",
    val jenisKelamin: String = "",
    val alamat: String = "",
    val kelas: String = "",
    val angkatan: String = ""
)

//menyimpan input form ke dalam entity
fun InsertUiEvent.toMhs(): Mahasiswa = Mahasiswa(
    nim = nim, //yang kiri adalah entitas yang kanan adalah variabel yang ada di MahasiswaEvent
    nama = nama,
    jenisKelamin = jenisKelamin,
    alamat = alamat,
    kelas = kelas,
    angkatan = angkatan
)

fun Mahasiswa.toUiStateMhs(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Mahasiswa.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    nim = nim, //yang kiri adalah entitas yang kanan adalah variabel yang ada di MahasiswaEvent
    nama = nama,
    jenisKelamin = jenisKelamin,
    alamat = alamat,
    kelas = kelas,
    angkatan = angkatan
)

