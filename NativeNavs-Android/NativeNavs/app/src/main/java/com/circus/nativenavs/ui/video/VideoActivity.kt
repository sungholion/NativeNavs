package com.circus.nativenavs.ui.video

import android.os.Bundle
import android.widget.Toast
import com.circus.nativenavs.R
import com.circus.nativenavs.config.BaseActivity
import com.circus.nativenavs.databinding.ActivityVideoBinding

class VideoActivity : BaseActivity<ActivityVideoBinding>(ActivityVideoBinding::inflate) {

    private var mic = true
    private var camera = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.videoCallCloseBtn.setOnClickListener {
            finish()
        }

        binding.videoMicBtn.apply {
            setOnClickListener {
                if(mic){
                    mic = false
                    setImageResource(R.drawable.icon_mic_off)
                    Toast.makeText(context,"마이크 Off",Toast.LENGTH_SHORT).show()
                }
                else{
                    mic = true
                    setImageResource(R.drawable.icon_mic_on)
                    Toast.makeText(context,"마이크 On",Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.videoCameraBtn.apply {
            setOnClickListener {
                if(camera){
                    camera = false
                    setImageResource(R.drawable.icon_camera_off)
                    Toast.makeText(context,"카메라 Off",Toast.LENGTH_SHORT).show()
                }
                else{
                    camera = true
                    setImageResource(R.drawable.icon_camera_on)
                    Toast.makeText(context,"카메라 On",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}