package com.datastax.sampledata;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CreateSampleData {

	private BufferedWriter out;
	private int totalTrans = 50000000;

	public CreateSampleData() throws IOException {
		out = new BufferedWriter(new FileWriter("src/main/resources/Transactions.csv"));
		
		int batch = 1000;
		int cycles = this.totalTrans / batch;	
		
		for (int i=0; i < cycles; i++){
			List<Transaction> transactions = TransactionGenerator.generatorTransaction(batch);
			this.writeToFile(transactions);
			
			if (cycles % 1000 == 0){
				System.out.println("Wrote " + i + " of " + cycles + " cycles.");			
			}
		}
		
		out.close();
		System.out.println("Finished file with " + this.totalTrans + " transactions.");
		System.exit(0);
	}

	public void writeToFile(List<Transaction> transactions) throws IOException {
		try {
			for (Transaction transaction : transactions) {
				out.write(transaction.toCVSString() + "\n");
			}
		} catch (IOException e) {
			throw e;
		}
	}

	public static void main(String[] args) {
		try {
			new CreateSampleData();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
