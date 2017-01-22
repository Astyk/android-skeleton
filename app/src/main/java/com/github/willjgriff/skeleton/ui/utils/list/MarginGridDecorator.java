package com.github.willjgriff.skeleton.ui.utils.list;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.willjgriff.skeleton.ui.utils.UiUtils;
import com.github.willjgriff.skeleton.ui.utils.UiUtilsKt;

/**
 * Created by Will on 28/12/2016.
 */

public class MarginGridDecorator extends RecyclerView.ItemDecoration {

	private int mDpSpacing;

	public MarginGridDecorator(int dpSpacing) {
		mDpSpacing = dpSpacing;
	}

	// Perfect candidate for Unit Tests...
	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		super.getItemOffsets(outRect, view, parent, state);

		int pixelSpacing = (int) UiUtilsKt.convertDpToPixel(mDpSpacing, view.getContext());
		int adapterPosition = parent.getChildAdapterPosition(view);

		// Not happy about this casting.
		int numberOfColumns = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();

		if (numberOfColumns > 1) {
			// Add margin to the top of the top row and the bottom of all items
			if (adapterPosition < numberOfColumns) {
				outRect.top = pixelSpacing;
			}
			outRect.bottom = pixelSpacing;

			// Add margin to the left of the left row and the right of all items
			if (adapterPosition % numberOfColumns == 0) {
				outRect.left = pixelSpacing;
			}
			outRect.right = pixelSpacing;
		} else {
			if (adapterPosition == 0) {
				outRect.top = pixelSpacing;
			}
			outRect.left = outRect.right = outRect.bottom = pixelSpacing;
		}
	}
}
