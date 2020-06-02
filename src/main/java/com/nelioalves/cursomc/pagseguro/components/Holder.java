package com.nelioalves.cursomc.pagseguro.components;

import java.util.Date;
import java.util.List;

public class Holder {
	private String name;
	private Document document;
	private String birthDate;
	private Phone phone;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public Phone getPhone() {
		return phone;
	}

	public void setPhone(Phone phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "Holder [name=" + name + ", documents=" + document + ", birthDate=" + birthDate + ", phone=" + phone
				+ "]";
	}

}
