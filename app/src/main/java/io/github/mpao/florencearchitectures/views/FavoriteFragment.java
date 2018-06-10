package io.github.mpao.florencearchitectures.views;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Arrays;
import java.util.Objects;
import io.github.mpao.florencearchitectures.R;
import io.github.mpao.florencearchitectures.databinding.FragmentFavoriteBinding;
import io.github.mpao.florencearchitectures.viewmodels.BuildingsListViewModel;
import io.github.mpao.florencearchitectures.views.adapters.FavoritesAdapter;
import io.github.mpao.florencearchitectures.views.custom.StaggedGridItemDecoration;

public class FavoriteFragment extends Fragment {

    private FragmentFavoriteBinding binding;
    private FragmentActivity activity;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        activity = Objects.requireNonNull(getActivity());

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false);
        binding.list.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        binding.list.addItemDecoration(new StaggedGridItemDecoration(activity, R.dimen.default_margin, 2));
        binding.list.setLayoutManager(lm);

        this.observe();

        return binding.getRoot();

    }

    /*
     * ViewModel for the fragment. The access to the database is executed only if
     * there is not any saved instance state, that is on the first creation
     */
    private void observe(){
        BuildingsListViewModel viewModel = ViewModelProviders.of(activity).get(BuildingsListViewModel.class);
        viewModel.getList().observe(this, list ->{
            if( list != null) {
                FavoritesAdapter adapter = new FavoritesAdapter(activity, Arrays.asList(list));
                binding.list.setAdapter(adapter);
            }
        });

    }

}
