package asu.edu.sbs.domain;

public class Credit {

	private String fromaccount ;
	private String toacccount ;
	private String fromCustomer;
	private String toCustomer;
	private Double amount;
	private byte[] signedRequest;
	private String publicKey;

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
	public String getToacccount() {
		return toacccount;
	}
	public void setToacccount(String toacccount) {
		this.toacccount = toacccount;
	}
	public String getFromCustomer() {
		return fromCustomer;
	}
	public void setFromCustomer(String fromCustomer) {
		this.fromCustomer = fromCustomer;
	}
	public String getToCustomer() {
		return toCustomer;
	}
	public void setToCustomer(String toCustomer) {
		this.toCustomer = toCustomer;
	}
	
	public String getStringForEncryption(){
		String result = this.fromCustomer;
		return result;
	}
	
	public byte[] getSignedRequest() {
		return signedRequest;
	}
	
	public void setSignedRequest(byte[] signedRequest) {
		this.signedRequest = signedRequest;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	
}
