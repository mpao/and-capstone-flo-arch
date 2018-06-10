package io.github.mpao.florencearchitectures.views.widgets;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.RemoteViews;
import java.util.concurrent.ThreadLocalRandom;
import javax.inject.Inject;
import io.github.mpao.florencearchitectures.R;
import io.github.mpao.florencearchitectures.di.App;
import io.github.mpao.florencearchitectures.entities.Building;
import io.github.mpao.florencearchitectures.models.databases.AppDatabase;
import io.github.mpao.florencearchitectures.views.BuildingActivity;

public class RandomBuildingWidgetProvider extends AppWidgetProvider {

    @Inject AppDatabase database;
    private static Building[] list;
    private static int chooseRandomIndex;

    public RandomBuildingWidgetProvider() {
        App.graph.inject(this);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // create new thread for querying database
        new AsyncTask<Void, Void, Integer>(){
            @Override
            protected Integer doInBackground(Void... voids) {
                list = database.buildingDao().getAllForWidget();
                return list.length;
            }

            @Override
            protected void onPostExecute(Integer integer) {

                for (int appWidgetId : appWidgetIds) {
                    // for each widget, create a random index...
                    chooseRandomIndex = ThreadLocalRandom.current().nextInt(0, integer );
                    // ... and then a random building
                    Building building = list[chooseRandomIndex];
                    // intent and click listener
                    Intent intent = new Intent(context, BuildingActivity.class);
                    intent.putExtra(App.INTENT_BUILDING, building);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
                    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
                    views.setOnClickPendingIntent(R.id.layout, pendingIntent);


                    views.setTextViewText(R.id.title, building.getName());
                    appWidgetManager.updateAppWidget(appWidgetId, views);
                }

            }
        }.execute();

    }

}
