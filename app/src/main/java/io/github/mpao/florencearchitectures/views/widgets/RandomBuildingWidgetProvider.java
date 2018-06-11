package io.github.mpao.florencearchitectures.views.widgets;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;
import com.squareup.picasso.Picasso;
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
                    // pending intent from https://developer.android.com/training/implementing-navigation/temporal.html
                    // The resulting PendingIntent specifies not only the activity to start,
                    // but also the back and up stack that should be inserted into the task
                    PendingIntent pendingIntent = TaskStackBuilder
                                        .create(context)
                                        .addNextIntentWithParentStack(intent)
                                        .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
                    views.setOnClickPendingIntent(R.id.layout, pendingIntent);


                    views.setTextViewText(R.id.title, building.getName());
                    views.setTextViewText(R.id.description, building.getDescription());
                    Picasso.get()
                            .load( building.getMainImage() )
                            .into( views, R.id.image, new int[] {appWidgetId} );
                    appWidgetManager.updateAppWidget(appWidgetId, views);
                }

            }
        }.execute();

    }

}
