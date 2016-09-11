package com.github.willjgriff.skeleton.ui.land;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.data.models.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Will on 19/08/2016.
 */

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleItemViewHolder> {

	List<Person> mPeople;

	public PeopleAdapter() {
		mPeople = new ArrayList<>();
	}

	public void setPeople(List<Person> people) {
		mPeople = people;
		notifyItemRangeChanged(0, people.size());
	}

	@Override
	public PeopleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater
			.from(parent.getContext())
			.inflate(R.layout.view_people_item, parent, false);
		return new PeopleItemViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(PeopleItemViewHolder holder, int position) {
		holder.bindData(mPeople.get(position));
	}

	@Override
	public int getItemCount() {
		return mPeople.size();
	}

	public class PeopleItemViewHolder extends RecyclerView.ViewHolder {

		TextView mTextView;

		public PeopleItemViewHolder(View itemView) {
			super(itemView);
			mTextView = (TextView) itemView.findViewById(R.id.view_people_item_email);
		}

		public void bindData(Person person) {
			if (person.getEmail() != null) {
				mTextView.setText(person.getEmail());
			}
		}
	}
}
