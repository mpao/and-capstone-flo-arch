package io.github.mpao.florencearchitectures.di;

import javax.inject.Singleton;
import dagger.Component;
import io.github.mpao.florencearchitectures.models.networks.OpenDataService;

@Singleton
@Component(modules = {
        ContextModule.class,
        RetrofitModule.class
})

public interface Graph {
    void inject(OpenDataService openDataService);
}
