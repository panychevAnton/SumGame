package com.antonpa.sumgame.domain.repositories

import com.antonpa.sumgame.domain.entities.GameGoal
import com.antonpa.sumgame.domain.entities.GameLevel
import com.antonpa.sumgame.domain.entities.GameSettings

interface GameRepository {

    fun getGameGoal(
        maxSumValue: Int,
        countOfOptions: Int
    ): GameGoal

    fun getGameSetting(gameLevel: GameLevel): GameSettings
}