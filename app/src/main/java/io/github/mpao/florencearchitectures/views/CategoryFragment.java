package io.github.mpao.florencearchitectures.views;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.github.mpao.florencearchitectures.R;
import io.github.mpao.florencearchitectures.models.databases.AppContract;
import io.github.mpao.florencearchitectures.views.adapters.CategoriesAdapter;

public class CategoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView list;
    private Activity activity;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.activity = getActivity();
        getLoaderManager().initLoader(1, null, this);
        View root = inflater.inflate(R.layout.fragment_category, container, false);
        list = root.findViewById(R.id.list);
        list.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getActivity());
        list.setLayoutManager(lm);
        return root;

    }

    /* manage fragment's data. not necessary at the moment, the fragment state is
    fully saved in the activity, recyclerview position included
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            Parcelable save = savedInstanceState.getParcelable(LIST_STATE);
            list.getLayoutManager().onRestoreInstanceState(save);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        Parcelable save = list.getLayoutManager().onSaveInstanceState();
        state.putParcelable(LIST_STATE, save);
    }
    */

    @NonNull
    @Override
    public CursorLoader onCreateLoader(int id, Bundle args) {

        return new CursorLoader(
                activity,
                AppContract.AppContractElement.CATEGORY_URI,
                null,
                null,
                null,
                null
        );

    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Cursor cursor) {

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
    public void onLoaderReset(@NonNull Loader loader) {
        list.setAdapter(null);
    }

}
