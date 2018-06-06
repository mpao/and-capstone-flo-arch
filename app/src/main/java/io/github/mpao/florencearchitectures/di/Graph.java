package io.github.mpao.florencearchitectures.di;

import javax.inject.Singleton;
import dagger.Component;
import io.github.mpao.florencearchitectures.models.networks.OpenDataService;
import io.github.mpao.florencearchitectures.models.repositories.BuildingsRepositoryImpl;
import io.github.mpao.florencearchitectures.viewmodels.BuildingsListViewModel;

@Singleton
@Component(modules = {
        ContextModule.class,
        RetrofitModule.class,
        DatabaseModule.class,
        BuildingsRepositoryModule.class
})

public interface Graph {
    void inject(OpenDataService openDataService);
    void inject(BuildingsListViewModel buildingsListViewModel);
    void inject(BuildingsRepositoryImpl buildingsRepository);
}
