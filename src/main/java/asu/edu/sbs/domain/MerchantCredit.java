package asu.edu.sbs.domain;

public class MerchantCredit {

	private String transactionID;
	private String fromaccount ;
	private String tomerchantaccount ;
	private String fromusername;
	private String tomerchantname;
	private Double amount;
	private String signedRequest;
	private byte[] publicKey;
	
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	public String getFromaccount() {
		return fromaccount;
	}
	public void setFromaccount(String fromaccount) {
		this.fromaccount = fromaccount;
	}
	public String getTomerchantaccount() {
		return tomerchantaccount;
	}
	public void setTomerchantaccount(String tomerchantaccount) {
		this.tomerchantaccount = tomerchantaccount;
	}
	public String getFromusername() {
		return fromusername;
	}
	public void setFromusername(String fromusername) {
		this.fromusername = fromusername;
	}
	public String getTomerchantname() {
		return tomerchantname;
	}
	public void setTomerchantname(String tomerchantname) {
		this.tomerchantname = tomerchantname;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getSignedRequest() {
		return signedRequest;
	}
	public void setSignedRequest(String signedRequest) {
		this.signedRequest = signedRequest;
	}
	public byte[] getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(byte[] publicKey) {
		this.publicKey = publicKey;
	}
	public String getStringForEncryption(){
		String result = this.fromusername;
		return result;
	}

}
