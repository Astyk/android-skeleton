package com.github.willjgriff.skeleton.data;

import android.support.annotation.NonNull;

import com.github.willjgriff.skeleton.data.models.Questions;
import com.github.willjgriff.skeleton.data.network.services.QuestionsService;
import com.github.willjgriff.skeleton.data.storage.fetchers.AllRealmFetcher;
import com.github.willjgriff.skeleton.data.storage.fetchers.RealmFetcher;
import com.github.willjgriff.skeleton.data.storage.updaters.RealmUpdater;
import com.github.willjgriff.skeleton.data.storage.updaters.ReplaceRealmUpdater;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

/**
 * Created by Will on 13/08/2016.
 * <p>
 * A data manager that constructs cached data requests using the Realm database.
 * Functions will return current data available from the Realm and accept a listener
 * that will be called when new results have been successfully fetched.
 */
public class QuestionsDataManager {

	private QuestionsService mQuestionsService;
	private NetworkFetchAndUpdate<Questions> mSoQuestionsNetworkFetchAndUpdate;
	private RealmFetcher<Questions> mQuestionsRealmFetcher;
	private Subscription mQuestionsUpdateSubscription;
	private PublishSubject<Questions> mQuestionsPublishSubject;

	public QuestionsDataManager(@NonNull QuestionsService questionsService) {
		mQuestionsService = questionsService;
		mQuestionsRealmFetcher = new AllRealmFetcher<>(Questions.class);
		mQuestionsPublishSubject = PublishSubject.create();
	}

	public Questions getStoredQuestions(Realm realm, RealmChangeListener<RealmResults<Questions>> realmChangeListener) {
		RealmResults<Questions> savedSoQuestions = mQuestionsRealmFetcher.fetch(realm);

		savedSoQuestions.addChangeListener(realmChangeListener);

		if (savedSoQuestions.size() >= 1) {
			return savedSoQuestions.get(0);
		} else {
			return null;
		}
	}

	public Observable<Questions> getQuestionsUpdateObservable() {
		return mQuestionsPublishSubject.asObservable();
	}

	public void updateQuestionsFromNetwork(Realm realm) {
		if (mQuestionsUpdateSubscription == null || mQuestionsUpdateSubscription.isUnsubscribed()) {

			RealmUpdater<Questions> realmUpdater = new ReplaceRealmUpdater<>(realm, mQuestionsRealmFetcher);
			mSoQuestionsNetworkFetchAndUpdate = new NetworkFetchAndUpdate<>(realm,
				mQuestionsService.getQuestions("android"), realmUpdater);

			mQuestionsUpdateSubscription = mSoQuestionsNetworkFetchAndUpdate.fetchAndUpdateData().subscribe(new Action1<Questions>() {
				@Override
				public void call(Questions questions) {
					mQuestionsPublishSubject.onNext(questions);
				}
			});
		}
	}

	public void cancelUpdate() {
		if (mQuestionsUpdateSubscription != null && !mQuestionsUpdateSubscription.isUnsubscribed()) {
			mQuestionsUpdateSubscription.unsubscribe();
		}
		mSoQuestionsNetworkFetchAndUpdate.cancelRequests();
	}
}
