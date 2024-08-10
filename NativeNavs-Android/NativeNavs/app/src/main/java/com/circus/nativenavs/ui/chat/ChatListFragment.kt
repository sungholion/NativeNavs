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

        chattingViewModel.getChatRoomList()
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
            override fun onItemClicked(chatRoom: ChatRoomDto) {
                chattingViewModel.setCurrentChatRoom(chatRoom)
                Log.d(TAG, "onItemClicked: $chatRoom")
                val action =
                    ChatListFragmentDirections.actionChatListFragmentToChattingRoomFragment(
                        chatRoom.roomId
                    )
                navigate(action)
            }

        })
    }

    private fun initAdapter() {
        binding.chatListRv.adapter = chatListAdapter
    }

    override fun onResume() {
        super.onResume()
        homeActivity.hideBottomNav(true)
    }

}