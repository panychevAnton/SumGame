package com.antonpa.sumgame.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.antonpa.sumgame.domain.entities.GameLevel
import java.lang.RuntimeException

class GameFragmentViewModelFactory(
    private val application: Application,
    private val gameLevel: GameLevel
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
         if (modelClass.isAssignableFrom(GameFragmentViewModel::class.java))
             return GameFragmentViewModel(application, gameLevel) as T
         else throw RuntimeException("Unknown view model $modelClass")
    }
}