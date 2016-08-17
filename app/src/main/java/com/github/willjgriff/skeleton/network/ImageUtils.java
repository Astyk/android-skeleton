package com.github.willjgriff.skeleton.network;

import android.support.annotation.DrawableRes;
import android.support.v7.app.NotificationCompat;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

/**
 * Created by Will on 17/08/2016.
 */

public class ImageUtils {

	public static Builder showImage(String imageUri, ImageView imageView) {
		return new Builder(imageUri, imageView);
	}

	public static class Builder {

		private String mImageUri;
		private ImageView mImageView;
		@DrawableRes private int mPlaceholder;
		@DrawableRes private int mErrorImage;

		public Builder(String imageUri, ImageView imageView) {
			mImageUri = imageUri;
			mImageView = imageView;
		}

		public Builder withPlaceholder(@DrawableRes int placeholder) {
			mPlaceholder = placeholder;
			return this;
		}

		public Builder withErrorImage(@DrawableRes int errorImage) {
			mErrorImage = errorImage;
			return this;
		}

		public void now() {
			RequestCreator picassoLoader = Picasso.with(mImageView.getContext()).load(mImageUri);

			if (mPlaceholder != 0) {
				picassoLoader.placeholder(mPlaceholder);
			}

			if (mErrorImage != 0) {
				picassoLoader.error(mErrorImage);
			}

			picassoLoader.into(mImageView);
		}
	}
}
