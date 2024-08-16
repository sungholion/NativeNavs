package com.circus.nativenavs.ui.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.core.os.HandlerCompat.postDelayed
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.navArgs
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.data.ChatRoomDto
import com.circus.nativenavs.data.MessageDto
import com.circus.nativenavs.data.RequestTranslate
import com.circus.nativenavs.databinding.FragmentChattingRoomBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.home.HomeActivityViewModel
import com.circus.nativenavs.ui.video.VideoActivity
import com.circus.nativenavs.util.SharedPref
import com.circus.nativenavs.util.navigate
import com.circus.nativenavs.util.popBackStack
import kotlin.math.log

private const val TAG = "ChattingRoomFragment"

class ChattingRoomFragment : BaseFragment<FragmentChattingRoomBinding>(
    FragmentChattingRoomBinding::bind,
    R.layout.fragment_chatting_room
) {

    private lateinit var homeActivity: HomeActivity
    private val args: ChattingRoomFragmentArgs by navArgs()

    private val chattingViewModel: KrossbowChattingViewModel by activityViewModels()
    private val homeViewModel: HomeActivityViewModel by activityViewModels()

    private val messageListAdapter = MessageListAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity

        chattingViewModel.setSenderInfo(
            SharedPref.userId!!,
            homeViewModel.userDto.value!!.nickname,
            homeViewModel.userDto.value!!.image
        )
    }

    override fun onResume() {
        super.onResume()
        chattingViewModel.setChatRoomId(args.chatId)
        chattingViewModel.getChatMessages(args.chatId)
        chattingViewModel.connectWebSocket()
        homeActivity.hideBottomNav(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initAdapter()
        initObserve()
        initEvent()
    }

    private fun initObserve() {
        chattingViewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            messageListAdapter.submitList(uiState.messages.toList())
            messageListAdapter.notifyDataSetChanged()
            binding.chatMessageRv.apply {
                postDelayed({
                    layoutManager?.scrollToPosition(chattingViewModel.uiState.value!!.messages.size - 1)
                }, 50)
            }
        }
    }

    private fun initEvent() {
        messageListAdapter.setItemClickListener(object : MessageListAdapter.ChatItemClickListener {
            override fun onItemClicked(content: String, position: Int) {
                if (chattingViewModel.uiState.value!!.messages[position].translatedContent != "") {
                    chattingViewModel.translateMessage(position)
                } else {
                    chattingViewModel.translateMessage(
                        RequestTranslate(
                            source = "auto",
                            target = SharedPref.language!!,
                            text = content
                        ),
                        position = position
                    )

                }
            }

        })

        binding.chatRoomSendBtn.setOnClickListener {
            chattingViewModel.setMessage(binding.chatRoomTypingEt.text.toString())
            chattingViewModel.sendMessage {
                binding.chatRoomTypingEt.text.clear()
            }
        }

        binding.chatRoomLayout.customWebviewTitleBackIv.setOnClickListener {
            popBackStack()
        }

        binding.chatTourBookLl.setOnClickListener {
            val action =
                ChattingRoomFragmentDirections.actionChattingRoomFragmentToReservationRegisterFragment(
                    tourId = chattingViewModel.currentChatRoom.value!!.tourId,
                    travId = chattingViewModel.currentChatRoom.value!!.senderId
                )
            navigate(action)
        }

        binding.chatTourCallLl.setOnClickListener {
            homeActivity.startActivity(Intent(homeActivity, VideoActivity::class.java))
        }
    }

    private fun initView() {
        binding.userId = SharedPref.userId
        binding.chatRoom = chattingViewModel.currentChatRoom.value!!
        binding.chatTourBookLl.visibility = if (SharedPref.isNav!!) View.VISIBLE else View.GONE

        binding.chatMessageRv.apply {
            addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
                if (bottom < oldBottom) {
                    postDelayed({
                        layoutManager?.scrollToPosition(chattingViewModel.uiState.value!!.messages.size - 1)
                    }, 50)
                }
            }
        }
    }

    private fun initAdapter() {
        binding.chatMessageRv.adapter = messageListAdapter
    }

    override fun onPause() {
        super.onPause()
        chattingViewModel.disconnectWebSocket()
        chattingViewModel.setChatRoomId(-1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        chattingViewModel.setChatRoomId(-1)
        chattingViewModel.resetUiState()
    }
}
