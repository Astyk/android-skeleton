package com.github.willjgriff.skeleton.data;

import com.github.willjgriff.skeleton.data.NetworkCallerAndUpdater.NewDataListener;
import com.github.willjgriff.skeleton.data.models.Questions;
import com.github.willjgriff.skeleton.data.network.services.QuestionsService;
import com.github.willjgriff.skeleton.data.storage.fetchers.AllRealmFetcher;
import com.github.willjgriff.skeleton.data.storage.fetchers.RealmFetcher;
import com.github.willjgriff.skeleton.data.storage.updaters.RealmUpdater;
import com.github.willjgriff.skeleton.data.storage.updaters.ReplaceRealmUpdater;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Will on 13/08/2016.
 * <p>
 * A data manager that constructs cached data requests using the Realm database.
 * Functions will return current data available from the Realm and accept a listener
 * that will be called when new results have been successfully fetched.
 */
public class QuestionsDataManager {

	private Realm mRealm;
	private QuestionsService mQuestionsService;
	private NetworkCallerAndUpdater<Questions> mSoQuestionsNetworkCallerAndUpdater;

	public QuestionsDataManager(QuestionsService questionsService, Realm realm) {
		mQuestionsService = questionsService;
		mRealm = realm;
	}

	/**
	 * Returns the currently stored data, if it is present, null otherwise, and sets
	 * a listener that is called when new data from the network is retrieved.
	 *
	 * @param soQuestionsListener called when new data is retrieved
	 * @return currently saved data if present, null otherwise
	 */
	public Questions getStackOverflowQuestions(NewDataListener<Questions> soQuestionsListener) {
		RealmFetcher<Questions> realmFetcher = new AllRealmFetcher<>(Questions.class);
		RealmUpdater<Questions> realmUpdater = new ReplaceRealmUpdater<>(mRealm, realmFetcher);

		mSoQuestionsNetworkCallerAndUpdater = new NetworkCallerAndUpdater<>(
			mQuestionsService.loadQuestions("android"), soQuestionsListener, realmUpdater);
		mSoQuestionsNetworkCallerAndUpdater.fetchAndUpdateData();

		// TODO: We should use the same realm fetch method when updating the realm as when we display realm
		// data to the user. Try to find a way of forcing the use of the same realmFetcher for both purposes
		RealmResults<Questions> savedSoQuestions = realmFetcher.fetch(mRealm);
		if (savedSoQuestions.size() >= 1) {
			return savedSoQuestions.get(0);
		} else {
			return null;
		}
	}

	public void close() {
		if (mSoQuestionsNetworkCallerAndUpdater != null) {
			mSoQuestionsNetworkCallerAndUpdater.close();
			mSoQuestionsNetworkCallerAndUpdater = null;
		}
		mRealm.close();
	}
}
