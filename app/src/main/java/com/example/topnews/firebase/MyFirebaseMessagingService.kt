package com.example.topnews.firebase

import android.R
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import com.example.topnews.NewsActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


/**
 * Created by shande on 30-05-2021
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        showNotification(message.data)
    }

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    private fun showNotification(message: Map<String, String>) {
        val intent = Intent(this, NewsActivity::class.java)
        val pIntent = PendingIntent.getActivity(
            this,
            System.currentTimeMillis().toInt(), intent, 0
        )

        val n: Notification = Notification.Builder(this)
            .setContentTitle(message["title"])
            .setContentText(message["description"])
            .setSmallIcon(R.drawable.sym_def_app_icon)
            .setContentIntent(pIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0, n)
    }

}