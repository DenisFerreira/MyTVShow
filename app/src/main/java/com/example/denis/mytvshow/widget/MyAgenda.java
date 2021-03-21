package com.example.denis.mytvshow.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.ferreira.denis.mytvshow.R;

/**
 * Implementation of App Widget functionality.
 */
public class MyAgenda extends AppWidgetProvider {

    public static final String ACTION_DATA_UPDATED = "data_updated";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_title);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_agenda);
        views.setTextViewText(R.id.appwidget_title, widgetText);

        Intent intent = new Intent(context, MyAgendaWidgetService.class);
        views.setRemoteAdapter(R.id.app_widget_list_view, intent);
        views.setEmptyView(R.id.app_widget_list_view, R.id.widget_no_favorites_tv);

        Intent updateWidgetIntent = new Intent(context,
                MyAgenda.class);
        updateWidgetIntent.setAction(
                MyAgenda.ACTION_DATA_UPDATED);

        PendingIntent pendingIntent= PendingIntent.getBroadcast(context, 0, updateWidgetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_refresh_iv, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(ACTION_DATA_UPDATED)) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                    new ComponentName(context, getClass()));
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.app_widget_list_view);
        }
    }
}

