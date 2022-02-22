package com.antonpa.sumgame.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.antonpa.sumgame.R
import com.antonpa.sumgame.databinding.FragmentGameFinishedBinding
import com.antonpa.sumgame.domain.entities.GameResult


class GameFinishedFragment : Fragment() {

    private val navigationArgs by navArgs<GameFinishedFragmentArgs>()

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.gameResult = navigationArgs.gameResult
        binding.buttonRetry.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getPercentOfCorrectAnswers() = with(navigationArgs.gameResult) {
        if (countOfAllAnswers == 0)
            0
        else
            ((countOfRightAnswers / countOfAllAnswers.toDouble()) * 100).toInt()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
