package com.github.willjgriff.skeleton.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Will on 06/09/2016.
 */
// TODO: Remove if unused. Useful if there are other parts of the JSON response we want
// eg an error / paging object. Otherwise better to use a custom Deserializer or TypeAdapterFactory
public class ApiResponse<CONTENTTYPE> {

	@Expose
	@SerializedName("results")
	private CONTENTTYPE content;

	public CONTENTTYPE getContent() {
		return content;
	}

}
