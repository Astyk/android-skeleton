package com.github.willjgriff.skeleton.ui.people2.mvp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.data.models.Person;
import com.github.willjgriff.skeleton.mvp.listmvp.ListMvpViewHolder;
import com.github.willjgriff.skeleton.ui.utils.list.ViewHolderSelector;

/**
 * Created by Will on 22/09/2016.
 */
public class People2ViewHolder extends ListMvpViewHolder<Person> {

	private TextView mPersonEmail;

	public People2ViewHolder(View itemView, ListItemListener<Person> peopleListener) {
		super(itemView, peopleListener);
		mPersonEmail = (TextView) itemView.findViewById(R.id.view_people_item_email);
	}

	public void bindData(Person person) {
		if (person.getEmail() != null) {
			mPersonEmail.setText(person.getEmail());
		}
	}
}
