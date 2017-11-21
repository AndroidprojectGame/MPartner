package com.luminous.mpartner.models;

import java.io.Serializable;

public class Dealer implements Serializable {

	private String dealerCode, dealerName;

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public Dealer(String dealerCode, String dealerName) {
		super();
		this.dealerCode = dealerCode;
		this.dealerName = dealerName;
	}

}
