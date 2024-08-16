package com.circus.nativenavs.ui.mypage

import StampListAdapter
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.activityViewModels
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseFragment
import com.circus.nativenavs.databinding.FragmentStampBinding
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.ui.home.HomeActivityViewModel
import com.circus.nativenavs.util.popBackStack

class StampFragment : BaseFragment<FragmentStampBinding>(FragmentStampBinding::bind,R.layout.fragment_stamp) {

    private lateinit var homeActivity: HomeActivity
    private val stampListAdapter = StampListAdapter()
    private val homeActivityViewModel: HomeActivityViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeActivity = context as HomeActivity
    }

    override fun onResume() {
        super.onResume()
        homeActivity.hideBottomNav(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    fun initView(){

        if(homeActivityViewModel.stamp.value?.isEmpty() == true){
            binding.stampEmptyCl.visibility = VISIBLE
            binding.stampRv.visibility = GONE
        }
        else{
            binding.stampEmptyCl.visibility = GONE
            binding.stampRv.visibility = VISIBLE
            binding.stampRv.adapter = stampListAdapter.apply {
                submitList(homeActivityViewModel.stamp.value)
            }
        }
        binding.stampTitleLayout.titleText = getString(R.string.stamp)
        binding.stampTitleLayout.customWebviewTitleBackIv.setOnClickListener {
            popBackStack()
        }

    }

}