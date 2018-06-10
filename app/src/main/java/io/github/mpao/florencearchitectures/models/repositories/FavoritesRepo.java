package io.github.mpao.florencearchitectures.models.repositories;

import android.arch.lifecycle.LiveData;
import io.github.mpao.florencearchitectures.entities.Building;

public interface FavoritesRepo {

    LiveData<Building> get(String id);
    void deleteFavorite(String id);
    void insertFavorite(String id);

}
