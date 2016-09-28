package com.github.willjgriff.skeleton.ui.settings;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.ui.navigation.NavigationToolbarListener;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Will on 17/08/2016.
 */

public class SettingsFragment extends Fragment {

	NavigationToolbarListener mToolbarListener;
	Person mPerson;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		mToolbarListener = (NavigationToolbarListener) context;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_settings, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mToolbarListener.setToolbarTitle(R.string.fragment_settings_title);

		Realm realm = Realm.getDefaultInstance();
		Observable<RealmResults<Person>> firstPerson = realm.where(Person.class).findAllAsync().asObservable().first();
		Subscription subscription = firstPerson.subscribe(new Action1<RealmResults<Person>>() {
			@Override
			public void call(RealmResults<Person> persons) {
				Log.d("RXREALM", "Subscribed to Realm Where");
				mPerson = persons.get(0);
			}
		});
		subscription.unsubscribe();
		realm.close();
	}
}
