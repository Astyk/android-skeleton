package com.github.willjgriff.skeleton.ui.navigation;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.ui.land.LandFragment;
import com.github.willjgriff.skeleton.ui.settings.SettingsFragment;

public class NavigationActivity extends AppCompatActivity {

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private NavigationView mNavigationView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);

		setupToolbar();
		setupNavigationView();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_navigation_drawer);
		setNavigationToggle(mDrawerLayout);
	}

	private void setupNavigationView() {
		mNavigationView = (NavigationView) findViewById(R.id.activity_navigation_nav_view);
		mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(MenuItem item) {
				switchNavigationItem(item);
				return false;
			}

		});
	}

	private void setupToolbar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.activity_navigation_toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	private void setNavigationToggle(DrawerLayout drawerLayout) {
		mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
			R.string.activity_navigation_open, R.string.activity_navigation_close);
		mDrawerToggle.setDrawerIndicatorEnabled(true);
		drawerLayout.addDrawerListener(mDrawerToggle);
	}

	private void switchNavigationItem(MenuItem item) {
		Fragment fragment;
		switch (item.getItemId()) {
			case R.id.navigation_first_fragment:
				fragment = new LandFragment();
				break;
			case R.id.navigation_settings:
				fragment = new SettingsFragment();
				break;
			default:
				fragment = new LandFragment();
		}

		switchToFragment(fragment);
		refreshLayout(item);
	}

	private void refreshLayout(MenuItem item) {
		item.setChecked(true);
		getSupportActionBar().setTitle(item.getTitle());
		mDrawerLayout.closeDrawers();
	}

	private void switchToFragment(Fragment fragment) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
			.replace(R.id.activity_navigation_container, fragment)
			.addToBackStack(fragment.getClass().getName())
			.commit();
	}

	@Override
	protected void onPostCreate(@Nullable Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		} else if (item.getItemId() == android.R.id.home) {
			mDrawerLayout.openDrawer(GravityCompat.START);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
