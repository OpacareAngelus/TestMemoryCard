package com.innogames.testmemorycard.activity.mainActivityFragments

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FragmentGameViewModel : ViewModel() {

    private val _round = MutableStateFlow(0)
    val round: StateFlow<Int> = _round

    private val _time = MutableStateFlow(0)
    val time: StateFlow<Int> = _time

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

    private val _images = MutableStateFlow<List<Int>>(listOf())
    val images: StateFlow<List<Int>> = _images

    private val _firstTakenCard = MutableStateFlow<ImageView?>(null)
    val firstTakenCard: StateFlow<ImageView?> = _firstTakenCard

    private val _secondTakenCard = MutableStateFlow<ImageView?>(null)
    val secondTakenCard: StateFlow<ImageView?> = _secondTakenCard

    fun changeRound() = viewModelScope.launch { _round.emit(_round.value + ROUNDS_STEP) }
    fun changeScore() =
        viewModelScope.launch { _score.emit(_score.value + SCORE_FOR_CORRECT_ANSWER) }

    fun startTimer() {
        viewModelScope.launch {
            while (true) {
                _time.emit(_time.value + SECOND_STEP)
                delay(ONE_SECOND)
            }
        }
    }

    fun setImagesList(list: List<Int>) = viewModelScope.launch { _images.emit(list) }
    fun setFirstTakenCard(imageView: ImageView?) =
        viewModelScope.launch { _firstTakenCard.emit(imageView) }

    fun setSecondTakenCard(imageView: ImageView?) =
        viewModelScope.launch { _secondTakenCard.emit(imageView) }

    fun setNullTakenCards() {
        viewModelScope.launch {
            _firstTakenCard.emit(null)
            _secondTakenCard.emit(null)
        }
    }

    companion object {
        const val ONE_SECOND = 1000L
        const val SCORE_FOR_CORRECT_ANSWER = 5
        const val ROUNDS_STEP = 1
        const val SECOND_STEP = 1
    }
}