package com.github.willjgriff.skeleton.ui.land.viewholders;

import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.ui.land.PeopleAdapter;

/**
 * Created by Will on 22/09/2016.
 */
//TODO: Consider generalising the selected position and click listener behaviour
public class PeopleItemViewHolder extends RecyclerView.ViewHolder {

	private LinearLayout mLinearLayout;
	private TextView mTextView;
	private PeopleListener mPeopleListener;
	private SelectedListener mSelectedListener;

	public PeopleItemViewHolder(View itemView, PeopleListener peopleListener, SelectedListener selectedListener) {
		super(itemView);
		mLinearLayout = (LinearLayout) itemView.findViewById(R.id.view_people_item_container);
		mTextView = (TextView) itemView.findViewById(R.id.view_people_item_email);
		mPeopleListener = peopleListener;
		mSelectedListener = selectedListener;
	}

	public void bindData(Person person, int selectedPosition) {
		if (person.getEmail() != null) {
			mTextView.setText(person.getEmail());
		}

		itemView.setOnClickListener(view -> {
			mPeopleListener.openPersonDetails(person);
			mSelectedListener.setSelectedItem(getAdapterPosition());
		});

		if (getAdapterPosition() == selectedPosition) {
			mLinearLayout.setSelected(true);
		} else {
			mLinearLayout.setSelected(false);
		}
	}

	public interface PeopleListener {
		void openPersonDetails(Person person);
	}

	public interface SelectedListener {
		void setSelectedItem(int position);
	}
}
