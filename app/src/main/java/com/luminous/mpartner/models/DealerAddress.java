package com.luminous.mpartner.models;


public class DealerAddress {

	private String dealerAddress, dealerName;

	public String getDealerAddress() {
		return dealerAddress;
	}

	public void setDealerAddress(String dealerAddress) {
		this.dealerAddress = dealerAddress;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public DealerAddress(String dealerAddress, String dealerName) {
		super();
		this.dealerAddress = dealerAddress;
		this.dealerName = dealerName;
	}

}
