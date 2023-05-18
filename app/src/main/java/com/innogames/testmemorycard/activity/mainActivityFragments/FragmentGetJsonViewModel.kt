package com.innogames.testmemorycard.activity.mainActivityFragments

import NetworkService.requestAPI
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innogames.testmemorycard.network.GeoDataResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FragmentGetJsonViewModel : ViewModel() {

    private val _response = MutableStateFlow<GeoDataResponse?>(null)
    val response: StateFlow<GeoDataResponse?> = _response

    fun getData() {
        viewModelScope.launch {
            try {
                val data = requestAPI.getFactAboutNumber()
                _response.emit(data)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}