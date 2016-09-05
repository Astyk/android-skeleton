package com.github.willjgriff.skeleton.data.network;

/**
 * Created by Will on 18/08/2016.
 */

public final class ApiRes {

	public static final class Base {

		public static final String STACKOVERFLOW = "https://api.stackexchange.com/";

		public static final String RANDOM = "http://api.randomuser.me/";
	}

	public static final class Questions {

		// TODO: Split this into a query object
		public static final String URI = "2.2/questions?order=desc&sort=creation&site=stackoverflow";

		public static final String QUERY_TAGGED = "tagged";
	}

	public static final class Random {
		public static final String PEOPLE = "";
	}
}
