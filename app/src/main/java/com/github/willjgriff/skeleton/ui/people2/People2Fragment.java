package com.github.willjgriff.skeleton.ui.people2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.data.ListCacheRepository;
import com.github.willjgriff.skeleton.data.PeopleQuery;
import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.di.app.AppInjector;
import com.github.willjgriff.skeleton.mvp.BaseMvpFragment;
import com.github.willjgriff.skeleton.mvp.listmvp.ListMvpAdapter;
import com.github.willjgriff.skeleton.mvp.listmvp.ListMvpFragment;
import com.github.willjgriff.skeleton.mvp.listmvp.ListMvpViewHolder;
import com.github.willjgriff.skeleton.mvp.listmvp.ListMvpViewHolder.ListItemListener;
import com.github.willjgriff.skeleton.ui.people2.di.DaggerPeople2Component;
import com.github.willjgriff.skeleton.ui.people2.di.People2Component;
import com.github.willjgriff.skeleton.ui.people2.di.People2Module;
import com.github.willjgriff.skeleton.ui.people2.mvp.People2Adapter;
import com.github.willjgriff.skeleton.ui.people2.mvp.People2Presenter;
import com.github.willjgriff.skeleton.ui.people2.mvp.People2View;
import com.github.willjgriff.skeleton.ui.navigation.NavigationToolbarListener;
import com.github.willjgriff.skeleton.ui.people2.mvp.People2ViewHolder;

import javax.inject.Inject;

/**
 * Created by Will on 28/09/2016.
 */

public class People2Fragment extends ListMvpFragment<Person, People2View, People2Presenter, People2ViewHolder, PeopleQuery>
	implements People2View {

	@Inject
	People2Presenter mPeople2Presenter;
	private NavigationToolbarListener mNavigationToolbarListener;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		mNavigationToolbarListener = (NavigationToolbarListener) context;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DaggerPeople2Component.builder()
			.appComponent(AppInjector.INSTANCE.getComponent())
			.people2Module(new People2Module())
			.build()
			.inject(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		mNavigationToolbarListener.setToolbarTitle(R.string.fragment_people_2_title);
	}

	@Override
	protected ListMvpAdapter<Person, People2ViewHolder> createAdapter() {
		return new People2Adapter();
	}

	@Override
	protected People2View getMvpView() {
		return this;
	}

	@Override
	protected People2Presenter createPresenter() {
		return mPeople2Presenter;
	}

	@Override
	public void onItemClick(Person data) {

	}
}
