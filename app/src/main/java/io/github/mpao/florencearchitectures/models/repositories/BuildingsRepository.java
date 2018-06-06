package io.github.mpao.florencearchitectures.models.repositories;

import android.arch.lifecycle.LiveData;
import io.github.mpao.florencearchitectures.entities.Building;

public interface BuildingsRepository {
    LiveData<Building[]> getAll();
}
