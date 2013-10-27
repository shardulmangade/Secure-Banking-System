package asu.edu.sbs.customer.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.List;
import java.util.StringTokenizer;

import javax.activity.InvalidActivityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import asu.edu.sbs.db.CustomerDBConnection;
import asu.edu.sbs.db.SalesDBConnectionManager;
import asu.edu.sbs.domain.Credit;
import asu.edu.sbs.domain.User;
import asu.edu.sbs.exception.BankStorageException;

@Scope(value="session")
@Service
public class CustomerManager {	
	
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
		
		public String getAccountNumberForCustomer (String userName) throws Exception
		{
			return (customerdbconnection.getAccountNumberForCustomer(userName));
		}
		/**
		 * This method returns the signed request
		 * @return
		 */
		public String getSignedRequest(PrivateKey privateKey, Credit credit) {
			byte[] realSig = null;
			byte[] buffer = new byte[1024];
			try{
				//sign the data
//				Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");				
//				dsa.initSign(privateKey);
//				String toEncrypt = credit.getStringForEncryption();
//				InputStream is = new ByteArrayInputStream(toEncrypt.getBytes());
//				BufferedInputStream bufin = new BufferedInputStream(is);			
//				int len;
//				while ((len = bufin.read(buffer)) >= 0) {
//				    dsa.update(buffer, 0, len);
//				}
//				bufin.close();
//				realSig = dsa.sign();
				Signature dsa = Signature.getInstance("SHA1withDSA");				
				dsa.initSign(privateKey);
				String toEncrypt = credit.getStringForEncryption();
				dsa.update(credit.getStringForEncryption().getBytes());
				realSig = dsa.sign();
				
			}catch(Exception ex){
				try {
					throw new BankStorageException(ex);
				} catch (BankStorageException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return getString(realSig);
		}
		
		/**
		 * verify request 
		 * @param request
		 * @return
		 */
		public boolean verifyRequest(Credit credit, String request, PublicKey publicKey){
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
		
	 private static String getString( byte[] bytes )
	  {
	   StringBuffer sb = new StringBuffer();
	   for( int i=0; i<bytes.length; i++ )
	   {
	     byte b = bytes[ i ];
	     sb.append( ( int )( 0x00FF & b ) );
	     if( i+1 <bytes.length )
	     {
	      sb.append( "-" );
	     }
	   }
	   return sb.toString();
	}
	 
	 private static byte[] getBytes( String str )
	  {
	   ByteArrayOutputStream bos = new ByteArrayOutputStream();
	   StringTokenizer st = new StringTokenizer( str, "-", false );
	   while( st.hasMoreTokens() )
	   {
	     int i = Integer.parseInt( st.nextToken() );
	     bos.write( ( byte )i );
	   }
	   return bos.toByteArray();
	  }
}
