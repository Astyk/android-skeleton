package com.github.willjgriff.skeleton.di.app;

import com.github.willjgriff.skeleton.data.network.ApiRes;
import com.github.willjgriff.skeleton.data.network.services.PeopleService;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Will on 18/08/2016.
 */
@Module
public class NetworkModule {

	@Provides
	@Singleton
	GsonConverterFactory providesGsonConverterFactory() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.excludeFieldsWithoutExposeAnnotation();
		return GsonConverterFactory.create(gsonBuilder.create());
	}

	@Provides
	@Singleton
	Retrofit providesRetrofit(GsonConverterFactory gsonConverterFactory) {
		return new Retrofit.Builder()
			.baseUrl(ApiRes.Base.RANDOM)
			.addConverterFactory(gsonConverterFactory)
			.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
			.build();
	}

	@Provides
	@Singleton
	PeopleService providesRandomPeopleService(Retrofit retrofit) {
		return retrofit.create(PeopleService.class);
	}

}
