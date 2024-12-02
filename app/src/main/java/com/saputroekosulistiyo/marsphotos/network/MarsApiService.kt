
package com.saputroekosulistiyo.marsphotos.network

import com.saputroekosulistiyo.marsphotos.model.MarsPhoto
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

    private const val BASE_URL =
        "https://android-kotlin-fun-mars-server.appspot.com"

    // Gunakan builder Retrofit untuk membangun objek retrofit dengan menggunakan konverter kotlinx.serialization
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(BASE_URL)
        .build()

    // Objek layanan Retrofit untuk membuat panggilan API
    interface MarsApiService {
        @GET("photos")
        suspend fun getPhotos(): List<MarsPhoto>
    }

    // Objek Api publik yang menyediakan layanan Retrofit yang diinisialisasi secara lazy
    object MarsApi {
        val retrofitService: MarsApiService by lazy {
            retrofit.create(MarsApiService::class.java)
        }
}
