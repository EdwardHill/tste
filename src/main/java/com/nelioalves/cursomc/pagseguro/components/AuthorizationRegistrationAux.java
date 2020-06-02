package com.nelioalves.cursomc.pagseguro.components;

import java.util.List;

import br.com.uol.pagseguro.api.common.domain.AccountRegisterSuggestion;
import br.com.uol.pagseguro.api.common.domain.PermissionCode.Code;

public class AuthorizationRegistrationAux {
	private String reference;
	private List<Code> permissions;
	private String redirectURL;
	private String notificationURL;
	private AccountRegisterSuggestion account;
	

	
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public List<Code> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<Code> permissions) {
		this.permissions = permissions;
	}
	public String getRedirectURL() {
		return redirectURL;
	}
	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}
	
	public String getNotificationURL() {
		return notificationURL;
	}
	public void setNotificationURL(String notificationURL) {
		this.notificationURL = notificationURL;
	}
	public AccountRegisterSuggestion getAccount() {
		return account;
	}
	public void setAccount(AccountRegisterSuggestion account) {
		this.account = account;
	}
	@Override
	public String toString() {
		return "AuthorizationRegistration [reference=" + reference + ", permissions=" + permissions + ", redirectURL="
				+ redirectURL + ", getNotificationURL=" + notificationURL + ", account=" + account + "]";
	}
	

}
