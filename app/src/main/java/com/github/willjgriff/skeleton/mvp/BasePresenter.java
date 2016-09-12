package com.github.willjgriff.skeleton.mvp;

/**
 * Created by Will on 15/08/2016.
 */

public interface BasePresenter<VIEW> {

	void bindView(VIEW view);

	void unbindView();

	void onDestroy();
}
