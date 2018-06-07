package io.github.mpao.florencearchitectures.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.google.android.gms.maps.model.LatLng;
import javax.inject.Inject;
import io.github.mpao.florencearchitectures.di.App;
import io.github.mpao.florencearchitectures.entities.Building;
import io.github.mpao.florencearchitectures.models.repositories.BuildingsRepository;

public class BuildingsListViewModel extends ViewModel {

    private LiveData<Building[]> list;
    private LatLng position;
    @Inject BuildingsRepository repo;

    public BuildingsListViewModel(){

        App.graph.inject(this);
        this.list = repo.getAll();

    }

    public LiveData<Building[]> getList() {

        return this.list;

    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

}
