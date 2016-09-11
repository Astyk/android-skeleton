package com.github.willjgriff.skeleton.data.storage.updaters.methods;

import com.github.willjgriff.skeleton.data.models.helpers.Timestamp;
import com.github.willjgriff.skeleton.data.storage.fetchers.RealmFetcher;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Created by Will on 11/09/2016.
 */

public class ReplaceListRealmUpdateMethod<UPDATETYPE extends RealmModel & Timestamp> implements RealmUpdateMethod<List<UPDATETYPE>> {

	// Can we move this to an Abstract RealmUpdateMethod?
	private RealmFetcher<UPDATETYPE> mRealmFetcher;

	public ReplaceListRealmUpdateMethod(RealmFetcher<UPDATETYPE> realmFetcher) {
		mRealmFetcher = realmFetcher;
	}

	@Override
	public void updateRealm(Realm realm, List<UPDATETYPE> updatedData) {
			// Note: This process will notify listeners of this query with OLD AND NEW data in the
			// same list and then remove the old data. The RealmFetcher currently only takes the first
			// emitted value, the one before any updating occurs, so this isn't a problem for now.
			// Timestamping the Data is necessary because if we remove old data before new data is added an
			// IllegalStateException can occur because the current LiveRealmObject is empty.
			RealmResults<UPDATETYPE> previousData = mRealmFetcher.fetchCurrentData();

			List<UPDATETYPE> timestampedData = timestampNewData(updatedData);
			realm.copyToRealmOrUpdate(timestampedData);

			previousData.deleteAllFromRealm();
	}

	private List<UPDATETYPE> timestampNewData(List<UPDATETYPE> updatedData) {
		for (UPDATETYPE item : updatedData) {
			item.setTimestamp(System.currentTimeMillis());
		}
		return updatedData;
	}
}
