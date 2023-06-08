package com.example.domain.useCase.welcomeScreen

import com.example.domain.repository.DataStoreRepository
import javax.inject.Inject

class SaveOnBoardStateUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend fun saveOnBoardState(isComplete: Boolean) =
        dataStoreRepository.saveOnBoardSate(isComplete = isComplete)
}