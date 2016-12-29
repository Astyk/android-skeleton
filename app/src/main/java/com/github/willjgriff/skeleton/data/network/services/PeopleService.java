package com.github.willjgriff.skeleton.data.network.services;

import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.data.models.person2.NetworkPerson2;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Will on 06/09/2016.
 */

public interface PeopleService {

	@GET("./")
	Observable<List<Person>> getPeople(@Query("results") int amount);

	@GET("./")
	Observable<List<NetworkPerson2>> getPeople2(@Query("results") int amount);
}
