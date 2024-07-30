package com.circus.nativenavs.ui.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.navArgs
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.data.ChatRoomDto
import com.circus.nativenavs.data.MessageDto
import com.circus.nativenavs.databinding.FragmentChattingRoomBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.video.VideoActivity
import com.circus.nativenavs.util.navigate
import com.circus.nativenavs.util.popBackStack

class ChattingRoomFragment : BaseFragment<FragmentChattingRoomBinding>(
    FragmentChattingRoomBinding::bind,
    R.layout.fragment_chatting_room
) {

    private lateinit var homeActivity: HomeActivity
    private val messageListAdapter = MessageListAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDataFromFragment()

        initView()
        initAdapter()
        initEvent()
    }

    private fun initEvent() {
        binding.chatRoomLayout.customWebviewTitleBackIv.setOnClickListener {
            popBackStack()
        }

        binding.chatTourBookLl.setOnClickListener {
            navigate(R.id.action_chattingRoomFragment_to_reservationRegisterFragment)
        }

        binding.chatTourCallLl.setOnClickListener {
            homeActivity.startActivity(Intent(homeActivity, VideoActivity::class.java))
        }
    }

    private fun getDataFromFragment() {
        val args: ChattingRoomFragmentArgs by navArgs()
        Log.d("getDataFromFragment", "getDataFromFragment: ${args.chatId}")
    }

    private fun initView() {
        binding.chatRoom = ChatRoomDto(1, "남산타워 투어", "서울", "", "아린")
    }

    private fun initAdapter() {
        val messageList = arrayListOf(
            MessageDto(
                1, 0, "안드류",
                "", "문의드립니다~",
                System.currentTimeMillis(), 1
            ),
            MessageDto(
                2, 2, "아린",
                "", "안녕하세요",
                System.currentTimeMillis(), 1
            ),
            MessageDto(
                3, 2, "아린",
                "", "문의 감사합니다. 문의 감사합니다. 문의 감사합니다. 문의 감사합니다.",
                System.currentTimeMillis(), 1
            ),
        )

        binding.chatMessageRv.adapter = messageListAdapter
        messageListAdapter.submitList(messageList)
    }

    override fun onResume() {
        super.onResume()
        homeActivity.hideBottomNav(false)
    }
}
