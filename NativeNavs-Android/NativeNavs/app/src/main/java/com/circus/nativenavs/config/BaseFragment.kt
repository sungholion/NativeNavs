package com.circus.nativenavs.config

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.circus.nativenavs.util.LocaleUtils
import com.circus.nativenavs.util.SharedPref

abstract class BaseFragment<B : ViewBinding>(
    private val bind: (View) -> B,
    @LayoutRes layoutResId: Int
) : Fragment(layoutResId) {
    private var _binding: B? = null
    protected val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        SharedPref.language?.let {
            LocaleUtils.setLocale(requireContext(), it)
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}