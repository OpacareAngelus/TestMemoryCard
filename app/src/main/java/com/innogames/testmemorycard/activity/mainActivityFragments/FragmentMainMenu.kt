package com.innogames.testmemorycard.activity.mainActivityFragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.innogames.testmemorycard.architecture.BaseFragment
import com.innogames.testmemorycard.databinding.FragmentMainMenuBinding

class FragmentMainMenu : BaseFragment<FragmentMainMenuBinding>(FragmentMainMenuBinding::inflate){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()
    }

    override fun setObservers() {
    }

    override fun setListeners() {
        with(binding){
            btnStartGame.setOnClickListener {
                findNavController().navigate(
                    FragmentMainMenuDirections.actionFragmentMainMenuToFragmentGame(
                        "",
                        ""
                    )
                )
            }
            btnGetJson.setOnClickListener {
                findNavController().navigate(FragmentMainMenuDirections.actionFragmentMainMenuToFragmentGetJson())
            }
            btnExit.setOnClickListener {
                requireActivity().finishAffinity()
            }
        }
    }
}