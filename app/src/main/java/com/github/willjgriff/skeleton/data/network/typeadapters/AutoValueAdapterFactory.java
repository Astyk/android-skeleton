package com.github.willjgriff.skeleton.data.network.typeadapters;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

/**
 * Created by Will on 29/12/2016.
 */
@GsonTypeAdapterFactory
public abstract class AutoValueAdapterFactory implements TypeAdapterFactory {

	public static TypeAdapterFactory create() {
		return new AutoValueGson_AutoValueAdapterFactory();
	}
}
