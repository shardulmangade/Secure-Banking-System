package asu.edu.sbs.domain;

public class MerchantTransaction {

	private String fromaccount ;
	private String toMerchantacccount ;
	private String fromCustomer;
	private String toMerchant;
	private Double amount;
	private String certificate;
	
	public String getCertificate() {
		return certificate;
	}
	public void setCertificate(String certificate) {
		this.certificate = certificate;
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
}
