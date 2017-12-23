package com.violetboralee.android.bakingappnew.widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by bora on 09/12/2017.
 */

/**
 * An IntentService is subcalss for handeling asychronous task requests in a service on a seperate
 * handler thread.
 */
public class UpdateIngredientService extends IntentService {

    public static String FROM_ACTIVITY_INGREDIENTS_LIST = "FROM_ACTIVITY_INGREDIENT_LIST";
    public static String ACTION_UPDATE_WIDGET = "android.appwidget.action.APPWIDGET_UPDATE2";


    public UpdateIngredientService() {
        super("UpdateIngredientService");
    }


    /**
     * Starts this service to update widget using the extra which comes from
     * SelectARecipeStepActivity(SelectARecipeStepFragment)
     *
     * @param context
     * @param fromActivityIngredientsList
     */
    public static void startIngredientUpdateService(Context context,
                                                    ArrayList<String> fromActivityIngredientsList) {
        Intent intent = new Intent(context, UpdateIngredientService.class);
        intent.putExtra(FROM_ACTIVITY_INGREDIENTS_LIST, fromActivityIngredientsList);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            ArrayList<String> fromActivityIngredientsList =
                    intent.getExtras().getStringArrayList(FROM_ACTIVITY_INGREDIENTS_LIST);
            handleActionUpdateIngredientWidgets(fromActivityIngredientsList);
        }
    }

    /**
     * Handle action UpdateWidget int the provided background thread with the provided parameters.
     * @param fromActivityIngredientsList
     */
    private void handleActionUpdateIngredientWidgets(ArrayList<String> fromActivityIngredientsList) {
        Intent intent = new Intent(ACTION_UPDATE_WIDGET);
        intent.setAction(ACTION_UPDATE_WIDGET);
        intent.putExtra(FROM_ACTIVITY_INGREDIENTS_LIST, fromActivityIngredientsList);
        sendBroadcast(intent);
    }

}
