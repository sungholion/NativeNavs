package com.circus.nativenavs.data.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.circus.nativenavs.R
import com.circus.nativenavs.ui.SplashActivity
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
        Log.d(TAG, "onMessageReceived: ${message.data}")

        // data - 백그라운드
        if (message.data.isNotEmpty()) {
            sendNotification(
                message.data["flag"]!!.toInt(),
                message
            )
        } else {
            Log.d(TAG, "onMessageReceived: data가 비어있습니다. 메시지를 수신하지 못했습니다.")
            Log.d(TAG, "onMessageReceived: ${message.data}")
        }

    }

    private fun sendNotification(flag: Int, message: RemoteMessage) {
        val uniId: Int = (System.currentTimeMillis() / 7).toInt()

        val intent = Intent(this, SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // Activity Stack 을 경로만 남긴다. A-B-C-D-B => A-B
        when (flag) {
            1 -> { // 채팅 -> 룸id 필요
                intent.putExtra("flag", flag)
                intent.putExtra("roomId", message.data["roomId"]!!.toInt())
            }

            2 -> { // 예약 신청 완료 -> 예약, 투어 id
                intent.putExtra("flag", flag)
                intent.putExtra("reservationId", message.data["reservationId"]!!.toInt())
                intent.putExtra("tourId", message.data["tourId"]!!.toInt())
            }

            3 -> { // 투어 종료 -> 예약, 투어 id
                intent.putExtra("flag", flag)
                intent.putExtra("reservationId", message.data["reservationId"]!!.toInt())
                intent.putExtra("tourId", message.data["tourId"]!!.toInt())
            }

            4 -> { // 투어 예정 알림 -> 예약, 투어 id
                intent.putExtra("flag", flag)
                intent.putExtra("reservationId", message.data["reservationId"]!!.toInt())
                intent.putExtra("tourId", message.data["tourId"]!!.toInt())
            }
        }

        val pendingIntent =
            PendingIntent.getActivity(this, uniId, intent, PendingIntent.FLAG_IMMUTABLE)


        val title = message.data["title"]
        val body = message.data["body"]
        // 알림 소리
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        // 알림에 대한 UI 정보와 작업을 지정한다.
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo_nativenavs) // 아이콘 설정
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