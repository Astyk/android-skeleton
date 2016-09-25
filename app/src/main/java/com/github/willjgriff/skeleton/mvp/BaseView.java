package com.github.willjgriff.skeleton.mvp;

/**
 * Created by Will on 15/08/2016.
 */
// TODO: Can this be removed?
public interface BaseView<PRESENTER extends BasePresenter> {

	PRESENTER getPresenter();

}
