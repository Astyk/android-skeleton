package com.github.willjgriff.skeleton.ui.form;

import android.content.Context;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.mvp.BaseMvpFragment;
import com.github.willjgriff.skeleton.ui.form.mvp.FormPresenter;
import com.github.willjgriff.skeleton.ui.form.mvp.FormView;
import com.github.willjgriff.skeleton.ui.navigation.NavigationToolbarListener;

/**
 * Created by Will on 28/09/2016.
 */

public class FormFragment extends BaseMvpFragment<FormView, FormPresenter> implements FormView {

	private NavigationToolbarListener mNavigationToolbarListener;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		mNavigationToolbarListener = (NavigationToolbarListener) context;
	}

	@Override
	public void onResume() {
		super.onResume();
		mNavigationToolbarListener.setToolbarTitle(R.string.fragment_form_title);
	}

	@Override
	protected FormView getMvpView() {
		return this;
	}

	@Override
	public FormPresenter createPresenter() {
		return new FormPresenter();
	}
}
