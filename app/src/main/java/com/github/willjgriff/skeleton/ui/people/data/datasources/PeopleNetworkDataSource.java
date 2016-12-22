package com.github.willjgriff.skeleton.ui.people.data.datasources;

import android.support.annotation.NonNull;

import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.data.network.services.PeopleService;
import com.github.willjgriff.skeleton.data.utils.response.NetworkResponseTransformer;
import com.github.willjgriff.skeleton.data.utils.response.ResponseHolder;
import com.github.willjgriff.skeleton.data.utils.transformers.BasicScheduleTransformer;

import java.util.List;

import rx.Observable;

/**
 * Created by Will on 04/10/2016.
 */
public class PeopleNetworkDataSource {

	private PeopleService mPeopleService;

	public PeopleNetworkDataSource(@NonNull PeopleService peopleService) {
		mPeopleService = peopleService;
	}

	public Observable<ResponseHolder<List<Person>>> getPeopleFromNetwork(int countPeople) {
		return mPeopleService.getPeople(countPeople)
			.compose(new BasicScheduleTransformer<>())
			.compose(new NetworkResponseTransformer<>());
	}
}
