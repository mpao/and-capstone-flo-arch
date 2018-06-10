package io.github.mpao.florencearchitectures.di;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import io.github.mpao.florencearchitectures.models.repositories.FavoritesRepo;
import io.github.mpao.florencearchitectures.models.repositories.FavoritesRepoImpl;

@Module
public class FavoritesRepoModule {

    @Provides
    @Singleton
    public FavoritesRepo provides(){
        return new FavoritesRepoImpl();
    }

}
