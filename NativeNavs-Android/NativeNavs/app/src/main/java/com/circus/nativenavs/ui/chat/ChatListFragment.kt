package com.circus.nativenavs.ui.chat

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.data.ChatListDto
import com.circus.nativenavs.databinding.FragmentChatListBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.util.navigate

class ChatListFragment : BaseFragment<FragmentChatListBinding>(
    FragmentChatListBinding::bind,
    R.layout.fragment_chat_list
) {

    private lateinit var homeActivity: HomeActivity
    private val chatListAdapter = ChatListAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        chatListAdapter.setItemClickListener(object : ChatListAdapter.ChatItemClickListener {
            override fun onItemClicked(chatRoomId: Int) {
                val action =
                    ChatListFragmentDirections.actionChatListFragmentToChattingRoomFragment(chatRoomId)
                navigate(action)
            }

        })
    }

    private fun initAdapter() {
        val chatList = arrayListOf(
            ChatListDto(
                1, "남산투어 남산투어 남산투어 남산투어 남산투어",
                "아린", "문의드립니다~",
                System.currentTimeMillis(), "imgurl"
            ),
            ChatListDto(
                2, "남산투어 남산투어 남산투어 남산투어 남산투어",
                "아린", "문의드립니다~",
                System.currentTimeMillis(), "imgurl"
            ),
            ChatListDto(
                3, "남산투어 남산투어 남산투어 남산투어 남산투어",
                "아린", "문의드립니다~",
                System.currentTimeMillis(), "imgurl"
            )
        )

        binding.chatListRv.adapter = chatListAdapter
        chatListAdapter.submitList(chatList)
    }

    override fun onResume() {
        super.onResume()
        homeActivity.hideBottomNav(true)
    }

}