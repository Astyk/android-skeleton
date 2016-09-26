package com.github.willjgriff.skeleton.ui.people;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.data.models.Person;

/**
 * Created by Will on 22/09/2016.
 */

public class PersonDetailsFragment extends Fragment {

	public static final String ARG_PERSON_FOR_FRAGMENT = "com.github.willjgriff.skeleton.ui.itemdetaillist.PersonDetailFragment;ARG_PERSON_FOR_ACTIVITY";

	public static PersonDetailsFragment createInstance(Person person) {
		PersonDetailsFragment personDetailsFragment = new PersonDetailsFragment();
		Bundle arguments = new Bundle();
		arguments.putParcelable(ARG_PERSON_FOR_FRAGMENT, person);
		personDetailsFragment.setArguments(arguments);

		return personDetailsFragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_person_details, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		TextView textView = (TextView) view.findViewById(R.id.fragment_person_details_text);
		if (getArguments().getParcelable(ARG_PERSON_FOR_FRAGMENT) != null) {
			Person person = getArguments().getParcelable(ARG_PERSON_FOR_FRAGMENT);
			textView.setText(person.getEmail());
		}
	}
}
