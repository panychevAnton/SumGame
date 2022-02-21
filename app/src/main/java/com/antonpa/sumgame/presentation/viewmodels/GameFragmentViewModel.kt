package com.antonpa.sumgame.presentation.viewmodels

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.antonpa.sumgame.R
import com.antonpa.sumgame.data.GameRepositoryImpl
import com.antonpa.sumgame.domain.entities.GameGoal
import com.antonpa.sumgame.domain.entities.GameLevel
import com.antonpa.sumgame.domain.entities.GameResult
import com.antonpa.sumgame.domain.entities.GameSettings
import com.antonpa.sumgame.domain.usecases.GetGameGoalUseCase
import com.antonpa.sumgame.domain.usecases.GetGameSettingUseCase

class GameFragmentViewModel(
    private val application: Application,
    private val gameLevel: GameLevel
) : ViewModel() {

    private val repository = GameRepositoryImpl
    private lateinit var gameSettings: GameSettings
    private var timer: CountDownTimer? = null

    private val _gameGoalLiveData = MutableLiveData<GameGoal>()
    val gameGoalLiveData: LiveData<GameGoal>
        get() = _gameGoalLiveData

    private val _gameTimerLiveData = MutableLiveData<String>()
    val gameTimerLiveData: LiveData<String>
        get() = _gameTimerLiveData

    private val _correctAnswersPercentLiveData = MutableLiveData<Int>()
    val correctAnswersPercentLiveData: LiveData<Int>
        get() = _correctAnswersPercentLiveData

    private val _gameAnswersInfoLiveData = MutableLiveData<String>()
    val gameAnswersInfoLiveData: LiveData<String>
        get() = _gameAnswersInfoLiveData

    private val _minPercentOfCorrectAnswersLiveData = MutableLiveData<Int>()
    val minPercentOfCorrectAnswersLiveData: LiveData<Int>
        get() = _minPercentOfCorrectAnswersLiveData

    private val _isNeededPercentOfCorrectAnswersLiveData = MutableLiveData<Boolean>()
    val isNeededPercentOfCorrectAnswersLiveData: LiveData<Boolean>
        get() = _isNeededPercentOfCorrectAnswersLiveData

    private val _isNeededCountOfCorrectAnswersLiveData = MutableLiveData<Boolean>()
    val isNeededCountOfCorrectAnswersLiveData: LiveData<Boolean>
        get() = _isNeededCountOfCorrectAnswersLiveData

    private val _gameResultLiveData = MutableLiveData<GameResult>()
    val gameResultLiveData: LiveData<GameResult>
        get() = _gameResultLiveData

    private val getGameSettingsUseCase = GetGameSettingUseCase(repository)
    private val getGameGoalUseCase = GetGameGoalUseCase(repository)

    private var counterOfCorrectAnswers = 0
    private var counterOfAllAnswers = 0

    init {
        getGameSettings(gameLevel)
        runGameTimer()
        updateGameProgress()
        getGameGoal()
    }

    fun onTermChosen(chosenTerm: Int) {
        checkTerm(chosenTerm)
        updateGameProgress()
        getGameGoal()
    }

    private fun updateGameProgress() {
        val percent = getPercentOfCorrectAnswers()
        _correctAnswersPercentLiveData.value = percent
        _gameAnswersInfoLiveData.value = String.format(
            application.resources.getString(R.string.progress_answers),
            counterOfCorrectAnswers,
            gameSettings.minCountOfRightAnswers
        )
        _isNeededCountOfCorrectAnswersLiveData.value =
            counterOfCorrectAnswers >= gameSettings.minCountOfRightAnswers
        _isNeededPercentOfCorrectAnswersLiveData.value =
            percent >= gameSettings.minPercentOfRightAnswers
    }

    private fun getPercentOfCorrectAnswers() =
        if (counterOfAllAnswers == 0)
            0
        else
            ((counterOfCorrectAnswers / counterOfAllAnswers.toDouble()) * 100).toInt()

    private fun checkTerm(chosenTerm: Int) {
        if (chosenTerm == gameGoalLiveData.value?.rightTerm)
            counterOfCorrectAnswers++
        counterOfAllAnswers++
    }

    private fun runGameTimer() {
        timer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * SECONDS_TO_MILLIS_MULTIPLIER,
            SECONDS_TO_MILLIS_MULTIPLIER
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _gameTimerLiveData.value = millisUntilFinished.toTime()
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun Long.toTime(): String {
        val seconds = this / SECONDS_TO_MILLIS_MULTIPLIER
        val minutes = seconds / SECONDS_TO_MINUTES_DIVIDER
        val leftSeconds = seconds - (minutes * SECONDS_TO_MINUTES_DIVIDER)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    private fun getGameSettings(gameLevel: GameLevel) {
        gameSettings = getGameSettingsUseCase(gameLevel)
        _minPercentOfCorrectAnswersLiveData.value = gameSettings.minPercentOfRightAnswers
    }

    private fun getGameGoal() {
        _gameGoalLiveData.value = getGameGoalUseCase(gameSettings.maxSumValue)
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    private fun finishGame() {
        _gameResultLiveData.value = GameResult(
            isNeededCountOfCorrectAnswersLiveData.value == true &&
                    isNeededPercentOfCorrectAnswersLiveData.value == true,
            counterOfCorrectAnswers,
            counterOfAllAnswers,
            gameSettings
        )
    }

    companion object {

        private const val SECONDS_TO_MILLIS_MULTIPLIER = 1000L
        private const val SECONDS_TO_MINUTES_DIVIDER = 60
    }
}