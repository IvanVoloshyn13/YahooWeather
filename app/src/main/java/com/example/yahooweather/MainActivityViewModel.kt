package com.example.yahooweather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCase.GetOnBoardStateUseCase
import com.example.yahooweather.utils.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val getOnBoardStateUseCase: GetOnBoardStateUseCase
) : ViewModel() {

    init {
        getOnBoardState()
    }


    private val _onBoardState = MutableStateFlow(Constants.Screens.WELCOME_SCREEN)
    val onBoardState = _onBoardState.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    fun getOnBoardState() {
        viewModelScope.launch {
            val result = getOnBoardStateUseCase.getOnBoardState()
            result.collect() { onBoardState ->
                if (onBoardState) {
                    _onBoardState.emit(Constants.Screens.MAIN_WEATHER_SCREEN)
                } else {
                    _onBoardState.emit(Constants.Screens.WELCOME_SCREEN)
                }
            }
            _isLoading.emit(false)
        }


    }
}


