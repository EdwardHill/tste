package com.nelioalves.cursomc.components;

import java.math.BigDecimal;

public class Installment {
	private Integer quantity;
	private String installmentAmount;
	private String totalAmount;
	private Integer maxInstallmentNoInterest;

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

	public Integer getMaxInstallmentNoInterest() {
		return maxInstallmentNoInterest;
	}

	public void setMaxInstallmentNoInterest(Integer maxInstallmentNoInterest) {
		this.maxInstallmentNoInterest = maxInstallmentNoInterest;
	}

	

}
