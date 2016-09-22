package com.github.willjgriff.skeleton.ui.land.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.data.models.Person;

/**
 * Created by Will on 22/09/2016.
 */
public class PeopleItemViewHolder extends RecyclerView.ViewHolder {

	private TextView mTextView;
	private PeopleListener mPeopleListener;

	public PeopleItemViewHolder(View itemView, PeopleListener peopleListener) {
		super(itemView);
		mTextView = (TextView) itemView.findViewById(R.id.view_people_item_email);
		mPeopleListener = peopleListener;
	}

	public void bindData(Person person) {
		if (person.getEmail() != null) {
			mTextView.setText(person.getEmail());
		}
		itemView.setOnClickListener(view -> mPeopleListener.openPersonDetails(person));
	}

	public interface PeopleListener {
		void openPersonDetails(Person person);
	}
}
