package io.github.mpao.florencearchitectures.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import javax.inject.Inject;
import io.github.mpao.florencearchitectures.di.App;
import io.github.mpao.florencearchitectures.entities.Building;
import io.github.mpao.florencearchitectures.models.repositories.BuildingsRepository;

public class BuildingsListViewModel extends ViewModel {

    private LiveData<Building[]> list;
    @Inject BuildingsRepository repo;

    public BuildingsListViewModel(){

        App.graph.inject(this);

    }

    public void init(){

        this.list = repo.getAll();

    }

    public LiveData<Building[]> getList() {

        return this.list;

    }

}
