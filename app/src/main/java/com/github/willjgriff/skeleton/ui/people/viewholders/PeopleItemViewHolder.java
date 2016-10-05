package com.github.willjgriff.skeleton.ui.people.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.data.models.Person;

/**
 * Created by Will on 22/09/2016.
 */
public class PeopleItemViewHolder extends RecyclerView.ViewHolder {

	private TextView mPersonEmail;
	private PeopleListener mPeopleListener;
	private ViewHolderSelector mViewHolderSelector;

	public PeopleItemViewHolder(View itemView, PeopleListener peopleListener, ViewHolderSelector viewHolderSelector) {
		super(itemView);
		mPersonEmail = (TextView) itemView.findViewById(R.id.view_people_item_email);
		mPeopleListener = peopleListener;

		LinearLayout itemContainer = (LinearLayout) itemView.findViewById(R.id.view_people_item_container);
		mViewHolderSelector = viewHolderSelector;
		mViewHolderSelector.setSelectableContainer(itemContainer);
	}

	public void bindData(Person person) {
		if (person.getEmail() != null) {
			mPersonEmail.setText(person.getEmail());
		}

		itemView.setOnClickListener(view -> {
			mPeopleListener.openPersonDetails(person);
			mViewHolderSelector.highlightThis(getAdapterPosition());
		});

		mViewHolderSelector.highlightIfSelected(getAdapterPosition());
	}

	public interface PeopleListener {
		void openPersonDetails(Person person);
	}
}
