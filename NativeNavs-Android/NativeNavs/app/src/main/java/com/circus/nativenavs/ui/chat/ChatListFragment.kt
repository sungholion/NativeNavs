package com.circus.nativenavs.ui.chat

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.data.ChatRoomDto
import com.circus.nativenavs.databinding.FragmentChatListBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.home.HomeActivityViewModel
import com.circus.nativenavs.util.SharedPref
import com.circus.nativenavs.util.navigate

private const val TAG = "ChatListFragment"

class ChatListFragment : BaseFragment<FragmentChatListBinding>(
    FragmentChatListBinding::bind,
    R.layout.fragment_chat_list
) {

    private lateinit var homeActivity: HomeActivity
    private val chatListAdapter = ChatListAdapter()

    private val chattingViewModel: KrossbowChattingViewModel by activityViewModels()
    private val homeViewModel: HomeActivityViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initEvent()
        initObserve()
    }

    private fun initObserve() {
        chattingViewModel.chatRoomList.observe(viewLifecycleOwner) { chatRoomList ->
            Log.d(TAG, "observeViewModel: $chatRoomList")
            chatListAdapter.submitList(chatRoomList)
        }
    }

    private fun initEvent() {
        chatListAdapter.setItemClickListener(object : ChatListAdapter.ChatItemClickListener {
            override fun onItemClicked(roomId: Int) {
                val action =
                    ChatListFragmentDirections.actionChatListFragmentToChattingRoomFragment(
                        roomId
                    )
                navigate(action)
            }

        })
    }

    private fun initAdapter() {
        val chatList = arrayListOf(
            ChatRoomDto(
                roomId = 1,
                travId = SharedPref.userId!!,
                navId = 17,
                travNickname = homeViewModel.userDto.value!!.nickname,
                navNickname = "김네브",
                tourTitle = "남산투어 남산투어 남산투어 남산투어 남산투어",
                imgUrl = "",
                tourRegion = "서울",
                recentMessage = "문의드립니다~",
                recentMessageTime = System.currentTimeMillis()
            ),
            ChatRoomDto(
                roomId = 2,
                travId = SharedPref.userId!!,
                navId = 17,
                travNickname = homeViewModel.userDto.value!!.nickname,
                navNickname = "김네브",
                tourTitle = "남산투어 남산투어 남산투어 남산투어 남산투어",
                imgUrl = "",
                tourRegion = "서울",
                recentMessage = "문의드립니다~",
                recentMessageTime = System.currentTimeMillis()
            ),
            ChatRoomDto(
                roomId = 3,
                travId = SharedPref.userId!!,
                navId = 17,
                travNickname = homeViewModel.userDto.value!!.nickname,
                navNickname = "김네브",
                tourTitle = "남산투어 남산투어 남산투어 남산투어 남산투어",
                imgUrl = "",
                tourRegion = "서울",
                recentMessage = "문의드립니다~",
                recentMessageTime = System.currentTimeMillis()
            ),
        )

        binding.chatListRv.adapter = chatListAdapter
        chatListAdapter.submitList(chatList)
    }

    override fun onResume() {
        super.onResume()
        homeActivity.hideBottomNav(true)
    }

}