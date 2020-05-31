package com.nelioalves.cursomc.components;

import java.util.Arrays;

import br.com.uol.pagseguro.api.common.domain.BankName;

public class CheckoutGenerico {
	private String reference;

	private String method;

	private Shipping shipping;

	private String extraAmount;

	private Sender sender;

	private Items[] items;

	private Billing billing;

	private CreditCard creditCard;

	private Bank bank;

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Shipping getShipping() {
		return shipping;
	}

	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}

	public String getExtraAmount() {
		return extraAmount;
	}

	public void setExtraAmount(String extraAmount) {
		this.extraAmount = extraAmount;
	}

	public Sender getSender() {
		return sender;
	}

	public void setSender(Sender sender) {
		this.sender = sender;
	}

	public Items[] getItems() {
		return items;
	}

	public void setItems(Items[] items) {
		this.items = items;
	}

	public Billing getBilling() {
		return billing;
	}

	public void setBilling(Billing billing) {
		this.billing = billing;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	@Override
	public String toString() {
		return "CheckoutGenerico [reference=" + reference + ", method=" + method + ", shipping=" + shipping
				+ ", extraAmount=" + extraAmount + ", sender=" + sender + ", items=" + Arrays.toString(items)
				+ ", billing=" + billing + ", creditCard=" + creditCard + ", bank=" + bank + "]";
	}

}
