package com.datastax.sampledata;

import java.io.IOException;
import java.util.List;

import com.datastax.bulkloader.BulkLoader;

public class CreateSSTables {

	public static void createSSTables(BulkLoader bulkLoader, int totalTrans) throws IOException {
				
		int batch = 10000;
		int cycles = totalTrans / batch;	
				
		for (int i=0; i < cycles; i++){
			List<Transaction> transactions = TransactionGenerator.generatorTransaction(batch);
			bulkLoader.loadTransactions(transactions);
				
			if (cycles % batch == 0){
				System.out.println("Wrote " + i + " of " + cycles + " cycles. Batch size : " + batch);			
			}
		}				
		bulkLoader.finish();
		
		System.out.println("Finished file with " + totalTrans + " transactions.");
	}

}
