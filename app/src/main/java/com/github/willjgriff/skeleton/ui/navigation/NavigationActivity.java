package com.github.willjgriff.skeleton.ui.navigation;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.notifications.FirebaseIdService;
import com.google.firebase.iid.FirebaseInstanceId;

public class NavigationActivity extends AppCompatActivity implements NavigationToolbarListener {

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private NavigationView mNavigationView;
	private ProgressBar mProgressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);

		Log.d("NOTIFI", FirebaseInstanceId.getInstance().getToken());

		setupToolbar();
		setupNavigationView();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_navigation_drawer);
		setNavigationToggle(mDrawerLayout);

		mProgressBar = (ProgressBar) findViewById(R.id.activity_navigation_toolbar_progress_bar);

		if (savedInstanceState == null) {
			switchToFragment(NavigationFragment.LAND);
		}
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

	private void setupToolbar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.activity_navigation_toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

	private void setNavigationToggle(DrawerLayout drawerLayout) {
		mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
			R.string.activity_navigation_open, R.string.activity_navigation_close);
		mDrawerToggle.setDrawerIndicatorEnabled(true);
		drawerLayout.addDrawerListener(mDrawerToggle);
	}

	private void switchNavigationItem(MenuItem item) {
		NavigationFragment navigationFragment;
		item.setChecked(true);
		switch (item.getItemId()) {
			case R.id.navigation_first_fragment:
				navigationFragment = NavigationFragment.LAND;
				break;
			case R.id.navigation_settings:
				navigationFragment = NavigationFragment.SETTINGS;
				break;
			default:
				navigationFragment = NavigationFragment.LAND;
		}

		switchToFragment(navigationFragment);
		mDrawerLayout.closeDrawers();

	}

	private void switchToFragment(NavigationFragment navigationFragment) {
		FragmentManager fragmentManager = getSupportFragmentManager();

		if (isDifferentFragment(navigationFragment)) {
			fragmentManager.beginTransaction()
				.replace(R.id.activity_navigation_container,
					Fragment.instantiate(this, navigationFragment.getFragmentClass().getName()),
					navigationFragment.toString())
				.commit();
			getSupportActionBar().setTitle(navigationFragment.getNavigationTitle());
		}
	}

	private boolean isDifferentFragment(NavigationFragment navigationFragment) {
		Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.activity_navigation_container);
		if (currentFragment != null) {
			return !currentFragment.getTag().equals(navigationFragment.toString());
		}
		return true;
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

	@Override
	public void showNetworkLoadingView() {
		mProgressBar.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideNetworkLoadingView() {
		mProgressBar.setVisibility(View.GONE);
	}

	@Override
	public void setToolbarTitle(@StringRes int toolbarTitle) {
		getSupportActionBar().setTitle(toolbarTitle);
	}

}
