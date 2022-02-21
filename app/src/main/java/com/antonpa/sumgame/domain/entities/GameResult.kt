package com.antonpa.sumgame.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult(
    val isWinner: Boolean,
    val countOfRightAnswers: Int,
    val countOfAllAnswers: Int,
    val gameSettings: GameSettings
): Parcelable
