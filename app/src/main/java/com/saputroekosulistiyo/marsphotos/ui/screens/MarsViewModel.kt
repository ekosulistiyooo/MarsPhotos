
package com.saputroekosulistiyo.marsphotos.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saputroekosulistiyo.marsphotos.model.MarsPhoto
import com.saputroekosulistiyo.marsphotos.network.MarsApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

//Status UI untuk layar Home
sealed interface MarsUiState {
    data class Success(val photos: String) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}

class MarsViewModel : ViewModel() {
    // State yang dapat diubah (mutable) yang menyimpan status permintaan terbaru
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set

    // Panggil getMarsPhotos() saat inisialisasi agar kita dapat segera menampilkan status.
    init {
        getMarsPhotos()
    }

    // Mengambil informasi foto Mars dari layanan API Mars Retrofit dan memperbarui
    // [MarsPhoto] [List] [MutableList].
    fun getMarsPhotos() {
        viewModelScope.launch {
            marsUiState = MarsUiState.Loading
            marsUiState = try {
                val listResult = MarsApi.retrofitService.getPhotos()
                MarsUiState.Success(
                    "Success: ${listResult.size} Mars photos retrieved"
                )
            } catch (e: IOException) {
                MarsUiState.Error
            } catch (e: HttpException) {
                MarsUiState.Error
            }
        }
    }
}
