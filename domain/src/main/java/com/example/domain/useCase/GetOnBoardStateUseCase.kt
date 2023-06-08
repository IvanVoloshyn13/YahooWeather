package com.example.domain.useCase

import com.example.domain.repository.DataStoreRepository
import javax.inject.Inject

class GetOnBoardStateUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend fun getOnBoardState(): Boolean =
        dataStoreRepository.getOnBoardSate()
}