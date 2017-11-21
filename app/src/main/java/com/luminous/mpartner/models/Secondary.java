package com.luminous.mpartner.models;

import java.io.Serializable;

public class Secondary implements Serializable {

	private String id, dealerCode, dealerName,dealerAddress, serialNumbers, quantity, date,
			time;

	public String getDealerAddress() {
		return dealerAddress;
	}

	public void setDealerAddress(String dealerAddress) {
		this.dealerAddress = dealerAddress;
	}

	public String getId() {
		return id;
	}

	public Secondary(String id, String dealerCode, String dealerName,String dealerAddress,
			String serialNumbers, String quantity, String date, String time) {
		super();
		this.id = id;
		this.dealerCode = dealerCode;
		this.dealerName = dealerName;
		this.dealerAddress = dealerAddress;
		this.serialNumbers = serialNumbers;
		this.quantity = quantity;
		this.date = date;
		this.time = time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Secondary() {

	}

	public String getSerialNumbers() {
		return serialNumbers;
	}

	public void setSerialNumbers(String serialNumbers) {
		this.serialNumbers = serialNumbers;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

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

}
