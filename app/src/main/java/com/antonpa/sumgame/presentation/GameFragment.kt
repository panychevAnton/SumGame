package com.antonpa.sumgame.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.antonpa.sumgame.R
import com.antonpa.sumgame.databinding.FragmentGameBinding
import com.antonpa.sumgame.domain.entities.GameLevel
import com.antonpa.sumgame.domain.entities.GameResult
import com.antonpa.sumgame.domain.entities.GameSettings
import java.lang.RuntimeException


class GameFragment : Fragment() {

    private lateinit var gameLevel: GameLevel

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
        binding.tvSum.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(
                    R.id.main_container, GameFinishedFragment.getNewInstance(
                        GameResult(
                            true,
                            0,
                            0,
                            GameSettings(
                                0, 0, 0, 0
                            )
                        )
                    )
                )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getCorrectArgs() {
        gameLevel = requireArguments().getSerializable(KEY_GAME_LEVEL) as GameLevel
    }

    companion object {

        const val NAME = "GameFragment"

        private const val KEY_GAME_LEVEL = "game_level"

        fun getNewInstance(gameLevel: GameLevel): GameFragment =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_GAME_LEVEL, gameLevel)
                }
            }
    }
}
