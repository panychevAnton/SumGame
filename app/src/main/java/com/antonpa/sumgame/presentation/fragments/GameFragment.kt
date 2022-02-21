package com.antonpa.sumgame.presentation.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import com.antonpa.sumgame.R
import com.antonpa.sumgame.databinding.FragmentGameBinding
import com.antonpa.sumgame.domain.entities.GameLevel
import com.antonpa.sumgame.domain.entities.GameResult
import com.antonpa.sumgame.presentation.viewmodels.GameFragmentViewModel
import java.lang.RuntimeException


class GameFragment : Fragment() {

    private lateinit var gameLevel: GameLevel
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            AndroidViewModelFactory.getInstance(requireActivity().application)
        )[GameFragmentViewModel::class.java]
    }
    private val tvOptionsList by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding = null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getCorrectArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        setOnClockListenersToOptions()
        viewModel.startGame(gameLevel)
    }

    private fun setOnClockListenersToOptions() {
        tvOptionsList.forEach { option ->
            option.setOnClickListener {
                viewModel.onTermChosen(option.text.toString().toInt())
            }
        }
    }

    private fun observeLiveData() {
        with(viewModel) {
            gameGoalLiveData.observe(viewLifecycleOwner) {
                binding.tvSum.text = it.requiredSum.toString()
                binding.tvLeftNumber.text = it.visibleTerm.toString()
                tvOptionsList.forEachIndexed { i, tv ->
                    tv.text = it.termOptions[i].toString()
                }
            }

            gameTimerLiveData.observe(viewLifecycleOwner) {
                binding.tvTimer.text = it
            }

            correctAnswersPercentLiveData.observe(viewLifecycleOwner) {
                binding.progressBar.setProgress(it, true)
            }

            minPercentOfCorrectAnswersLiveData.observe(viewLifecycleOwner) {
                binding.progressBar.secondaryProgress = it
            }

            isNeededCountOfCorrectAnswersLiveData.observe(viewLifecycleOwner) {
                binding.tvAnswersProgress.setTextColor(getColorByCondition(it))
            }

            isNeededPercentOfCorrectAnswersLiveData.observe(viewLifecycleOwner) {
                binding.progressBar.progressTintList =
                    ColorStateList.valueOf(getColorByCondition(it))
            }

            gameAnswersInfoLiveData.observe(viewLifecycleOwner) {
                binding.tvAnswersProgress.text = it
            }

            gameResultLiveData.observe(viewLifecycleOwner) {
                runGameFinishedFragment(it)
            }
        }
    }

    private fun runGameFinishedFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.getNewInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    private fun getColorByCondition(trueCondition: Boolean): Int {
        val colorResId = if (trueCondition)
            android.R.color.holo_green_light
        else
            android.R.color.holo_red_light
        return ContextCompat.getColor(requireContext(), colorResId)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getCorrectArgs() {
        requireArguments().getParcelable<GameLevel>(KEY_GAME_LEVEL)?.let {
            gameLevel = it
        }
    }

    companion object {

        const val NAME = "GameFragment"

        private const val KEY_GAME_LEVEL = "game_level"

        fun getNewInstance(gameLevel: GameLevel): GameFragment =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_LEVEL, gameLevel)
                }
            }
    }
}
