package io.github.mpao.florencearchitectures.di;

import android.app.Application;

public class App extends Application {

    public static Graph graph;
    public static final String INTENT_BUILDING = "parcelable building intent tag";

    @Override
    public void onCreate(){

        super.onCreate();
        graph = DaggerGraph.builder()
                .contextModule( new ContextModule(this) )
                .retrofitModule( new RetrofitModule() )
                .databaseModule( new DatabaseModule(this) )
                .buildingsRepositoryModule( new BuildingsRepositoryModule() )
                .build();

    }

}
