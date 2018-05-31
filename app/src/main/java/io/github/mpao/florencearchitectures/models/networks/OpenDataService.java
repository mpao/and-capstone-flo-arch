package io.github.mpao.florencearchitectures.models.networks;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;
import javax.inject.Inject;
import io.github.mpao.florencearchitectures.di.App;
import io.github.mpao.florencearchitectures.entities.Building;
import io.github.mpao.florencearchitectures.models.databases.AppContract;
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
                    OpenDataService.this.deleteAll();
                    OpenDataService.this.insertAll(list);
                }

            }

            @Override
            public void onFailure(Call<Building[]> call, Throwable t) {
                Log.d(OpenDataService.class.toString(), "oh no");
                //todo fix error message
            }

        });

    }

    private void insertAll(Building[] list){

        for (Building element : list) {
            ContentValues values = new ContentValues();
            values.put(AppContract.AppContractElement.ID, element.getId());
            values.put(AppContract.AppContractElement.NAME, element.getName());
            values.put(AppContract.AppContractElement.CATEGORY, element.getPeriod());
            values.put(AppContract.AppContractElement.YEAR, element.getYear());
            values.put(AppContract.AppContractElement.TIPOLOGY, element.getTypology());
            values.put(AppContract.AppContractElement.AUTHOR, element.getAuthor());
            values.put(AppContract.AppContractElement.DESCRIPTION, element.getDescription());
            values.put(AppContract.AppContractElement.PROJECT, element.getProject());
            values.put(AppContract.AppContractElement.MUNICIPALITY, element.getMunicipality());
            values.put(AppContract.AppContractElement.ADDRESS, element.getAddress());
            values.put(AppContract.AppContractElement.PROVINCE, element.getProvince());
            values.put(AppContract.AppContractElement.LATITUDE, element.getLatitude());
            values.put(AppContract.AppContractElement.LONGITUDE, element.getLongitude());
            values.put(AppContract.AppContractElement.MAIN_IMAGE, element.getMainImage());
            values.put(AppContract.AppContractElement.OTHER_IMAGES, element.getImagesAsString() );
            values.put(AppContract.AppContractElement.FAVORITE, 0); //todo attento, favs resettati

            getContentResolver().insert(AppContract.AppContractElement.CONTENT_URI, values);
        }

    }

    private void deleteAll(){
        getContentResolver().delete(AppContract.AppContractElement.CONTENT_URI, null,null);
    }

}
