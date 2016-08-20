package com.github.willjgriff.skeleton.ui.land;

import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.willjgriff.skeleton.R;
import com.github.willjgriff.skeleton.data.models.Question;
import com.github.willjgriff.skeleton.data.models.Questions;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Will on 19/08/2016.
 */

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionsItemViewHolder> {

	List<Question> mQuestions;

	public void setQuestions(List<Question> questions) {
		mQuestions.clear();
		mQuestions.addAll(questions);
		notifyItemRangeChanged(0, questions.size());
	}

	public QuestionsAdapter() {
		mQuestions = new ArrayList<>();
	}

	@Override
	public QuestionsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater
			.from(parent.getContext())
			.inflate(R.layout.view_questions_item, parent, false);
		return new QuestionsItemViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(QuestionsItemViewHolder holder, int position) {
		holder.bindData(mQuestions.get(position));
	}

	@Override
	public int getItemCount() {
		return mQuestions.size();
	}

	public class QuestionsItemViewHolder extends RecyclerView.ViewHolder {

		TextView mTextView;

		public QuestionsItemViewHolder(View itemView) {
			super(itemView);
			mTextView = (TextView) itemView.findViewById(R.id.view_questions_item_title);
		}

		public void bindData(Question question) {
			mTextView.setText(question.getTitle());
		}
	}
}
