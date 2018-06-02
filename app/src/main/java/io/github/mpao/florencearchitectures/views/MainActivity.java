package io.github.mpao.florencearchitectures.views;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import io.github.mpao.florencearchitectures.R;
import io.github.mpao.florencearchitectures.models.networks.OpenDataService;
import static android.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

public class MainActivity extends AppCompatActivity {

    private static final String FRAGMENT_NORMAL = "FRAGMENT_NORMAL";
    private BottomNavigationView navigation;
    private Fragment fragment;
    private static final String FRAGMENT_STATE = "fragment_save";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme); // see splash_screen.xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar));

        // restore or create fragment
        fragment = savedInstanceState == null ? new HomeFragment() : getFragmentManager().getFragment(savedInstanceState, FRAGMENT_STATE);
        viewFragment( fragment );

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewFragment(new HomeFragment());
                    return true;
                case R.id.navigation_map:
                    viewFragment(new MapFragment(), FRAGMENT_NORMAL);
                    return true;
                case R.id.navigation_favorite:
                    viewFragment(new FavoriteFragment(), FRAGMENT_NORMAL);
                    return true;
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
        getFragmentManager().putFragment(outState, FRAGMENT_STATE, fragment);
    }


    /*
     * Fragment management
     * https://stackoverflow.com/a/44190200/1588252
     * That answer is a one of mine, it's a my solution
     */
    private void viewFragment(Fragment fragment, String name){

        this.fragment = fragment;
        final FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);

        if(name == null){
            fragmentManager.popBackStack(FRAGMENT_NORMAL, POP_BACK_STACK_INCLUSIVE);
            fragmentTransaction.commit();
            return;
        }

        final int count = fragmentManager.getBackStackEntryCount();
        if( name.equals( FRAGMENT_NORMAL) ) {
            fragmentTransaction.addToBackStack(name);
        }
        fragmentTransaction.commit();

        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if( fragmentManager.getBackStackEntryCount() <= count){
                    fragmentManager.popBackStack(FRAGMENT_NORMAL, POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.removeOnBackStackChangedListener(this);
                    navigation.getMenu().getItem(0).setChecked(true);
                }
            }
        });

    }

    /*
     * method overload for no named fragment
     */
    private void viewFragment(Fragment fragment){
        // normal back navigation, exit on back press
        viewFragment( fragment, null );

    }

}
