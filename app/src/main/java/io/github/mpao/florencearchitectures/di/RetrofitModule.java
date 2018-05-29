package io.github.mpao.florencearchitectures.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import io.github.mpao.florencearchitectures.entities.Building;
import io.github.mpao.florencearchitectures.entities.BuildingDeserializer;
import io.github.mpao.florencearchitectures.models.networks.OpenDataApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {

    private final static String FLORENCE_OPENDATA = "http://opendata.comune.fi.it/";

    @Singleton
    @Provides
    public OpenDataApi provideData(){

        Gson gson = new GsonBuilder().registerTypeAdapter(Building[].class, new BuildingDeserializer()).create();
        Retrofit retrofit = getRetrofit(gson);
        return retrofit.create(OpenDataApi.class);

    }

    private Retrofit getRetrofit(Gson gson){

        return new Retrofit.Builder()
                .baseUrl(FLORENCE_OPENDATA)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }

}
