package io.github.mpao.florencearchitectures.models.repositories;

import android.arch.lifecycle.LiveData;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.inject.Inject;
import io.github.mpao.florencearchitectures.di.App;
import io.github.mpao.florencearchitectures.entities.Building;
import io.github.mpao.florencearchitectures.models.databases.AppDatabase;

public class FavoritesRepoImpl implements FavoritesRepo {

    @Inject AppDatabase database;
    Executor executor = Executors.newFixedThreadPool(1);

    public FavoritesRepoImpl(){
        App.graph.inject(this);
    }

    @Override
    public LiveData<Building> get(String id) {
        return database.buildingDao().getBuilding(id);
    }

    @Override
    public void insertFavorite(String id) {
        executor.execute(() -> database.buildingDao().insertFavorite(id));
    }

    @Override
    public void deleteFavorite(String id) {
        executor.execute(() -> database.buildingDao().deleteFavorite(id));
    }

}
