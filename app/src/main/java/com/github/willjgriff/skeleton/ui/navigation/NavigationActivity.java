package com.github.willjgriff.skeleton.ui.navigation;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.ui.people.PeopleFragment;

public class NavigationActivity extends AppCompatActivity implements NavigationToolbarListener, DetailFragmentListener {

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ProgressBar mProgressBar;
	private ComponentsInvalidator mComponentsInvalidator;
	private NavigationFragmentFactory mFragmentFactory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navigation);

		setupToolbar();
		setupNavigationView();

		mComponentsInvalidator = new ComponentsInvalidator();
		mFragmentFactory = new NavigationFragmentFactory();

		if (savedInstanceState == null) {
			switchToNavigationFragment(new PeopleFragment());
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
		mProgressBar = (ProgressBar) findViewById(R.id.toolbar_progress_bar);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_navigation_drawer);

		setSupportActionBar(toolbar);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setNavigationToggle(mDrawerLayout);
	}

	private void setupNavigationView() {
		NavigationView navigationView = (NavigationView) findViewById(R.id.activity_navigation_nav_view);
		navigationView.setNavigationItemSelectedListener(item -> {
			switchNavigationItem(item);
			return false;
		});
	}

	private void setNavigationToggle(DrawerLayout drawerLayout) {
		mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
			R.string.activity_navigation_open, R.string.activity_navigation_close);

		mDrawerToggle.setDrawerIndicatorEnabled(true);
		drawerLayout.addDrawerListener(mDrawerToggle);
	}

	private void switchNavigationItem(MenuItem item) {
		item.setChecked(true);
		Fragment navigationFragment = mFragmentFactory.getFragmentFromId(item.getItemId());
		switchToNavigationFragment(navigationFragment);
		mDrawerLayout.closeDrawers();

		mComponentsInvalidator.invalidateComponents();
	}

	private void switchToNavigationFragment(Fragment navigationFragment) {
		if (isDifferentFragment(navigationFragment)) {
			switchFragmentInContainer(navigationFragment, R.id.activity_navigation_container);
			closeDetailFragment();
		}
	}

	private boolean isDifferentFragment(Fragment navigationFragment) {
		Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.activity_navigation_container);
		return currentFragment == null || !currentFragment.getTag().equals(navigationFragment.getClass().toString());
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
	public boolean isFinishing() {
		return super.isFinishing();
	}

	@Override
	public void showNetworkLoadingView() {
		if (View.GONE == mProgressBar.getVisibility()) {
			mProgressBar.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void hideNetworkLoadingView() {
		if (View.VISIBLE == mProgressBar.getVisibility()) {
			mProgressBar.setVisibility(View.GONE);
		}
	}

	@Override
	public void setToolbarTitle(@StringRes int toolbarTitle) {
		getSupportActionBar().setTitle(toolbarTitle);
	}

	@Override
	public void openDetailFragment(Fragment fragment) {
		setDetailsContainerVisibility(View.VISIBLE);
		switchFragmentInContainer(fragment, R.id.activity_navigation_details_container);
	}

	@Override
	public void closeDetailFragment() {
		Fragment detailsFragment = getSupportFragmentManager().findFragmentById(R.id.activity_navigation_details_container);
		if (detailsFragment != null) {
			getSupportFragmentManager().beginTransaction().remove(detailsFragment).commit();
			setDetailsContainerVisibility(View.GONE);
		}
	}

	@Override
	public boolean twoPaneViewEnabled() {
		return findViewById(R.id.activity_navigation_details_container) != null
			&& findViewById(R.id.activity_navigation_container_divider) != null;
	}

	private void setDetailsContainerVisibility(int visible) {
		if (twoPaneViewEnabled()) {
			findViewById(R.id.activity_navigation_details_container).setVisibility(visible);
			findViewById(R.id.activity_navigation_container_divider).setVisibility(visible);
		}
	}

	private void switchFragmentInContainer(Fragment navigationFragment, @IdRes int container) {
		getSupportFragmentManager()
			.beginTransaction()
			.replace(container,
				navigationFragment,
				navigationFragment.getClass().toString())
			.commit();
	}
}
