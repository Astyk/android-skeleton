package com.github.willjgriff.skeleton.di.questions;

import com.github.willjgriff.skeleton.data.QuestionsDataManager;
import com.github.willjgriff.skeleton.data.network.services.QuestionsService;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import retrofit2.Retrofit;

/**
 * Created by Will on 18/08/2016.
 */
@Module
public class QuestionsModule {

	@Provides
	@QuestionsScope
	QuestionsDataManager providesQuestionsDataManager(QuestionsService questionsService, Realm realm) {
		return new QuestionsDataManager(questionsService, realm);
	}

}
