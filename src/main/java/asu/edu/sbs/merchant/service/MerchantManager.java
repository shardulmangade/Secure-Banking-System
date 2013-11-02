package asu.edu.sbs.merchant.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.activity.InvalidActivityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asu.edu.sbs.db.CustomerDBConnection;
import asu.edu.sbs.db.MerchantDBConnectionManager;
import asu.edu.sbs.domain.Credit;
import asu.edu.sbs.domain.MerchantCredit;
import asu.edu.sbs.domain.MerchantTransaction;
import asu.edu.sbs.exception.BankStorageException;

@Service
public class MerchantManager {
	
	@Autowired
	private MerchantDBConnectionManager merchantdbconnection;
	@Autowired
	private CustomerDBConnection customerdbconnection;
	
	public List<Credit> getAllTransaction(String userName)
	{
		return customerdbconnection.getAllTransaction( userName);		
	}
	
	
	public int insertNewTransaction(Credit credit) throws Exception
	{			
		double fromCustomerBalance = customerdbconnection.getbalanceofCustomer(credit.getFromCustomer());
		double toCustomerBalance = customerdbconnection.getbalanceofCustomer(credit.getToCustomer());
		double newfromBalance=0,newtobalance=0;
		if (fromCustomerBalance > 0)
		{
			newfromBalance= fromCustomerBalance-credit.getAmount();
			
			
			if(newfromBalance >=0) {
				newtobalance = toCustomerBalance +credit.getAmount(); 
				System.out.println("newfromBalance"+newfromBalance);
				System.out.println("newtobalance"+ newtobalance);
				System.out.println("fromuser"+credit.getFromCustomer());
				System.out.println("touser "+credit.getToCustomer());
				customerdbconnection.updatebalanceofCustomer(credit.getFromCustomer(),newfromBalance);
				customerdbconnection.updatebalanceofCustomer(credit.getToCustomer(),newtobalance);
				return (customerdbconnection.insertNewTransaction(credit));
			}else {
				throw new InvalidActivityException();
			}
		} else
		{
			throw new InvalidActivityException();
		}			
	}
	
	public boolean validateRecepientUser(String userName,String account) throws Exception
	{
		return (customerdbconnection.validateRecepientUser(userName,account));
	}
	public List<MerchantTransaction> getAllPendingTransaction(String merchantName)
	{
		return merchantdbconnection.getAllPendingTransaction(merchantName);		
	}
	
	/**
	 * This method verifies the signature of customer.
	 * 
	 */
	public boolean verifyRequest(MerchantCredit credit, String request, PublicKey publicKey){
		boolean result = true;
		try{
			Signature sig = Signature.getInstance("SHA1withDSA");
			sig.initVerify(publicKey);
			sig.update(credit.getStringForEncryption().getBytes());
			result = sig.verify(getBytes(request));			
			
		}catch(Exception ex){
			try {
				throw new BankStorageException(ex);
			} catch (BankStorageException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * get bytes of the request
	 * 
	 */
	 private static byte[] getBytes( String str ){
		 ByteArrayOutputStream bos = new ByteArrayOutputStream();
		 StringTokenizer st = new StringTokenizer( str, "-", false );
		 while( st.hasMoreTokens() ){
			 int i = Integer.parseInt( st.nextToken() );
			 bos.write( ( byte )i );
		}
	   return bos.toByteArray();
	 }

	/**
	 * This method pulls the list of transaction from merchants request table
	 * 
	 * 
	 */
	public ArrayList<MerchantCredit> getPendingRequest(String userName) {
		//get all the transaction in db.
		return merchantdbconnection.getPendingMerchantTransaction(userName);
	}


	/**
	 * This method takes the request list as input and approves them. 
	 * If the sig is verified then the balance is changed in both the account
	 * otherwise do something hellish
	 * @param requestList
	 * @throws BankStorageException
	 */
	public void verifyAprrove(ArrayList<MerchantCredit> requestList) throws BankStorageException {
		try{
			for(int i=0; i < requestList.size(); i++){
				MerchantCredit merCredit = requestList.get(i);
				PublicKey pKey = blobToPublicKey(merCredit.getPublicKey());
				boolean verify = verifyRequest(merCredit, merCredit.getSignedRequest(), pKey);
				if(verify){
					try {//change this exception handling APURV PATKI
						customerdbconnection.insertNewTransaction(merCredit);
						merchantdbconnection.deletePendingMerchantTransaction(merCredit.getTransactionID());
					}catch (Exception e){
						e.printStackTrace();
					}
				}else{
					//do something here, if the certificate is not valid
				}
			}
		}catch(Exception ex){
			
		}
		
	}

	public PublicKey blobToPublicKey(byte[] blob) throws Exception
    {
		PublicKey publicKey = KeyFactory.getInstance("DSA").generatePublic(new X509EncodedKeySpec(blob));
        return publicKey;
    }

	
}
