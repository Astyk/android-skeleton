package com.github.willjgriff.skeleton.data.models.person2;

import com.github.willjgriff.skeleton.data.models.converters.ConvertListFromTo;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import rx.functions.Func1;

/**
 * Created by Will on 15/11/2016.
 *
 * Adding the static typeAdapter() function call makes AutoValue create a TypeAdapter for this class.
 * We can register this TypeAdapter with Gson so it doesn't have to use as much reflection when
 * de/serializing this class from/to JSON.
 */
@AutoValue
public abstract class NetworkPerson2 {

	public static TypeAdapter<NetworkPerson2> typeAdapter(Gson gson) {
		return new AutoValue_NetworkPerson2.GsonTypeAdapter(gson);
	}

	@SerializedName("email")
	abstract String getEmail();

	public static class ConvertToDomain implements Func1<NetworkPerson2, Person2> {
		@Override
		public Person2 call(NetworkPerson2 networkPerson2) {
			return Person2.builder()
				.setEmail(networkPerson2.getEmail())
				.build();
		}
	}

	public static ConvertListFromTo<NetworkPerson2, Person2> getListConverterFunc() {
		return new ConvertListFromTo<>(new ConvertToDomain());
	}

}
