package com.example.domain.useCase

import com.example.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOnBoardStateUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend fun getOnBoardState(): Flow<Boolean> =
        dataStoreRepository.getOnBoardSate()
}