package io.github.mpao.florencearchitectures.models.networks;

import io.github.mpao.florencearchitectures.entities.Building;
import retrofit2.Call;
import retrofit2.http.GET;

public interface OpenDataApi {

    @GET("/od/architetture_900.json")
    Call<Building[]> get();

}
