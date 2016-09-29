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
import com.github.willjgriff.skeleton.data.responsewrapper.ResponseHolder;
import com.github.willjgriff.skeleton.ui.navigation.NavigationToolbarListener;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

import static com.github.willjgriff.skeleton.data.responsewrapper.ResponseHolder.Source.STORAGE;

/**
 * Created by Will on 17/08/2016.
 */

public class SettingsFragment extends Fragment {

	NavigationToolbarListener mToolbarListener;
	Subscription mSubscription;
	Realm mRealm;

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

		mRealm = Realm.getDefaultInstance();

		RealmResults<Person> people = mRealm.where(Person.class).findAll();
		Observable<RealmResults<Person>> firstPerson = people.asObservable();
//		Observable<ResponseHolder<List<Person>>> firstPerson = realm.where(Person.class).findAllAsync().asObservable()
//			.first()
//			.map(new Func1<RealmResults<Person>, ResponseHolder<List<Person>>>() {
//				@Override
//				public ResponseHolder<List<Person>> call(RealmResults<Person> persons) {
//					ResponseHolder<List<Person>> responseHolder = new ResponseHolder<>(STORAGE);
//					responseHolder.setData(persons);
//					return responseHolder;
//				}
//			})
//			.onErrorReturn(new Func1<Throwable, ResponseHolder<List<Person>>>() {
//				@Override
//				public ResponseHolder<List<Person>> call(Throwable throwable) {
//					ResponseHolder<List<Person>> responseHolder = new ResponseHolder<>(STORAGE);
//					responseHolder.setError(throwable);
//					return responseHolder;
//				}
//			});


		mSubscription = firstPerson.subscribe(new Subscriber<RealmResults<Person>>() {
			@Override
			public void onCompleted() {

			}

			@Override
			public void onError(Throwable e) {
				Log.d("RXREALM", "Error");
			}

			@Override
			public void onNext(RealmResults<Person> persons) {
				Log.d("RXREALM", "Subscribed to Realm, Person: ");
			}
		});
//
//		mSubscription = firstPerson.subscribe(new Action1<ResponseHolder<List<Person>>>() {
//			@Override
//			public void call(ResponseHolder<List<Person>> listResponseHolder) {
//				Log.d("RXREALM", "Subscribed to Realm, Person: ");
//			}
//		});


	}

	@Override
	public void onPause() {
		mSubscription.unsubscribe();
		mRealm.close();
		super.onPause();
	}
}
