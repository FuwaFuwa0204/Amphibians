package com.example.amphibiansapp.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibiansapp.ui.AmphibianApplication
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface AmphibianUiState {
    data class Success(
        val items: List<AmphibianData>
        ) : AmphibianUiState
    object Error : AmphibianUiState
    object Loading : AmphibianUiState
}



class AmphibianViewModel(private val amphibianDataRepository:AmphibianDataRepository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var amphibianUiState: AmphibianUiState by mutableStateOf(AmphibianUiState.Loading)
    private set

    init {
        getAmphibians()
    }

    fun getAmphibians(){
        viewModelScope.launch {
            amphibianUiState = AmphibianUiState.Loading
            amphibianUiState = try {

                AmphibianUiState.Success(
                    //"Success: ${listResult.size} Mars photos retrieved"
                    //"First Mars image URL: ${result.imgSrc}"
                    amphibianDataRepository.getAmphibians()
                )
            } catch (e: IOException) {
                AmphibianUiState.Error
            } catch (e: HttpException) {
                AmphibianUiState.Error
            }
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AmphibianApplication)
                val amphibianDataRepository = application.container.amphibianDataRepository
                AmphibianViewModel(amphibianDataRepository = amphibianDataRepository)
            }
        }
    }

}




