package com.innogames.testmemorycard.activity.mainActivityFragments

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.innogames.testmemorycard.R
import com.innogames.testmemorycard.architecture.BaseFragment
import com.innogames.testmemorycard.databinding.FragmentGameBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class FragmentGame : BaseFragment<FragmentGameBinding>(FragmentGameBinding::inflate) {

    private val viewModel: FragmentGameViewModel by viewModels()
    private lateinit var cards: List<ImageView>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setField()
        setListeners()
        setObservers()
    }

    private fun init() {
        initCardsList()
        initImagesList()
        viewModel.startTimer()
    }

    private fun initCardsList() {
        with(binding) {
            cards = listOf(
                ivFirstCard, ivSecondCard, ivThirdCard, ivFourthCard
            )
        }
    }

    private fun setField() {
        cards.forEach {
            val image = viewModel.images.value.random()
            it.setImageResource(image)
            viewModel.setImagesList(viewModel.images.value.minus(image))
        }
    }

    private fun initImagesList() {
        viewModel.setImagesList(
            listOf(
                R.drawable.blue,
                R.drawable.blue,
                R.drawable.turquoise,
                R.drawable.turquoise
            ).apply { shuffled() }
        )
    }

    override fun setListeners() {
        with(binding) {
            btnBackToMenu.setOnClickListener {
                findNavController().navigate(FragmentGameDirections.actionFragmentGameToFragmentMainMenu())
            }
            btnRestart.setOnClickListener {
                findNavController().navigate(FragmentGameDirections.actionFragmentGameSelf("", ""))
            }
        }
        cards.forEach { card ->
            card.setOnClickListener {
                card.foreground = null
                if (viewModel.firstTakenCard.value == null) {
                    viewModel.setFirstTakenCard(card)
                } else {
                    viewModel.setSecondTakenCard(card)
                }
            }
        }
    }

    override fun setObservers() {
        with(binding) {
            viewModel.round.onEach {
                tvRoundCounter.text = getString(R.string.rounds_s).format(it)
            }.launchIn(lifecycleScope)
            viewModel.secondTakenCard.onEach {
                if (it != null &&
                    (it.drawable as BitmapDrawable).bitmap ==
                    (viewModel.firstTakenCard.value!!.drawable as BitmapDrawable).bitmap
                ) {
                    viewModel.firstTakenCard.value!!.isEnabled = false
                    viewModel.secondTakenCard.value!!.isEnabled = false
                    Toast.makeText(requireContext(), "+5 points", Toast.LENGTH_SHORT).show()
                    viewModel.changeScore()
                    viewModel.setNullTakenCards()
                    viewModel.changeRound()
                } else {
                    delay(DELAY_SHOWING_WRONG_ANSWER)
                    if (it != null) {
                        viewModel.firstTakenCard.value!!.foreground =
                            AppCompatResources.getDrawable(
                                requireContext(),
                                R.drawable.frame_rounded_light_to_mid
                            )
                        viewModel.secondTakenCard.value!!.foreground =
                            AppCompatResources.getDrawable(
                                requireContext(),
                                R.drawable.frame_rounded_light_to_mid
                            )
                        viewModel.changeRound()
                    }
                    viewModel.setNullTakenCards()
                }
            }.launchIn(lifecycleScope)
            viewModel.score.onEach {
                if (it == WIN_SCORE) {
                    findNavController().navigate(
                        FragmentGameDirections.actionFragmentGameToFragmentEndGame(
                            viewModel.round.value.toString(),
                            viewModel.time.value.toString()
                        )
                    )
                }
            }.launchIn(lifecycleScope)
        }
    }

    companion object {
        const val DELAY_SHOWING_WRONG_ANSWER = 500L
        const val WIN_SCORE = 10
    }
}