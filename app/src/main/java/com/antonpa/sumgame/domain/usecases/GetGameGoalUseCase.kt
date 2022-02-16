package com.antonpa.sumgame.domain.usecases

import com.antonpa.sumgame.domain.entities.GameGoal
import com.antonpa.sumgame.domain.repositories.GameRepository

class GetGameGoalUseCase(
    private val repository: GameRepository
) {
    operator fun invoke(maxSumValue: Int): GameGoal =
        repository.getGameGoal(maxSumValue, COUNT_OF_OPTIONS)

    private companion object {
        const val COUNT_OF_OPTIONS = 6
    }
}