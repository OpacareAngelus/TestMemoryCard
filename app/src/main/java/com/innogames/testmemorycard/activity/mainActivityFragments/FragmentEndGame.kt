package com.innogames.testmemorycard.activity.mainActivityFragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.innogames.testmemorycard.R
import com.innogames.testmemorycard.architecture.BaseFragment
import com.innogames.testmemorycard.databinding.FragmentEndGameBinding


class FragmentEndGame : BaseFragment<FragmentEndGameBinding>(FragmentEndGameBinding::inflate) {

    private val args: FragmentGameArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showResult()
        setListeners()
    }

    private fun showResult() {
        with(binding) {
            tvRoundsSpent.text = getString(R.string.rounds_spent_s).format(args.rounds)
            tvTimeSpent.text = getString(R.string.seconds_spent_s).format(args.time)
        }
    }

    override fun setListeners() {
        binding.btnBackToMenu.setOnClickListener {
            findNavController().navigate(FragmentEndGameDirections.actionFragmentEndGameToFragmentMainMenu())
        }
    }

    override fun setObservers() {
    }
}