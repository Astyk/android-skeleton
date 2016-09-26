package com.github.willjgriff.skeleton.ui.people;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.ui.utils.UiUtils;

/**
 * Created by Will on 24/09/2016.
 */

public class PersonDetailsActivity extends AppCompatActivity {

	public static final String ARG_PERSON_FOR_ACTIVITY = "com.github.willjgriff.skeleton.ui.people.PersonDetailActivity;ARG_PERSON_FOR_ACTIVITY";

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_details);

		setupToolbar();

		if (canAddFragment(savedInstanceState)) {
			Person person = getIntent().getParcelableExtra(ARG_PERSON_FOR_ACTIVITY);
			Fragment personDetailsFragment = PersonDetailsFragment.createInstance(person);
			getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.activity_person_details_container, personDetailsFragment)
				.commit();
		}
	}

	private void setupToolbar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.activity_person_details_toolbar);
		toolbar.setTitle(getString(R.string.fragment_person_title));
		ViewCompat.setElevation(toolbar, UiUtils.convertDpToPixel(4, this));
		setSupportActionBar(toolbar);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	private boolean canAddFragment(@Nullable Bundle savedInstanceState) {
		return savedInstanceState == null && getIntent().hasExtra(ARG_PERSON_FOR_ACTIVITY);
	}
}
