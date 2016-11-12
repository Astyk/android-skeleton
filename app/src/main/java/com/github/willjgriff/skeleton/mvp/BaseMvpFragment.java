package com.github.willjgriff.skeleton.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Will on 12/11/2016.
 */

public abstract class BaseMvpFragment<VIEW, PRESENTER extends MvpPresenter<VIEW>> extends Fragment {

	private PRESENTER mPresenter;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		setRetainInstance(true);
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		getPresenter().bindView(getMvpView());
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onDestroyView() {
		getPresenter().unbindView();
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		getPresenter().close();
		super.onDestroy();
	}

	protected PRESENTER getPresenter() {
		if (mPresenter == null) {
			mPresenter = createPresenter();
		}
		return mPresenter;
	}

	protected abstract VIEW getMvpView();

	protected abstract PRESENTER createPresenter();
}
