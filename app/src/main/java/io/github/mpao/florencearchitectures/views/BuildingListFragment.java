package io.github.mpao.florencearchitectures.views;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Arrays;
import io.github.mpao.florencearchitectures.R;
import io.github.mpao.florencearchitectures.databinding.FragmentListBinding;
import io.github.mpao.florencearchitectures.viewmodels.BuildingsListViewModel;
import io.github.mpao.florencearchitectures.views.adapters.BuildingsAdapter;

public class BuildingListFragment extends Fragment {

    private FragmentListBinding binding;

    public BuildingListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        binding.list.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        binding.list.setLayoutManager(lm);

        this.observe(savedInstanceState);

        return binding.getRoot();

    }

    /*
     * ViewModel for the fragment. The access to the database is executed only if
     * there is not any saved instance state, that is on the first creation
     */
    private void observe(Bundle savedInstanceState){

        BuildingsListViewModel viewModel = ViewModelProviders.of(this).get(BuildingsListViewModel.class);
        if(savedInstanceState == null ) {
            viewModel.init();
        }
        viewModel.getList().observe(this, list ->{
            if( list != null) {
                BuildingsAdapter adapter = new BuildingsAdapter(Arrays.asList(list));
                binding.list.setAdapter(adapter);
            }
        });

    }

}
