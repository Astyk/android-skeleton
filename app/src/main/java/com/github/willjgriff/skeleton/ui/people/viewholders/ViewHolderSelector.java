package com.github.willjgriff.skeleton.ui.people.viewholders;

import android.view.View;

/**
 * Created by Will on 05/10/2016.
 */
public class ViewHolderSelector {

	private SelectorAdapterPosition mSelectorAdapterPosition;
	private View mItemContainer;

	public ViewHolderSelector(SelectorAdapterPosition selectorAdapterPosition) {
		mSelectorAdapterPosition = selectorAdapterPosition;
	}

	public void setSelectableContainer(View selectableContainer) {
		mItemContainer = selectableContainer;
	}

	public void highlightThis(int adapterPosition) {
		mSelectorAdapterPosition.setHighlightedPosition(adapterPosition);
	}

	public void highlightIfSelected(int adapterPosition) {
		if (adapterPosition == mSelectorAdapterPosition.getHighlightedPosition()) {
			mItemContainer.setSelected(true);
		} else {
			mItemContainer.setSelected(false);
		}
	}
}
