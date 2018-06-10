package io.github.mpao.florencearchitectures.models.databases;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import io.github.mpao.florencearchitectures.entities.Building;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface BuildingDAO {

    @Query("SELECT * FROM building")
    LiveData<Building[]> getAll();

    @Query("SELECT * FROM building")
    Building[] getAllForWidget();

    @Insert(onConflict = REPLACE)
    void insertAll(Building... buildings);

    // select single element by ID
    @Query("select * from building where name = :id")
    LiveData<Building> getBuilding(String id);

    // count element in building table
    @Query("select count(*) from building")
    int countBuildings();

    @Query("update building set favorite = 1 where name = :id")
    void insertFavorite(String id);

    @Query("update building set favorite = 0 where name = :id")
    void deleteFavorite(String id);

}
