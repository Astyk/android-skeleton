package com.github.willjgriff.skeleton.ui.form;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.willjgriff.skeleton.R;

import java.util.List;

/**
 * Created by Will on 03/10/2016.
 */

public class BasicAdapter extends RecyclerView.Adapter<BasicAdapter.BindingViewHolder> {

	private List<String> mData;
	private PaginationTest.ListEndApproachingListener mListEndApproachingListener;
	private boolean mIsLoadingVisible = false;
	private boolean mLastPageLoaded = false;

	public BasicAdapter(List<String> data, PaginationTest.ListEndApproachingListener listEndApproachingListener) {
		mData = data;
		mListEndApproachingListener = listEndApproachingListener;
	}

	@Override
	public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

		if (ItemType.LOADING.ordinal() == viewType) {
			return new LoadingViewHolder(layoutInflater.inflate(R.layout.fragment_pagination_item_loading, parent, false));
		} else {
			return new BasicViewHolder(layoutInflater.inflate(R.layout.fragment_pagination_item, parent, false));
		}
	}

	@Override
	public void onBindViewHolder(BindingViewHolder holder, int position) {
		// Get an indexOutOfBounds exception if the loading is shown and we don't do this.
		if (position < mData.size()) {
			holder.bind(mData.get(position));
		}

		// when the remaining number of items to display in the list are less than the threshold, notify.
		if (position > mData.size() - Math.min(5, mData.size())) {
			//approaching the end of the list, maybe we need more mData
			if (mListEndApproachingListener != null && !mLastPageLoaded) {
				mListEndApproachingListener.listEndApproaching();
				showLoadingView();
			} else {
				hideLoadingView();
			}
		}
	}

	@Override
	public int getItemViewType(int position) {
		if (isLoadingFooter(position)) {
			return ItemType.LOADING.ordinal();
		} else {
			return ItemType.ITEM.ordinal();
		}
	}

	private boolean isLoadingFooter(int position) {
		return position == mData.size() && mIsLoadingVisible;
	}

	@Override
	public int getItemCount() {
		return mData.size() + (mIsLoadingVisible ? 1 : 0);
	}

	public void showLoadingView() {
		if (!mIsLoadingVisible && !mLastPageLoaded) {
			mIsLoadingVisible = true;
			refreshData();
		}
	}

	private void refreshData() {
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				notifyItemChanged(mData.size());
			}
		});
	}

	public void addData(List<String> newData) {
		mData.addAll(newData);
		notifyDataSetChanged();
	}

	public void hideLoadingView() {
		if (mIsLoadingVisible) {
			mIsLoadingVisible = false;
			refreshData();
		}
	}

	public void lastPageLoaded() {
		mLastPageLoaded = true;
	}

	private enum ItemType {
		ITEM,
		LOADING;
	}

	public class BasicViewHolder extends BindingViewHolder {

		private TextView mTextView;

		public BasicViewHolder(View itemView) {
			super(itemView);
			mTextView = (TextView) itemView.findViewById(R.id.fragment_pagination_test_text);
		}

		@Override
		public void bind(String text) {
			mTextView.setText(text);
		}
	}

	public class LoadingViewHolder extends BindingViewHolder {

		public LoadingViewHolder(View itemView) {
			super(itemView);
		}

		@Override
		void bind(String text) {

		}
	}

	// TODO: Would add a generic data type if actually used.
	public abstract class BindingViewHolder extends RecyclerView.ViewHolder {

		public BindingViewHolder(View itemView) {
			super(itemView);
		}

		abstract void bind(String text);
	}
}
