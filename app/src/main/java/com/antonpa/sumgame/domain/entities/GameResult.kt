package com.antonpa.sumgame.domain.entities

data class GameResult(
    val isWinner: Boolean,
    val countOfRightAnswers: Int,
    val countOfAllAnswers: Int,
    val gameSettings: GameSettings
)
