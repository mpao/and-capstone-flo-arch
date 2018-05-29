package io.github.mpao.florencearchitectures.models.networks;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import javax.inject.Inject;
import io.github.mpao.florencearchitectures.di.App;
import io.github.mpao.florencearchitectures.entities.Building;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpenDataService extends IntentService {

    @Inject OpenDataApi api;

    public OpenDataService() {
        super("OpenDataService");
        App.graph.inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        api.get().enqueue(new Callback<Building[]>() {

            @Override
            public void onResponse(Call<Building[]> call, Response<Building[]> response) {

                Building[] list = response.body();
                if(list != null && list.length > 0) {
                    for (Building element : list) {
                        Log.d(OpenDataService.class.toString(), element.getName());
                    }
                }

            }

            @Override
            public void onFailure(Call<Building[]> call, Throwable t) {
                Log.d(OpenDataService.class.toString(), "oh no");
            }

        });

    }

}
