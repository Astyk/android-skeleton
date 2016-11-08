package com.github.willjgriff.skeleton.ui.form;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.ui.navigation.NavigationToolbarListener;

/**
 * Created by Will on 28/09/2016.
 */

public class FormFragment extends Fragment {

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		((NavigationToolbarListener) context).setToolbarTitle(R.string.fragment_form_title);
	}
}
