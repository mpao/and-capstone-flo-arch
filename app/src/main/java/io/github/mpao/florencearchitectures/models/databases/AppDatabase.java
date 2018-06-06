package io.github.mpao.florencearchitectures.models.databases;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import io.github.mpao.florencearchitectures.di.DatabaseModule;
import io.github.mpao.florencearchitectures.entities.Building;

@Database(entities = {Building.class}, version = DatabaseModule.VERSION)
public abstract class AppDatabase extends RoomDatabase {

    public abstract BuildingDAO buildingDao();

}
