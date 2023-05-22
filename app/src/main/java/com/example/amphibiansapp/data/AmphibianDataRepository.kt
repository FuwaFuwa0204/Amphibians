package com.example.amphibiansapp.data

import com.example.amphibiansapp.network.AmphibianService

interface AmphibianDataRepository {

    suspend fun getAmphibians(): List<AmphibianData>
}

//인터페이스를 상속받음
class DefaultAmphibianDataRepository(private val amphibianService: AmphibianService):AmphibianDataRepository {
    override suspend fun getAmphibians(): List<AmphibianData> = amphibianService.getAmphibians()
}