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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.antonpa.sumgame.R
import com.antonpa.sumgame.databinding.FragmentGameBinding
import com.antonpa.sumgame.domain.entities.GameLevel
import com.antonpa.sumgame.domain.entities.GameResult
import com.antonpa.sumgame.presentation.viewmodels.GameFragmentViewModel
import com.antonpa.sumgame.presentation.viewmodels.GameFragmentViewModelFactory
import java.lang.RuntimeException


class GameFragment : Fragment() {

    private val navigationArgs by navArgs<GameFragmentArgs>()

    private val viewModelFactory by lazy {
        GameFragmentViewModelFactory(requireActivity().application, navigationArgs.gameLevel)
    }
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameFragmentViewModel::class.java]
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
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun observeLiveData() {
        with(viewModel) {
            gameResultLiveData.observe(viewLifecycleOwner) {
                runGameFinishedFragment(it)
            }
        }
    }

    private fun runGameFinishedFragment(gameResult: GameResult) {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameFinishedFragment(
                gameResult
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
