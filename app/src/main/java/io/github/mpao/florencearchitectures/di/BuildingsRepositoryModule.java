package io.github.mpao.florencearchitectures.di;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import io.github.mpao.florencearchitectures.models.repositories.BuildingsRepository;
import io.github.mpao.florencearchitectures.models.repositories.BuildingsRepositoryImpl;

@Module
public class BuildingsRepositoryModule {

    @Provides
    @Singleton
    BuildingsRepository provides(){
        return new BuildingsRepositoryImpl();
    }

}
