package com.github.willjgriff.skeleton.data;

import android.support.annotation.NonNull;

import com.github.willjgriff.skeleton.data.models.Questions;
import com.github.willjgriff.skeleton.data.network.services.QuestionsService;
import com.github.willjgriff.skeleton.data.storage.fetchers.AllRealmFetcher;
import com.github.willjgriff.skeleton.data.storage.fetchers.RealmFetcher;
import com.github.willjgriff.skeleton.data.storage.updaters.RealmUpdater;
import com.github.willjgriff.skeleton.data.storage.updaters.ReplaceRealmUpdater;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by Will on 13/08/2016.
 * <p>
 * A data manager that constructs cached data requests using the Realm database.
 * Functions will return current data available from the Realm and accept a listener
 * that will be called when new results have been successfully fetched.
 */
// TODO: Move stuff into local folders. eg this into Land folder.
public class QuestionsDataManager {

	private QuestionsService mQuestionsService;
	private NetworkFetchAndUpdate<Questions> mSoQuestionsNetworkFetchAndUpdate;
	private RealmFetcher<Questions> mQuestionsRealmFetcher;

	public QuestionsDataManager(@NonNull QuestionsService questionsService) {
		mQuestionsService = questionsService;
		mQuestionsRealmFetcher = new AllRealmFetcher<>(Questions.class);
	}

	public Observable<Questions> updateQuestionsFromNetwork(Realm realm) {
		RealmUpdater<Questions> realmUpdater = new ReplaceRealmUpdater<>(realm, mQuestionsRealmFetcher);
		mSoQuestionsNetworkFetchAndUpdate = new NetworkFetchAndUpdate<>(
			mQuestionsService.getQuestions("android"), realmUpdater);

		return mSoQuestionsNetworkFetchAndUpdate.fetchAndUpdateData();
	}

	public Observable<RealmResults<Questions>> getRealmQuestionsObservable(Realm realm) {
		return realm.where(Questions.class)
			.findAllAsync()
			.asObservable()
			.filter(new Func1<RealmResults<Questions>, Boolean>() {
				@Override
				public Boolean call(RealmResults<Questions> questionses) {
					return questionses.isLoaded();
				}
			})
			.filter(new Func1<RealmResults<Questions>, Boolean>() {
				@Override
				public Boolean call(RealmResults<Questions> questionses) {
					return questionses.isValid();
				}
			})
			.subscribeOn(AndroidSchedulers.mainThread())
			.observeOn(AndroidSchedulers.mainThread());
	}

	public void cancelUpdate() {
		mSoQuestionsNetworkFetchAndUpdate.cancelRequests();
	}
}
