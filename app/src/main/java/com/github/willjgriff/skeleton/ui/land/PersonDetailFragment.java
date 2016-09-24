package com.github.willjgriff.skeleton.ui.land;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.mvp.RxFragment;

/**
 * Created by Will on 22/09/2016.
 */

public class PersonDetailFragment extends Fragment {

	public static final String ARG_PERSON = "com.github.willjgriff.skeleton.ui.itemdetaillist.PersonDetailFragment;ARG_PERSON";

	public static PersonDetailFragment createInstance(Person person) {
		PersonDetailFragment personDetailFragment = new PersonDetailFragment();
		Bundle arguments = new Bundle();
		arguments.putParcelable(ARG_PERSON, person);
		personDetailFragment.setArguments(arguments);

		return personDetailFragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_land_details, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		TextView textView = (TextView) view.findViewById(R.id.fragment_land_details_text);
		if (getArguments().getParcelable(ARG_PERSON) != null) {
			Person person = getArguments().getParcelable(ARG_PERSON);
			textView.setText(person.getEmail());
		}
	}
}
