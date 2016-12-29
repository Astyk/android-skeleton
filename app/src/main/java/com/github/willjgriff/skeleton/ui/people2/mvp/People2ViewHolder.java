package com.github.willjgriff.skeleton.ui.people2.mvp;

import android.view.View;
import android.widget.TextView;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.data.models.person2.Person2;
import com.github.willjgriff.skeleton.mvp.listmvp.ListMvpViewHolder;

/**
 * Created by Will on 22/09/2016.
 */
public class People2ViewHolder extends ListMvpViewHolder<Person2> {

	private TextView mPersonEmail;

	public People2ViewHolder(View itemView, ListItemListener<Person2> peopleListener) {
		super(itemView, peopleListener);
		mPersonEmail = (TextView) itemView.findViewById(R.id.view_generic_list_item_text);
	}

	public void bindData(Person2 person) {
		if (person.getEmail() != null) {
			mPersonEmail.setText(person.getEmail());
		}
	}
}
