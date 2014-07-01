package com.datastax.bulkloader;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import org.apache.cassandra.exceptions.InvalidRequestException;
import org.apache.cassandra.io.sstable.CQLSSTableWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.sampledata.Transaction;

/**
 * To run this code, you need to have your cluster 'cassandra.yaml' and
 * 'log4j-tools.properties' in the 'src/main/resources' directory.
 * 
 */
public class CQL3BulkLoadTransactions implements BulkLoader {

	private static Logger logger = LoggerFactory.getLogger(CQL3BulkLoadTransactions.class);
	
	private DateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS");
	private DateFormat monthYearFormat = new SimpleDateFormat("yyyyMM");

	private File filePath;
	private String keyspace = "datastax_bulkload_demo";
	private String tableName = keyspace + ".transactions";

	private String schema = "create table " + tableName + " ( "
			+ "accid text, txtntime timestamp, txtnid uuid, amount double, type text, reason text, "
			+ "PRIMARY KEY((accid), txtntime) );";
	
	private String insertStatement = "insert into " + tableName + " (accid, txtntime, txtnid, amount, type, reason) values (?,?,?,?,?,?)";
	private CQLSSTableWriter writer;

	public CQL3BulkLoadTransactions() throws IOException {
		
		logger.info("Using CQL3 Writer");
		
		createDirectories(keyspace, tableName);
		
		this.writer = CQLSSTableWriter.builder()
				.forTable(schema)
				.using(insertStatement)
				.inDirectory(getFilePath().getAbsolutePath())
				.build();
	}
	
	public File getFilePath(){
		return this.filePath;
	}

	private void createDirectories(String keyspace, String tableName) {
		File directory = new File(keyspace);
		if (!directory.exists())
			directory.mkdir();
		
		filePath = new File(directory, tableName);
		if (!filePath.exists())
			filePath.mkdir();
	}

	/**
	 * 
	 create table transactions ( accid text, txtntime timestamp, txtnid uuid,
	 * amount double, type text, reason text, PRIMARY KEY((accid), txtntime) );
	 */
	public void loadTransactions(List<Transaction> transactions)
			throws IOException {

		for (Transaction transaction : transactions) {
			
			String yearMonth = monthYearFormat
					.format(transaction.getTxtnDate());
			String rowKey =transaction.getAcountId() + "-" + yearMonth;			
			double amount = transaction.getAmount();
			
			try {
				this.writer.addRow(rowKey, transaction.getTxtnDate(), UUID.fromString(transaction.getTxtnId()), amount, transaction.getType(), transaction.getReason());
			} catch (InvalidRequestException e) {
				e.printStackTrace();
				
				System.exit(5);
			}	
		}
	}
	
	public void finish() throws IOException {
		writer.close();
	}
}