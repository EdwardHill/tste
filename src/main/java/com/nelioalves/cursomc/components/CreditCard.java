package com.nelioalves.cursomc.components;

import org.springframework.stereotype.Component;

@Component
public class CreditCard {

	private String token;
	private Holder holder;
	private Installment installment;
	private Address billing;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Holder getHolder() {
		return holder;
	}

	public void setHolder(Holder holder) {
		this.holder = holder;
	}

	public Installment getInstallment() {
		return installment;
	}

	public void setInstallment(Installment installment) {
		this.installment = installment;
	}

	public Address getBilling() {
		return billing;
	}

	public void setBilling(Address billing) {
		this.billing = billing;
	}

	@Override
	public String toString() {
		return "CreditCard [token=" + token + ", holder=" + holder + ", installments=" + installment + ", billing="
				+ billing + "]";
	}

}
