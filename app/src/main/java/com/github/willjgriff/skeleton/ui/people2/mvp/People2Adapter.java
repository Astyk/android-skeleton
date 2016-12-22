package com.github.willjgriff.skeleton.ui.people2.mvp;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.mvp.listmvp.ListMvpAdapter;
import com.github.willjgriff.skeleton.mvp.listmvp.ListMvpViewHolder;

/**
 * Created by Will on 22/12/2016.
 */

public class People2Adapter extends ListMvpAdapter<Person, People2ViewHolder> {

	@Override
	public People2ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new People2ViewHolder(
			LayoutInflater.from(parent.getContext()).inflate(R.layout.view_people_item, parent, false),
			mListItemListener);
	}
}
