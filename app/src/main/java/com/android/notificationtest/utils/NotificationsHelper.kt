package com.android.notificationtest.utils

import android.app.*
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.SystemClock
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.android.notificationtest.MainActivity
import com.android.notificationtest.R
import com.android.notificationtest.constants.Constants
import com.android.notificationtest.fcm.NotificationViews
import com.android.notificationtest.fcm.TriggerNotificationReceiver
import com.android.notificationtest.notification.data.NotificationData
import com.bumptech.glide.Glide
import com.google.firebase.messaging.FirebaseMessagingService
import javax.inject.Inject


class NotificationsHelper @Inject constructor(private val context: Context, var notificationViews: NotificationViews) {

    private val mNotificationManager by lazy { context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(): String {
        //Create new notification channel.
        val channelId = "test_channel_id"
        val channelName: CharSequence = "Test Channel"
        val importance = NotificationManager.IMPORTANCE_HIGH

        val notificationChannel = NotificationChannel(channelId, channelName, importance)

        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        notificationChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        mNotificationManager.createNotificationChannel(notificationChannel)
        return channelId
    }


    /**
     * This method is used to show Notification from the give data.
     * @param notificationData Notification data to be shown.
     **/
    fun showNotification(notificationData: NotificationData?) {
        var remoteViews: RemoteViews? = null

        if (notificationData?.flag == Constants.TOP_DEALS) {
            //Get RemoteViews for auto scrolling images in Notification.
            remoteViews = notificationViews.getTopDealsView(notificationData)
        }

        val notifyId = notificationData?.id
        val notificationBuilder: NotificationCompat.Builder?

        notificationBuilder = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val channelId = createChannel()
            NotificationCompat.Builder(context, channelId)
        } else {
            NotificationCompat.Builder(context)
        }

        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        notificationBuilder.setContentIntent(PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT));

        notificationBuilder.setContentTitle(notificationData?.title)
            .setContentText(notificationData?.info)
            .setSmallIcon(R.drawable.ic_top_deals)
            .setLargeIcon(Glide.with(context).asBitmap().load(R.drawable.ic_top_deals).submit().get())
        if (remoteViews != null)
            notificationBuilder.setCustomBigContentView(remoteViews)

        val notification = notificationBuilder.build()
        notification.flags = Notification.FLAG_AUTO_CANCEL

        notifyId?.let { mNotificationManager.notify(it, notification) }
    }

    /**
    * This method is used to schedule notification for given interval.
    * @param notificationId Unique id to start repeating AlarmManager.
    * @param interval Repeating interval.
    **/
    fun scheduleNotification(notificationId: Int, interval: Long?) {
        val intent = Intent(context, TriggerNotificationReceiver::class.java)
        intent.putExtra(Constants.NOTIFICATION_ID, notificationId)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = context.getSystemService(FirebaseMessagingService.ALARM_SERVICE) as AlarmManager
        alarmManager.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME,
            SystemClock.elapsedRealtime() + (interval ?: AlarmManager.INTERVAL_FIFTEEN_MINUTES),
            interval ?: AlarmManager.INTERVAL_FIFTEEN_MINUTES,
            pendingIntent
        )
    }
}