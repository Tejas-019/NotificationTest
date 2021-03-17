package com.android.notificationtest.fcm

import android.content.Context
import android.widget.RemoteViews
import com.android.notificationtest.R
import com.android.notificationtest.notification.data.NotificationData
import com.bumptech.glide.Glide
import java.io.IOException
import javax.inject.Inject

class NotificationViews @Inject constructor(private val context: Context) {

    /**
     * This method is used to generate RemoteViews from given notification data.
     * @param notificationData Notification data to be shown.
     * @return RemoteViews RemoteViews containing images, Title and Notification Info
     **/
    fun getTopDealsView(notificationData: NotificationData?): RemoteViews {
        val topDealsView = RemoteViews(context.packageName, R.layout.notification_view)

        topDealsView.setTextViewText(R.id.deals_title, notificationData?.title)
        topDealsView.setTextViewText(R.id.deals_message, notificationData?.info)
        topDealsView.setImageViewBitmap(R.id.deals_icon, Glide.with(context).asBitmap().load(R.drawable.ic_top_deals).submit().get())

        notificationData?.deals_images?.forEach { dealImage ->
            val viewFlipperImage = RemoteViews(context.packageName, R.layout.item_notification_view)
            if (dealImage.isEmpty()) {
                viewFlipperImage.setImageViewResource(R.id.imageView, R.mipmap.ic_launcher)
            } else {
                try {
                    viewFlipperImage.setImageViewBitmap(R.id.imageView, Glide.with(context).asBitmap().load(dealImage).submit().get())
                } catch (e: IOException) {
                    viewFlipperImage.setImageViewResource(R.id.imageView, R.mipmap.ic_launcher)
                }
            }
            topDealsView.addView(R.id.view_flipper, viewFlipperImage)
        }
        return topDealsView
    }
}