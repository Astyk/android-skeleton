package com.github.willjgriff.skeleton.data.models.person2;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Will on 13/11/2016.
 *
 * This, along with the other classes in this package, are a potential representation of a clean
 * structure for a domain model. This helps when the Network, Storage or other models differ. Ofcourse
 * this takes longer to update or create. I would generally stick to a Domain and Storage model,
 * merging the Network model into the Domain model.
 */
@AutoValue
public abstract class Person2 implements Parcelable {

	public @SerializedName("email")
	abstract String getEmail();

	public static Builder builder() {
		return new AutoValue_Person2.Builder();
	}

	@AutoValue.Builder
	public abstract static class Builder {
		public abstract Builder setEmail(String email);
		public abstract Person2 build();
	}

	public static TypeAdapter<Person2> typeAdapter(Gson gson) {
		return new AutoValue_Person2.GsonTypeAdapter(gson);
	}
}
