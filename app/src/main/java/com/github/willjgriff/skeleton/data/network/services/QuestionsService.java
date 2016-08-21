package com.github.willjgriff.skeleton.data.network.services;

import com.github.willjgriff.skeleton.data.models.Questions;
import com.github.willjgriff.skeleton.data.network.ApiRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Will on 18/08/2016.
 */
public interface QuestionsService {

	@GET(ApiRes.Questions.URI)
	Observable<Questions> loadQuestions(@Query(ApiRes.Questions.QUERY_TAGGED) String tags);
}
