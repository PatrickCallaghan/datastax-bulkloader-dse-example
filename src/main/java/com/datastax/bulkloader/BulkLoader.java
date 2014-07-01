package com.datastax.bulkloader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.datastax.sampledata.Transaction;

public interface BulkLoader {

	public void loadTransactions(List<Transaction> transactions)
			throws IOException;

	public File getFilePath();

	public void finish() throws IOException;
}