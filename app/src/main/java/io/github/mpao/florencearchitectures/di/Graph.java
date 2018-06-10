package io.github.mpao.florencearchitectures.di;

import javax.inject.Singleton;
import dagger.Component;
import io.github.mpao.florencearchitectures.models.networks.OpenDataService;
import io.github.mpao.florencearchitectures.models.repositories.BuildingsRepositoryImpl;
import io.github.mpao.florencearchitectures.models.repositories.FavoritesRepoImpl;
import io.github.mpao.florencearchitectures.viewmodels.BuildingsListViewModel;
import io.github.mpao.florencearchitectures.viewmodels.DetailViewModel;

@Singleton
@Component(modules = {
        ContextModule.class,
        RetrofitModule.class,
        DatabaseModule.class,
        BuildingsRepositoryModule.class,
        FavoritesRepoModule.class
})

public interface Graph {
    void inject(OpenDataService openDataService);
    void inject(BuildingsListViewModel buildingsListViewModel);
    void inject(BuildingsRepositoryImpl buildingsRepository);
    void inject(DetailViewModel detailViewModel);
    void inject(FavoritesRepoImpl favoritesRepo);
}
