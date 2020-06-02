package com.nelioalves.cursomc.pagseguro.components;

import org.springframework.stereotype.Component;

@Component
public class CreditCard {

	private String token;
	private Holder holder;
	private Installment installment;
	private Integer maxInstallmentNoInterest;

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

	public Integer getMaxInstallmentNoInterest() {
		return maxInstallmentNoInterest;
	}

	public void setMaxInstallmentNoInterest(Integer maxInstallmentNoInterest) {
		this.maxInstallmentNoInterest = maxInstallmentNoInterest;
	}

	public Address getBilling() {
		return billing;
	}

	public void setBilling(Address billing) {
		this.billing = billing;
	}

	@Override
	public String toString() {
		return "CreditCard [token=" + token + ", holder=" + holder + ", installment=" + installment
				+ ", maxInstallmentNoInterest=" + maxInstallmentNoInterest + ", billing=" + billing + "]";
	}

	

}
