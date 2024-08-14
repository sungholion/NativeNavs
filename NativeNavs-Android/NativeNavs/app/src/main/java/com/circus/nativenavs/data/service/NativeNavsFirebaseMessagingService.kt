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
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        if (message.data.isNotEmpty() && SharedPref.enabledNoti!!) {
            sendNotification(
                message.data["flag"]!!.toInt(),
                message
            )
        }

    }

    private fun sendNotification(flag: Int, message: RemoteMessage) {
        val uniId: Int = (System.currentTimeMillis() / 7).toInt()

        val intent = Intent(this, SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        when (flag) {
            1 -> {
                intent.putExtra("flag", flag)
                intent.putExtra("roomId", message.data["roomId"]!!.toInt())
            }

            2 -> {
                intent.putExtra("flag", flag)
                intent.putExtra("reservationId", message.data["reservationId"]!!.toInt())
                intent.putExtra("tourId", message.data["tourId"]!!.toInt())
            }

            3 -> {
                intent.putExtra("flag", flag)
                intent.putExtra("reservationId", message.data["reservationId"]!!.toInt())
                intent.putExtra("tourId", message.data["tourId"]!!.toInt())
            }

            4 -> {
                intent.putExtra("flag", flag)
                intent.putExtra("reservationId", message.data["reservationId"]!!.toInt())
                intent.putExtra("tourId", message.data["tourId"]!!.toInt())
            }
        }

        val pendingIntent =
            PendingIntent.getActivity(this, uniId, intent, PendingIntent.FLAG_IMMUTABLE)

        val title = message.data["title"]
        val body = message.data["body"]
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo_nativenavs)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(soundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel =
            NotificationChannel(CHANNEL_ID, "Notice", NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(uniId, notificationBuilder.build())
    }
}