package com.romariomkk.netflixrouletteclient.activities;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.romariomkk.netflixrouletteclient.R;
import com.romariomkk.netflixrouletteclient.frags.BasicFragment;
import com.romariomkk.netflixrouletteclient.frags.DirectorSearchFragment;
import com.romariomkk.netflixrouletteclient.frags.SavedMoviesFragment;
import com.romariomkk.netflixrouletteclient.frags.TitleSearchFragment;

import java.util.Objects;

public class NavigationViewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TitleSearchFragment.OnFragmentInteractionListener {

    FragmentManager fragManager = getSupportFragmentManager();
    Fragment fragToDisplay;
    String mainTitle;
    int currPageID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        refreshForeground();

        setupNavViewDrawer(toolbar);

        setupNavigView();
    }

    private void setupNavViewDrawer(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.layout_drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setupNavigView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.layout_drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        refreshForeground();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.layout_drawer);
        int id = item.getItemId();
        switch (id) {
            case (R.id.title_search):
                if (isNotTheSameID(R.id.title_search)) {
                    setForegroundFrag(new TitleSearchFragment(), "Search By Title", R.id.title_search);
                }
                Log.i("UIok", "TitleSearchFragment setup on foreground");
                break;
            case (R.id.director_search):
                if (isNotTheSameID(R.id.director_search)) {
                    setForegroundFrag(new DirectorSearchFragment(), "Search By Director", R.id.director_search);
                }
                Log.i("UIok", "DirectorSearchFragment setup on foreground");
                break;
            case (R.id.saved_movies):
                if (isNotTheSameID(R.id.saved_movies)) {
                    setForegroundFrag(new SavedMoviesFragment(), "Saved", R.id.saved_movies);
                }
                Log.i("UIok", "SavedMoviesFragment setup on foreground");
                break;
        }
        refreshForeground();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isNotTheSameID(int id) {
        return currPageID != id;
    }

    public void refreshForeground() {
        if (fragToDisplay == null) {
            setForegroundFrag(new SavedMoviesFragment(), "Saved", R.id.saved_movies);
        }
        fragManager.beginTransaction().replace(R.id.main_content_layout, fragToDisplay).commitAllowingStateLoss();
        setToolbarTitle();
    }

    private void setForegroundFrag(BasicFragment frag, String title, int pageId) {
        fragToDisplay = frag;
        mainTitle = title;
        currPageID = pageId;
    }

    private void setToolbarTitle() {
        try {
            getSupportActionBar().setTitle(Objects.requireNonNull(mainTitle));
        } catch (NullPointerException exc) {
            Log.e("UIerr", "Action Bar title not instantiated");
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}