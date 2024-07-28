package com.circus.nativenavs.ui.chat

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentChattingRoomBinding
import com.circus.nativenavs.ui.home.HomeActivity

class ChattingRoomFragment : BaseFragment<FragmentChattingRoomBinding>(FragmentChattingRoomBinding::bind, R.layout.fragment_chatting_room) {

    private lateinit var homeActivity: HomeActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: ChattingRoomFragmentArgs by navArgs()
        binding.chatRoomTv.text = "Chat ID는 ${args.chatId.toString()}"
    }

    override fun onResume() {
        super.onResume()
        homeActivity.hideBottomNav(false)
    }
}
