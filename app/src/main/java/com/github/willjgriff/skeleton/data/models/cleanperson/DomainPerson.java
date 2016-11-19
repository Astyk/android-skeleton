package com.github.willjgriff.skeleton.data.models.cleanperson;

/**
 * Created by Will on 13/11/2016.
 *
 * This, along with the other classes in this package, are a potential representation of a clean
 * structure for a domain model. This helps when the Network, Storage or other models differ.
 */
public class DomainPerson {

	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
