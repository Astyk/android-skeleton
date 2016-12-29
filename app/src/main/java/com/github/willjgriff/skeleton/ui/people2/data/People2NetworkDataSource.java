package com.github.willjgriff.skeleton.ui.people2.data;

import com.github.willjgriff.skeleton.data.PeopleQuery;
import com.github.willjgriff.skeleton.data.models.person2.NetworkPerson2;
import com.github.willjgriff.skeleton.data.models.person2.Person2;
import com.github.willjgriff.skeleton.data.network.ListNetworkDataSource;
import com.github.willjgriff.skeleton.data.network.services.PeopleService;
import com.github.willjgriff.skeleton.data.utils.transformers.BasicScheduleTransformer;

import java.util.List;

import rx.Observable;

/**
 * Created by Will on 23/12/2016.
 */

public class People2NetworkDataSource implements ListNetworkDataSource<Person2, PeopleQuery> {

	private PeopleService mPeopleService;

	public People2NetworkDataSource(PeopleService peopleService) {
		mPeopleService = peopleService;
	}

	@Override
	public Observable<List<Person2>> getDataFromNetwork(PeopleQuery peopleQuery) {
		return mPeopleService.getPeople2(peopleQuery.getNumberOfPeople())
			.compose(new BasicScheduleTransformer<>())
			.map(NetworkPerson2.getListConverterFunc());
	}
}
