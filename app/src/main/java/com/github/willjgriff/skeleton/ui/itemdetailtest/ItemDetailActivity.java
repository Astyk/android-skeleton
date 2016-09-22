package com.github.willjgriff.skeleton.ui.itemdetailtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.ui.land.LandFragment;
import com.github.willjgriff.skeleton.ui.land.LandFragment.PeopleDetailListener;
import com.github.willjgriff.skeleton.ui.navigation.NavigationToolbarListener;

/**
 * Created by Will on 22/09/2016.
 */

public class ItemDetailActivity extends AppCompatActivity implements PeopleDetailListener, NavigationToolbarListener {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_detail);

		if (savedInstanceState == null) {
			getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.activity_item_detail_fragment_container, new LandFragment())
				.commit();
		}
	}

	@Override
	public void openDetailFragment(Person person) {
		if (isTwoPane()) {
			getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.activity_item_detail_detail_fragment_container, PersonDetailFragment.createInstance(person))
				// TODO: Use an ID
				.addToBackStack(person.getEmail())
				.commit();
		} else {
			getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.activity_item_detail_fragment_container, PersonDetailFragment.createInstance(person))
				// TODO: Use an ID
				.addToBackStack(person.getEmail())
				.commit();
		}
	}

	private boolean isTwoPane() {
		return findViewById(R.id.activity_item_detail_detail_fragment_container) != null;
	}

	@Override
	public void showNetworkLoadingView() {
		// no-op
	}

	@Override
	public void hideNetworkLoadingView() {
		// no-op
	}

	@Override
	public void setToolbarTitle(@StringRes int toolbarTitleRes) {
		//no-op
	}
}
