package io.github.mpao.florencearchitectures.viewmodels;

import android.arch.lifecycle.ViewModel;
import javax.inject.Inject;
import io.github.mpao.florencearchitectures.di.App;
import io.github.mpao.florencearchitectures.models.repositories.FavoritesRepo;

public class DetailViewModel extends ViewModel {

    @Inject FavoritesRepo repo;

    public DetailViewModel(){
        App.graph.inject(this);
    }

    public void setFavorite(String id){
        repo.insertFavorite(id);
    }

    public void deleteFavorite(String id){
        repo.deleteFavorite(id);
    }

}
