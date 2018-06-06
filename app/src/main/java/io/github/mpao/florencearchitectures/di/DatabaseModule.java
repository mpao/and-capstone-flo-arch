package io.github.mpao.florencearchitectures.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import io.github.mpao.florencearchitectures.models.databases.AppDatabase;

@Module
public class DatabaseModule {

    private Context context;
    public static final int VERSION = 1;

    public DatabaseModule(Application app){

        this.context = app.getApplicationContext();

    }

    @Provides
    @Singleton
    AppDatabase getDatabase(){

        // ATTENTION: fallbackToDestructiveMigration destroys db on version update
        // or schema's changes
        return Room
                .databaseBuilder(context, AppDatabase.class, "app-data")
                .fallbackToDestructiveMigration()
                .build();

    }

}