package com.antonpa.sumgame.domain.entities

import java.io.Serializable

data class GameResult(
    val isWinner: Boolean,
    val countOfRightAnswers: Int,
    val countOfAllAnswers: Int,
    val gameSettings: GameSettings
): Serializable
