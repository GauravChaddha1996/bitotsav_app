package com.bitmesra.bitotsav.features;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.bitmesra.bitotsav.R;
import com.bitmesra.bitotsav.base.BaseFragment;
import com.bitmesra.bitotsav.features.csa.CSAActivity;
import com.bitmesra.bitotsav.features.events.EventsFragment;
import com.bitmesra.bitotsav.features.events.timeline.TimelineFragment;
import com.bitmesra.bitotsav.features.flagships.FlagshipFragment;
import com.bitmesra.bitotsav.features.home.HomeFragment;
import com.bitmesra.bitotsav.ui.CustomTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public int dayNumber;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    CustomTextView toolbarTitle;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    private BaseFragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setUpNavigationDrawer();
        setFragment(IdForFragment.HOME);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (currentFragment.getBackToFragmentId() != null) {
                setFragment(currentFragment.getBackToFragmentId());
            } else {
                super.onBackPressed();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            setFragment(IdForFragment.HOME);
        } else if (id == R.id.nav_flagship) {
            setFragment(IdForFragment.FLAGSHIP);
        } else if (id == R.id.nav_events) {
            setFragment(IdForFragment.EVENTS);
        } else if (id == R.id.nav_contact_about_sponsor) {
            startActivity(new Intent(MainActivity.this, CSAActivity.class),
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setUpNavigationDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
    }

    private BaseFragment handleNavViewTransition(IdForFragment idForFragment) {
        BaseFragment toReturnFragment = new HomeFragment();
        switch (idForFragment) {
            case HOME:
                navView.setCheckedItem(R.id.nav_home);
                toolbarTitle.setText("Home");
                toReturnFragment = new HomeFragment();
                break;
            case FLAGSHIP:
                navView.setCheckedItem(R.id.nav_events);
                toolbarTitle.setText("Flagships");
                toReturnFragment = new FlagshipFragment();
                break;
            case EVENTS:
                navView.setCheckedItem(R.id.nav_events);
                toolbarTitle.setText("Events");
                toReturnFragment = new EventsFragment();
                break;
            case TIMELINE:
                navView.setCheckedItem(R.id.nav_events);
                toolbarTitle.setText("Day " + dayNumber + " Timeline");
                toReturnFragment = new TimelineFragment();
                break;
        }
        return toReturnFragment;
    }

    public BaseFragment getCurrentFragment() {
        return currentFragment;
    }

    public void setFragment(IdForFragment idForFragment) {
        if (currentFragment != null && idForFragment == currentFragment.getFragmentId()) {
            return;
        }
        BaseFragment newFragment = handleNavViewTransition(idForFragment);
        currentFragment = newFragment;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in_slow,R.anim.fade_out_fast);
        transaction.replace(R.id.homeFrameLayout, newFragment);
        transaction.commit();
        appBarLayout.setExpanded(true, true);
    }

}
