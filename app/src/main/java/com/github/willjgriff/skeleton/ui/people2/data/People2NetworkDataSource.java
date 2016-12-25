package com.github.willjgriff.skeleton.ui.people2.data;

import com.github.willjgriff.skeleton.data.PeopleQuery;
import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.data.network.ListNetworkDataSource;
import com.github.willjgriff.skeleton.data.network.services.PeopleService;
import com.github.willjgriff.skeleton.data.utils.transformers.BasicScheduleTransformer;

import java.util.List;

import rx.Observable;

/**
 * Created by Will on 23/12/2016.
 */

public class People2NetworkDataSource implements ListNetworkDataSource<Person, PeopleQuery> {

	private PeopleService mPeopleService;

	public People2NetworkDataSource(PeopleService peopleService) {
		mPeopleService = peopleService;
	}

	@Override
	public Observable<List<Person>> getDataFromNetwork(PeopleQuery peopleQuery) {
		return mPeopleService.getPeople(peopleQuery.getNumberOfPeople())
			.compose(new BasicScheduleTransformer<>());
	}
}
