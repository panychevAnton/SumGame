package com.antonpa.sumgame.domain.entities

data class GameGoal(
    val requiredSum: Int,
    val visibleTerm: Int,
    val termOptions: List<Int>
) {
    val rightTerm: Int
        get() = requiredSum - visibleTerm
}
