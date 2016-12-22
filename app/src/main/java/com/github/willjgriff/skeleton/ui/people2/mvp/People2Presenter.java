package com.github.willjgriff.skeleton.ui.people2.mvp;

import com.github.willjgriff.skeleton.data.ListCacheRepository;
import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.mvp.listmvp.ListMvpPresenter;

/**
 * Created by Will on 12/11/2016.
 */

public class People2Presenter extends ListMvpPresenter<Person, People2View> {

	@Override
	protected ListCacheRepository<Person> getRepository() {
		return null;
	}
}
