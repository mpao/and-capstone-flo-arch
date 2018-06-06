package io.github.mpao.florencearchitectures.models.repositories;

import android.arch.lifecycle.LiveData;
import javax.inject.Inject;
import io.github.mpao.florencearchitectures.di.App;
import io.github.mpao.florencearchitectures.entities.Building;
import io.github.mpao.florencearchitectures.models.databases.AppDatabase;

public class BuildingsRepositoryImpl implements BuildingsRepository {

    @Inject AppDatabase database;

    public BuildingsRepositoryImpl(){
        App.graph.inject(this);
    }

    @Override
    public LiveData<Building[]> getAll() {
        return database.buildingDao().getAll();
    }

}
