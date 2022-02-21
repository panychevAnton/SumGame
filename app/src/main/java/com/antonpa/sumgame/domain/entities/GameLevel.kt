package com.antonpa.sumgame.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class GameLevel: Parcelable {
    TEST, EASY, MEDIUM, HARD
}