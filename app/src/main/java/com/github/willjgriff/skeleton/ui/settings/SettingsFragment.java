package com.github.willjgriff.skeleton.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.ui.itemdetailtest.ItemDetailActivity;

/**
 * Created by Will on 17/08/2016.
 */

public class SettingsFragment extends Fragment {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_settings, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		view.findViewById(R.id.fragment_settings_open_people).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getContext().startActivity(new Intent(getContext(), ItemDetailActivity.class));
			}
		});
	}
}
