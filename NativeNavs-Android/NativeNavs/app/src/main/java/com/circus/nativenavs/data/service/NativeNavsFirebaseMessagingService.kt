package com.circus.nativenavs.data.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.circus.nativenavs.R
import com.circus.nativenavs.config.ApplicationClass
import com.circus.nativenavs.ui.home.HomeActivity
import com.circus.nativenavs.util.CHANNEL_ID
import com.circus.nativenavs.util.SharedPref
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

private const val TAG = "fcm"

class NativeNavsFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        SharedPref.fcmToken = token
        Log.d(TAG, "onNewToken: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(TAG, "onMessageReceived: $message")

        // notification - 포그라운드
        if (message.notification != null) {
            Log.d(TAG, "onMessageReceived: ${message.notification?.title!!}")
            sendNotification(message.notification?.title!!, message.notification?.body!!)
        }else{
            Log.d(TAG, "onMessageReceived: notification이 비어있습니다. 메시지를 수신하지 못했습니다.")
            Log.d(TAG, "onMessageReceived: ${message.notification}")
        }

        // data - 백그라운드
        if (message.data.isNotEmpty()) {
            Log.d(TAG, "onMessageReceived: 타이틀 ${message.data["title"].toString()}")
            Log.d(TAG, "onMessageReceived: 바디 ${message.data["body"].toString()}")
            sendNotification(message.data["title"].toString(), message.data["body"].toString())
        } else {
            Log.d(TAG, "onMessageReceived: data가 비어있습니다. 메시지를 수신하지 못했습니다.")
            Log.d(TAG, "onMessageReceived: ${message.data}")
        }

    }

    private fun sendNotification(title: String, body: String) {
        val uniId: Int = (System.currentTimeMillis() / 7).toInt()

        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // Activity Stack 을 경로만 남긴다. A-B-C-D-B => A-B
        val pendingIntent =
            PendingIntent.getActivity(this, uniId, intent, PendingIntent.FLAG_IMMUTABLE)

        // 알림 소리
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // 알림에 대한 UI 정보와 작업을 지정한다.
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher) // 아이콘 설정
            .setContentTitle(title) // 제목
            .setContentText(body) // 메시지 내용
            .setAutoCancel(true)
            .setSound(soundUri) // 알림 소리
            .setContentIntent(pendingIntent) // 알림 실행 시 Intent

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 오레오 버전 이후에는 채널이 필요하다.
        val channel =
            NotificationChannel(CHANNEL_ID, "Notice", NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)

        // 알림 생성
        notificationManager.notify(uniId, notificationBuilder.build())
    }
}