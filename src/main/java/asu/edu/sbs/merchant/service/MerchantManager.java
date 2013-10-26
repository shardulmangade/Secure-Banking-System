package asu.edu.sbs.merchant.service;

import java.util.List;

import javax.activity.InvalidActivityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asu.edu.sbs.db.MerchantDBConnectionManager;
import asu.edu.sbs.domain.Credit;
import asu.edu.sbs.domain.MerchantTransaction;

@Service
public class MerchantManager {
	
	@Autowired
	private MerchantDBConnectionManager merchantdbconnection;
	
	public List<MerchantTransaction> getAllPendingTransaction(String merchantName)
	{
		return merchantdbconnection.getAllPendingTransaction(merchantName);		
	}

}
