package com.github.willjgriff.skeleton.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Will on 06/09/2016.
 */
public class ApiResponse<CONTENTTYPE> {

//	@Expose
//	@SerializedName("info")
//	private Info info;

	@Expose
	@SerializedName("results")
	private CONTENTTYPE content;

	public CONTENTTYPE getContent() {
		return content;
	}

}
