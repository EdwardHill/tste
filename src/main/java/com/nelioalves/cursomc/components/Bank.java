package com.nelioalves.cursomc.components;

import br.com.uol.pagseguro.api.common.domain.BankName;

public class Bank {
	private BankName name;

	public BankName getName() {
		return name;
	}

	public void setName(BankName name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Bank [name=" + name + "]";
	}

}
