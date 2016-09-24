package com.github.willjgriff.skeleton.ui.land;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.ui.land.viewholders.PeopleItemViewHolder;
import com.github.willjgriff.skeleton.ui.land.viewholders.PeopleItemViewHolder.PeopleListener;
import com.github.willjgriff.skeleton.ui.land.viewholders.PeopleItemViewHolder.SelectedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Will on 19/08/2016.
 */
// TODO: Consider generalising the selected position behaviour?
public class PeopleAdapter extends RecyclerView.Adapter<PeopleItemViewHolder> implements SelectedListener {

	private List<Person> mPeople;
	private PeopleListener mPeopleListener;
	private int mSelectedPosition;

	public PeopleAdapter() {
		mPeople = new ArrayList<>();
	}

	public void setPeople(List<Person> people, PeopleListener peopleListener) {
		mPeople = people;
		mPeopleListener = peopleListener;
		mSelectedPosition = -1;
		notifyItemRangeChanged(0, people.size());
	}

	@Override
	public PeopleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_people_item, parent, false);
		return new PeopleItemViewHolder(itemView, mPeopleListener, this);
	}

	@Override
	public void onBindViewHolder(PeopleItemViewHolder holder, int position) {
		holder.bindData(mPeople.get(position), mSelectedPosition);
	}

	@Override
	public int getItemCount() {
		return mPeople.size();
	}

	@Override
	public void setSelectedItem(int position) {
		int previousSelectedPosition = mSelectedPosition;
		mSelectedPosition = position;
		notifyItemChanged(mSelectedPosition);
		notifyItemChanged(previousSelectedPosition);
	}
}
