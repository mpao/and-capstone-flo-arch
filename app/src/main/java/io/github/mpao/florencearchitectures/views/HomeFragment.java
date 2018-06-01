package io.github.mpao.florencearchitectures.views;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.github.mpao.florencearchitectures.R;
import io.github.mpao.florencearchitectures.models.databases.AppContract;
import io.github.mpao.florencearchitectures.views.adapters.CategoriesAdapter;

public class HomeFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView list;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getLoaderManager().initLoader(1, null, this);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        list = root.findViewById(R.id.list);
        list.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        list.setLayoutManager(lm);
        return root;

    }

    //todo restore rv position on rotation

    @Override
    public CursorLoader onCreateLoader(int id, Bundle args) {

        return new CursorLoader(
                getActivity(),
                AppContract.AppContractElement.CATEGORY_URI,
                null,
                null,
                null,
                null
        );

    }

    @Override
    public void onLoadFinished(Loader loader, Cursor cursor) {

        /*
        while (cursor.moveToNext()){
            String title = cursor.getString(0);
            String image = cursor.getString(1);
            Log.d("FROM DB", title+" "+image);
        }
        cursor.close();
        */
        CategoriesAdapter adapter = new CategoriesAdapter(getActivity(), cursor);
        list.setAdapter(adapter);

    }

    @Override
    public void onLoaderReset(Loader loader) {
        list.setAdapter(null);
    }

}
