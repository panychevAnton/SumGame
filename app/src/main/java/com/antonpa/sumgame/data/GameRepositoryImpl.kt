package com.antonpa.sumgame.data

import com.antonpa.sumgame.domain.entities.GameGoal
import com.antonpa.sumgame.domain.entities.GameLevel
import com.antonpa.sumgame.domain.entities.GameSettings
import com.antonpa.sumgame.domain.repositories.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl: GameRepository {

    private const val MIN_SUM_VALUE = 2
    private const val MIN_TERM_VALUE = 1

    override fun getGameGoal(maxSumValue: Int, countOfOptions: Int): GameGoal {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleTerm = Random.nextInt(MIN_TERM_VALUE, sum)
        val rightAnswer = sum - visibleTerm
        val termOptionsList = HashSet<Int>()
        termOptionsList.add(rightAnswer)
        while (termOptionsList.size < countOfOptions){
            termOptionsList.add(Random.nextInt(
                max(MIN_TERM_VALUE, rightAnswer - countOfOptions),
                min(maxSumValue, rightAnswer + countOfOptions)
            ))
        }
        return GameGoal(sum, visibleTerm, termOptionsList.toList())
    }

    override fun getGameSetting(gameLevel: GameLevel): GameSettings =
        when (gameLevel) {
            GameLevel.TEST -> GameSettings(
                10,
                2,
                50,
                8
            )
            GameLevel.EASY -> GameSettings(
                10,
                5,
                50,
                20
            )
            GameLevel.MEDIUM -> GameSettings(
                20,
                7,
                70,
                15
            )
            GameLevel.HARD -> GameSettings(
                30,
                9,
                90,
                10
            )
        }
}