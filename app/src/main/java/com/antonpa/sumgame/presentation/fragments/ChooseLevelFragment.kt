package com.antonpa.sumgame.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.antonpa.sumgame.R
import com.antonpa.sumgame.databinding.FragmentChooseLevelBinding
import com.antonpa.sumgame.domain.entities.GameLevel

class ChooseLevelFragment : Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            buttonLevelTest.setOnClickListener {
                runChosenLevelGame(GameLevel.TEST)
            }
            buttonLevelEasy.setOnClickListener {
                runChosenLevelGame(GameLevel.EASY)
            }
            buttonLevelMedium.setOnClickListener {
                runChosenLevelGame(GameLevel.MEDIUM)
            }
            buttonLevelHard.setOnClickListener {
                runChosenLevelGame(GameLevel.HARD)
            }
        }
    }

    private fun runChosenLevelGame(gameLevel: GameLevel) {
        findNavController().navigate(
            ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(
                gameLevel
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
