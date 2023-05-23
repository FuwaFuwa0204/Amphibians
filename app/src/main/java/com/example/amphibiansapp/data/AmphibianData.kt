package com.example.amphibiansapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//꼭 추가하기
@Serializable
data class AmphibianData(
    val name:String,
    val type:String,
    val description:String,
    @SerialName("img_src") val imgSrc:String
)
