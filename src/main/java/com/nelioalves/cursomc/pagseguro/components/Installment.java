package com.nelioalves.cursomc.pagseguro.components;

public class Installment {
	private Integer quantity;
	private String installmentAmount;
	private String totalAmount;
	

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getInstallmentAmount() {
		return installmentAmount;
	}

	public void setInstallmentAmount(String installmentAmount) {
		this.installmentAmount = installmentAmount;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	@Override
	public String toString() {
		return "Installment [quantity=" + quantity + ", installmentAmount=" + installmentAmount + ", totalAmount="
				+ totalAmount + "]";
	}

	

	
}
