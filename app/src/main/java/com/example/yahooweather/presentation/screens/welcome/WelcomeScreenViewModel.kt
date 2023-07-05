package com.example.yahooweather.presentation.screens.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.useCase.welcomeScreen.SaveOnBoardStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeScreenViewModel @Inject constructor(
    private val saveOnBoardStateUseCase: SaveOnBoardStateUseCase,

    ) : ViewModel() {

    fun saveOnBoardState(isComplete: Boolean) {
        viewModelScope.launch {
            saveOnBoardStateUseCase.saveOnBoardState(isComplete)
        }
    }
}