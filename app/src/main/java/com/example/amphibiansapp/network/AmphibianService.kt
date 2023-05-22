package com.example.amphibiansapp.network

import com.example.amphibiansapp.data.AmphibianData
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL =
    "https://android-kotlin-fun-mars-server.appspot.com/amphibians"
//객체 빌드
private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

//Retrofit이 HTTP 요청을 사용하여 웹 서버와 통신하는 방법을 정의
interface AmphibianService {
    //엔드포인트 /photos
    @GET("amphibians")
    suspend fun getAmphibians(): List<AmphibianData>
}

//Retrofit 공개 객체 정의 -> 서비스 초기화
object AmphibianApi {
    //늦은 초기화 by lazy => by lazy로 정의한 내용은 늦게 초기화하고 프로퍼티 사용시 초기화한다.
    val retrofitService : AmphibianService by lazy {
        retrofit.create(AmphibianService::class.java)
    }
}