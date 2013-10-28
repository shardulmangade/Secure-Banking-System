package asu.edu.sbs.domain;

import java.sql.Blob;

public class MerchantTransaction {

	private String fromaccount ;
	private String toMerchantacccount ;
	private String fromCustomer;
	private String toMerchant;
	private String transactionId;
	private Double amount;
	private Blob certificate;
	
	public Blob getCertificate() {
		return certificate;
	}
	public void setCertificate(Blob blob) {
		this.certificate = blob;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getFromaccount() {
		return fromaccount;
	}
	public void setFromaccount(String fromaccount) {
		this.fromaccount = fromaccount;
	}
	public String getToMerchantacccount() {
		return toMerchantacccount;
	}
	public void setToMerchantacccount(String tomerchant) {
		this.toMerchantacccount = tomerchant;
	}
	public String getFromCustomer() {
		return fromCustomer;
	}
	public void setFromCustomer(String fromCustomer) {
		this.fromCustomer = fromCustomer;
	}
	public String getToMerchant() {
		return toMerchant;
	}
	public void setToMerchant(String toCustomer) {
		this.toMerchant = toCustomer;
	}
	public void setTransactionId(String transactionId) {
		// TODO Auto-generated method stub
		this.transactionId=transactionId;
	}

}
