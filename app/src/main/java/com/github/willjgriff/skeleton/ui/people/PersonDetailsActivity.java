package com.github.willjgriff.skeleton.ui.people;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.data.models.Person;

/**
 * Created by Will on 24/09/2016.
 */

public class PersonDetailsActivity extends AppCompatActivity {

	public static final String ARG_PERSON_FOR_ACTIVITY = "com.github.willjgriff.skeleton.ui.people.PersonDetailActivity;ARG_PERSON_FOR_ACTIVITY";

	public static Intent getIntent(Context context, Person person) {
		Intent personDetailsIntent = new Intent(context, PersonDetailsActivity.class);
		personDetailsIntent.putExtra(ARG_PERSON_FOR_ACTIVITY, person);
		return personDetailsIntent;
	}

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
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	private boolean canAddFragment(@Nullable Bundle savedInstanceState) {
		return savedInstanceState == null && getIntent().hasExtra(ARG_PERSON_FOR_ACTIVITY);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
