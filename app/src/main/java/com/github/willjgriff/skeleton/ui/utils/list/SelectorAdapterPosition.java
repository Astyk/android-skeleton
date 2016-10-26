package com.github.willjgriff.skeleton.ui.utils.list;

/**
 * Created by Will on 05/10/2016.
 */
public class SelectorAdapterPosition {

	private SelectorAdapterPosition.HighlightedListener mHighlightListener;
	private int mHighlightedPosition = -1;

	public SelectorAdapterPosition(HighlightedListener highlightListener) {
		mHighlightListener = highlightListener;
	}

	public int getHighlightedPosition() {
		return mHighlightedPosition;
	}

	public void setHighlightedPosition(int position) {
		mHighlightListener.notifySelectedItemChanged(position, mHighlightedPosition);
		mHighlightedPosition = position;
	}

	public void resetHighlightedPosition() {
		mHighlightedPosition = -1;
	}

	public interface HighlightedListener {
		// Notify the adapter these items have changed.
		void notifySelectedItemChanged(int newPosition, int oldPosition);
	}
}
