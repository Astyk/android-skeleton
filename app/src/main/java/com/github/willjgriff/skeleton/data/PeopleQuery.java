package com.github.willjgriff.skeleton.data;

import android.provider.Contacts;

/**
 * Created by Will on 23/12/2016.
 */

public class PeopleQuery {

	private int mNumberOfPeople;

	public int getNumberOfPeople() {
		return mNumberOfPeople;
	}

	private void setNumberOfPeople(int numberOfPeople) {
		mNumberOfPeople = numberOfPeople;
	}

	public static class Builder {
		private PeopleQuery mPeopleQuery;

		public Builder() {
			mPeopleQuery = new PeopleQuery();
		}

		public Builder withNumberOfPeople(int numberOfPeople) {
			mPeopleQuery.setNumberOfPeople(numberOfPeople);
			return this;
		}

		public PeopleQuery build() {
			return mPeopleQuery;
		}
	}
}
