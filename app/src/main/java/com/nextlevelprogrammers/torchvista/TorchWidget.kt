package com.nextlevelprogrammers.torchvista

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews

class TorchWidget : AppWidgetProvider() {

    companion object {
        private const val TAG = "TorchWidget"
        private const val ACTION_TOGGLE_ON = "TOGGLE_ON"
        private const val ACTION_TOGGLE_OFF = "TOGGLE_OFF"
        private const val PREFS_NAME = "TorchPrefs"
        private const val PREFS_TORCH_STATE = "TorchState"
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        Log.d(TAG, "onUpdate called with appWidgetIds: ${appWidgetIds.joinToString()}")
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        Log.d(TAG, "onReceive called with action: ${intent.action}")
        when (intent.action) {
            ACTION_TOGGLE_ON -> {
                Log.d(TAG, "Turning torch on")
                val widgetTorchManager = WidgetTorchManager(context)
                widgetTorchManager.turnTorchOn()
                updateTorchState(context, true)
                updateAllWidgets(context)
            }
            ACTION_TOGGLE_OFF -> {
                Log.d(TAG, "Turning torch off")
                val widgetTorchManager = WidgetTorchManager(context)
                widgetTorchManager.turnTorchOff()
                updateTorchState(context, false)
                updateAllWidgets(context)
            }
        }
    }

    private fun updateTorchState(context: Context, isTorchOn: Boolean) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(prefs.edit()) {
            putBoolean(PREFS_TORCH_STATE, isTorchOn)
            apply()
        }
        Log.d(TAG, "Torch state updated to: $isTorchOn")
    }

    private fun getTorchState(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(PREFS_TORCH_STATE, false)
    }

    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
        val views = RemoteViews(context.packageName, R.layout.torch_widget)
        val isTorchOn = getTorchState(context)
        views.setTextViewText(R.id.text_torch_state, if (isTorchOn) "Torch On" else "Torch Off")

        val intentOn = Intent(context, TorchWidget::class.java).apply {
            action = ACTION_TOGGLE_ON
        }
        val pendingIntentOn = PendingIntent.getBroadcast(
            context,
            0,
            intentOn,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        views.setOnClickPendingIntent(R.id.button_toggle_on, pendingIntentOn)

        val intentOff = Intent(context, TorchWidget::class.java).apply {
            action = ACTION_TOGGLE_OFF
        }
        val pendingIntentOff = PendingIntent.getBroadcast(
            context,
            1,
            intentOff,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        views.setOnClickPendingIntent(R.id.button_toggle_off, pendingIntentOff)

        appWidgetManager.updateAppWidget(appWidgetId, views)
        Log.d(TAG, "Widget updated with ID: $appWidgetId")
    }

    private fun updateAllWidgets(context: Context) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val componentName = ComponentName(context, TorchWidget::class.java)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(componentName)
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }
}