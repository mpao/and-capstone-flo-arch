package io.github.mpao.florencearchitectures.models.networks;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import javax.inject.Inject;
import io.github.mpao.florencearchitectures.di.App;
import io.github.mpao.florencearchitectures.entities.Building;
import io.github.mpao.florencearchitectures.models.databases.AppDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenDataService extends IntentService {

    @Inject OpenDataApi api;
    @Inject AppDatabase database;

    public OpenDataService() {
        super("OpenDataService");
        App.graph.inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        //TODO: improve updates
        // in this release there is not a swipe to refresh function and then
        // forceUpdate is unused. To implement this feature, remember to change
        // the sql structure to save favorites elsewhere
        boolean forceUpdate = intent.getBooleanExtra("force", false);

        api.get().enqueue(new Callback<Building[]>() {

            @Override
            public void onResponse(Call<Building[]> call, Response<Building[]> response) {
                // onResponse is on a separate thread, see RetrofitModule.getRetrofit
                Building[] list = response.body();
                // if forceUpdate is true, I dont check if the db is empty
                boolean conditions
                        = forceUpdate ? list != null && list.length > 0 :
                                        list != null && list.length > 0 && database.buildingDao().countBuildings()==0;
                if(conditions) {
                    database.buildingDao().insertAll(list);
                }
            }
            @Override
            public void onFailure(Call<Building[]> call, Throwable t) {
                Log.d(OpenDataService.class.toString(), "oh no");
                //todo fix error message
            }

        });

    }

}
