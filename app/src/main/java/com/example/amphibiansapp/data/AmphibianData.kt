package com.example.amphibiansapp.data

import kotlinx.serialization.SerialName

data class AmphibianData(
    val name:String,
    val type:String,
    val description:String,
    @SerialName("img_src") val imgSrc:String
)
