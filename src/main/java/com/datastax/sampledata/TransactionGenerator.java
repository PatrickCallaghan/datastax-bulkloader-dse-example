package com.datastax.sampledata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TransactionGenerator {

	static List<Transaction> generatorTransaction(int num){
		
		List<Transaction> transactions = new ArrayList<Transaction>();
		
		for (int i=0; i < num; i++){
			Transaction trans = new Transaction();
			trans.setAmount(Math.random()*4000000);
			trans.setAcountId(new Double(Math.random()*10000000).intValue() + "");
			trans.setTxtnId(UUID.randomUUID().toString());
			trans.setTxtnDate(new Date());
			trans.setType(Math.random() < .5 ? "D" : "C");
			trans.setReason("Some reason");
			
			transactions.add(trans);
		}
		
		return transactions;
	}
}
