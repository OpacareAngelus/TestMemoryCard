package com.innogames.testmemorycard.activity.mainActivityFragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.innogames.testmemorycard.R
import com.innogames.testmemorycard.architecture.BaseFragment
import com.innogames.testmemorycard.databinding.FragmentGetJsonBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FragmentGetJson : BaseFragment<FragmentGetJsonBinding>(FragmentGetJsonBinding::inflate) {

    private val viewModel: FragmentGetJsonViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
        setObservers()
    }

    override fun setListeners() {
        binding.btnGetData.setOnClickListener {
            viewModel.getData()
        }
    }

    override fun setObservers() {
        with(binding) {
            viewModel.response.onEach {
                if (it != null) {
                    tvCity.text = getString(R.string.city_s).format(it.city)
                    tvCountry.text = getString(R.string.country_s).format(it.country)
                    tvTimeZone.text = getString(R.string.time_zone_s).format(it.timezone)
                }
            }.launchIn(lifecycleScope)
        }
    }
}