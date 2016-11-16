package com.github.willjgriff.skeleton.data.models.cleanperson;

import com.google.gson.annotations.SerializedName;

import rx.functions.Func1;

/**
 * Created by Will on 15/11/2016.
 */

public class NetworkPerson {

	@SerializedName("email")
	private String email;

	public String getEmail() {
		return email;
	}

	public class ConvertToDomain implements Func1<NetworkPerson, DomainPerson> {
		@Override
		public DomainPerson call(NetworkPerson networkPerson) {
			DomainPerson domainPerson = new DomainPerson();
			domainPerson.setEmail(networkPerson.getEmail());
			return domainPerson;
		}
	}
}
