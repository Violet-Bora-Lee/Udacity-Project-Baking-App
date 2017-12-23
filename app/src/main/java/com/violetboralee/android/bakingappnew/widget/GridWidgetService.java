package com.violetboralee.android.bakingappnew.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.violetboralee.android.bakingappnew.R;

import java.util.List;

import static com.violetboralee.android.bakingappnew.widget.BakingWidgetProvider.ingredientList;

/**
 * Created by bora on 10/12/2017.
 */

public class GridWidgetService extends RemoteViewsService {
    List<String> remoteViewIngredientsList;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext(), intent);
    }


    class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
        Context mContext = null;

        public GridRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            remoteViewIngredientsList = ingredientList;

        }

        @Override
        public void onDestroy() {

        }

        // act like getCount
        @Override
        public int getCount() {
            return remoteViewIngredientsList.size();
        }

        // acts like onBindViewHolder
        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_grid_view_item);

            views.setTextViewText(R.id.tv_widget_grid_view_item, remoteViewIngredientsList.get(position));

            Intent fillInIntent = new Intent();
            views.setOnClickFillInIntent(R.id.tv_widget_grid_view_item, fillInIntent);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

}
