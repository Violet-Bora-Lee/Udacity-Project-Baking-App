package com.violetboralee.android.bakingappnew.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.violetboralee.android.bakingappnew.R;
import com.violetboralee.android.bakingappnew.ui.SelectARecipeActivity;

import java.util.ArrayList;

import static com.violetboralee.android.bakingappnew.widget.UpdateIngredientService.FROM_ACTIVITY_INGREDIENTS_LIST;

/**
 * Implementation of App Widget functionality.
 * AppWidgetProvider defines the basic methods that allow me to programmatically interface withe the
 * App Widget, based on broadcast events. Through it, I will receive broadcasts when the App Widget
 * is updated, enabled and deleted.
 */
public class BakingWidgetProvider extends AppWidgetProvider {

    public static String ACTION_UPDATE_WIDGET = "android.appwidget.action.APPWIDGET_UPDATE2";

    static ArrayList<String> ingredientList = new ArrayList<>();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_grid_view);

        // call activity when widget is clicked, but resume activity from stack
        Intent appIntent = new Intent(context, SelectARecipeActivity.class);

        // ACTION_MAIN: Start as a main entry point, does not expect to receive data.
        appIntent.addCategory(Intent.ACTION_MAIN);

        // CATEGORY_LAUNCHER: Should be displayed in the top-level launcher
        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        // FLAG_ACTIVITY_BROUGHT_TO_FRONT: This flag is not normally set by application code,
        // but set for you by the system as described in the launchMode documentation for
        // the singleTask mode.
        // FLAG_ACTIVITY_SINGLE_TOP: If set, the activity will not be launched if it is already
        // running at the top of the history stack.
        appIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent appPendingIntent = PendingIntent
                .getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setPendingIntentTemplate(R.id.widget_grid_view, appPendingIntent);

        // set the GridViewService intent to act as the adapter for the GridView
        Intent gridViewServiceIntent = new Intent(context, GridWidgetService.class);
        views.setRemoteAdapter(R.id.widget_grid_view, gridViewServiceIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateIngredientWidgets(Context context,
                                               AppWidgetManager appWidgetManager,
                                               int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    /**
     * Called for every update of the widget.
     *
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds     which an update is needed.
     *                         This may be all of the AppWidget instances for the provider,
     *                         or just a subset of them.
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
//        for (int appWidgetId : appWidgetIds) {
//            updateAppWidget(context, appWidgetManager, appWidgetId);
//        }
    }

    /**
     * Widtet instance is removed from the home screen
     *
     * @param context
     * @param appWidgetIds
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // Perform any action whe one or more AppWidget instances have been deleted
    }

    /**
     * Called the first time an instance of the widget is added to the homescree.
     * @param context
     */
    @Override
    public void onEnabled(Context context) {
        // Perform any action when an AppWidget for this provider is instantiated
    }

    @Override
    public void onDisabled(Context context) {
        // Perform any action when the last AppWidget instant for this provider is deleted
    }

    /**
     * Called once the last instance of the widget is removed from the home screen
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager
                .getAppWidgetIds(new ComponentName(context, BakingWidgetProvider.class));

        final String action = intent.getAction();

        if (action.equals(ACTION_UPDATE_WIDGET)) {
            ingredientList = intent.getExtras().getStringArrayList(FROM_ACTIVITY_INGREDIENTS_LIST);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);

            //Now update all widgets
            BakingWidgetProvider.updateIngredientWidgets(context, appWidgetManager, appWidgetIds);

        }
        super.onReceive(context, intent);
    }
}

