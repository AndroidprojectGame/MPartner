package com.luminous.mpartner.models;

import java.io.Serializable;

public class AccessCode implements Serializable {

	private String SKUModelId, AccessCode, SKUModelName, TAvlQuantity;

	public AccessCode(String sKUModelId, String accessCode,
			String sKUModelName, String tAvlQuantity) {
		super();
		SKUModelId = sKUModelId;
		AccessCode = accessCode;
		SKUModelName = sKUModelName;
		TAvlQuantity = tAvlQuantity;
	}

	

	public String getSKUModelId() {
		return SKUModelId;
	}

	public void setSKUModelId(String sKUModelId) {
		SKUModelId = sKUModelId;
	}

	public String getAccessCode() {
		return AccessCode;
	}

	public void setAccessCode(String accessCode) {
		AccessCode = accessCode;
	}

	public String getSKUModelName() {
		return SKUModelName;
	}

	public void setSKUModelName(String sKUModelName) {
		SKUModelName = sKUModelName;
	}

	public String getTAvlQuantity() {
		return TAvlQuantity;
	}

	public void setTAvlQuantity(String tAvlQuantity) {
		TAvlQuantity = tAvlQuantity;
	}

}
