package com.github.willjgriff.skeleton.data.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Will on 18/08/2016.
 */

public class Questions extends RealmObject {

	@PrimaryKey
	private int id;

	@SerializedName("items")
	private RealmList<Question> stackOverflowQuestions = new RealmList<>();

	public RealmList<Question> getStackOverflowQuestions() {
		return stackOverflowQuestions;
	}

	public void setStackOverflowQuestions(RealmList<Question> soQuestions) {
		this.stackOverflowQuestions = soQuestions;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
