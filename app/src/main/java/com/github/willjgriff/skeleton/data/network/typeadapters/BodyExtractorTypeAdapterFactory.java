package com.github.willjgriff.skeleton.data.network.typeadapters;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by Will on 19/11/2016.
 *
 * We could also use a custom deserializer, although that's less efficient than a TypeAdapter.
 * See here for why: http://stackoverflow.com/questions/30631004/gson-type-adapter-vs-custom-deseralizer
 *
 * This implementation was copied from here:
 * http://stackoverflow.com/questions/23070298/get-nested-json-object-with-gson-using-retrofit
 */
public class BodyExtractorTypeAdapterFactory implements TypeAdapterFactory {

	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

		final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
		final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);

		return new TypeAdapter<T>() {

			@Override
			public void write(JsonWriter out, T value) throws IOException {
				delegate.write(out, value);
			}

			@Override
			public T read(JsonReader in) throws IOException {
				JsonElement jsonElement = elementAdapter.read(in);
				if (jsonElement.isJsonObject()) {
					JsonObject jsonObject = jsonElement.getAsJsonObject();
					if (jsonObject.has("results")) {
						jsonElement = jsonObject.get("results");
					}
				}

				return delegate.fromJsonTree(jsonElement);
			}
		}.nullSafe();
	}
}
