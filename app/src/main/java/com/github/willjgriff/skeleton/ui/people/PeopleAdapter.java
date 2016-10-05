package com.github.willjgriff.skeleton.ui.people;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.ui.people.viewholders.PeopleItemViewHolder;
import com.github.willjgriff.skeleton.ui.people.viewholders.PeopleItemViewHolder.PeopleListener;
import com.github.willjgriff.skeleton.ui.people.viewholders.SelectorAdapterPosition;
import com.github.willjgriff.skeleton.ui.people.viewholders.SelectorAdapterPosition.HighlightedListener;
import com.github.willjgriff.skeleton.ui.people.viewholders.ViewHolderSelector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Will on 19/08/2016.
 */
public class PeopleAdapter extends RecyclerView.Adapter<PeopleItemViewHolder> implements HighlightedListener {

	private List<Person> mPeople;
	private PeopleListener mPeopleListener;
	private SelectorAdapterPosition mSelectorAdapterPosition;

	public PeopleAdapter() {
		mPeople = new ArrayList<>();
		mSelectorAdapterPosition = new SelectorAdapterPosition(this);
	}

	public void setPeople(List<Person> people, PeopleListener peopleListener) {
		mPeople = people;
		mPeopleListener = peopleListener;
		mSelectorAdapterPosition.resetHighlightedPosition();
		notifyDataSetChanged();
	}

	@Override
	public PeopleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_people_item, parent, false);
		return new PeopleItemViewHolder(itemView, mPeopleListener, new ViewHolderSelector(mSelectorAdapterPosition));
	}

	@Override
	public void onBindViewHolder(PeopleItemViewHolder holder, int position) {
		holder.bindData(mPeople.get(position));
	}

	@Override
	public int getItemCount() {
		return mPeople.size();
	}

	@Override
	public void notifySelectedItemChanged(int newPosition, int oldPosition) {
		notifyItemChanged(newPosition);
		notifyItemChanged(oldPosition);
	}


}
