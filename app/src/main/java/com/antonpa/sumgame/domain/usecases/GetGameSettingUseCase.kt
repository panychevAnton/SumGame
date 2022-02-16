package com.antonpa.sumgame.domain.usecases

import com.antonpa.sumgame.domain.entities.GameLevel
import com.antonpa.sumgame.domain.entities.GameSettings
import com.antonpa.sumgame.domain.repositories.GameRepository

class GetGameSettingUseCase(
    private val repository: GameRepository
) {

    private operator fun invoke(gameLevel: GameLevel): GameSettings =
        repository.getGameSetting(gameLevel)
}