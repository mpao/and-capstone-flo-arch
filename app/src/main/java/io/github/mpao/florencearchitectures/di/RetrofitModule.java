package io.github.mpao.florencearchitectures.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.concurrent.Executors;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import io.github.mpao.florencearchitectures.entities.Building;
import io.github.mpao.florencearchitectures.models.networks.BuildingDeserializer;
import io.github.mpao.florencearchitectures.models.networks.OpenDataApi;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
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

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ResponseInterceptor())
                .build();

        // retrofit callbacks in another thread
        return new Retrofit.Builder()
                .baseUrl(FLORENCE_OPENDATA)
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

    }

    /*
     * Endpoint has a wrong encoding text
     * @see https://stackoverflow.com/q/45284974/1588252
     */
    private class ResponseInterceptor implements Interceptor{

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());
            MediaType m       = MediaType.parse("application/json; charset=windows-1252");
            ResponseBody body = response.body();
            if(body != null) {
                ResponseBody r = ResponseBody.create(m, body.bytes());
                return response.newBuilder()
                        .body(r)
                        .build();
            }
            return response;
        }

    }

}
