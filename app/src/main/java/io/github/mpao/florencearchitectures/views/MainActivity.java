package io.github.mpao.florencearchitectures.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import io.github.mpao.florencearchitectures.R;
import io.github.mpao.florencearchitectures.models.networks.OpenDataService;

public class MainActivity extends AppCompatActivity {

    private static final String FRAGMENT_STATE = "fragment_save";
    private static final String HOME_TAG = "home";
    private static final String MAP_TAG  = "map";
    private static final String FAVS_TAG = "favs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme); // see splash_screen.xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        // restore or create default fragment
        if(savedInstanceState == null){
            manageFragment(HOME_TAG);
        }else{
            String tag = savedInstanceState.getString(FRAGMENT_STATE);
            assert tag != null;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment fragment = fragmentManager.findFragmentByTag(tag);
            fragmentTransaction.attach(fragment);
            fragmentTransaction.setPrimaryNavigationFragment(fragment);
            fragmentTransaction.setReorderingAllowed(true);
            fragmentTransaction.commitNowAllowingStateLoss();
        }

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home: manageFragment(HOME_TAG); return true;
                case R.id.navigation_map: manageFragment(MAP_TAG); return true;
                case R.id.navigation_favorite: manageFragment(FAVS_TAG); return true;
            }
            return false;
        });

        //todo manage data
        Intent intent = new Intent(this, OpenDataService.class);
        startService(intent);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager fragmentManager = getSupportFragmentManager();
        outState.putString(FRAGMENT_STATE, fragmentManager.getPrimaryNavigationFragment().getTag());
    }

    /*
     * Fragment management
     */
    private void manageFragment(String tag){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if(fragment == null) {
            switch (tag){
                case HOME_TAG: fragment = new BuildingListFragment(); break; //new CategoryFragment()
                case MAP_TAG : fragment = new MapFragment(); break;
                case FAVS_TAG: fragment = new FavoriteFragment(); break;
            }
            fragmentTransaction.add(R.id.content, fragment, tag);
        }else{
            fragmentTransaction.attach(fragment);
        }

        Fragment current = fragmentManager.getPrimaryNavigationFragment();
        if(current != null) {
            fragmentTransaction.detach(current);
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNowAllowingStateLoss();

    }

}
