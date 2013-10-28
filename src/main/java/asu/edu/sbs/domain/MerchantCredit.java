package asu.edu.sbs.domain;

public class MerchantCredit {

	private String transactionID;
	private String fromaccount ;
	private String tomerchantaccount ;
	private String fromusername;
	private String tomerchantname;
	private Double amount;
	private byte[] signedRequest;
	private String publicKey;

}
