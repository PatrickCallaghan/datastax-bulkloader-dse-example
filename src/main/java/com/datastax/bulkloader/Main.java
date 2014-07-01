package com.datastax.bulkloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.demo.utils.PropertyHelper;
import com.datastax.demo.utils.Timer;
import com.datastax.jmxloader.JmxBulkLoader;
import com.datastax.sampledata.CreateSSTables;

public class Main {

	private static Logger logger = LoggerFactory.getLogger( Main.class );
	
	public Main(){
		
		String host = PropertyHelper.getProperty("jmxhost", "localhost");
		int port = Integer.parseInt(PropertyHelper.getProperty("jmxport", "7199"));
		int noOfRows = Integer.parseInt(PropertyHelper.getProperty("noOfRows", "1000000"));
		
		try {
			Timer timer = new Timer();
			
			timer.start();
			BulkLoader bulkLoader = new CQL3BulkLoadTransactions();
		
			logger.info("Creating SSTables");
			CreateSSTables.createSSTables(bulkLoader, noOfRows);
			logger.info("Finished creating SSTables");

			logger.info("Running Bulk Load via JMX");
			JmxBulkLoader jmxLoader = new JmxBulkLoader(host, port);
			jmxLoader.bulkLoad(bulkLoader.getFilePath().getAbsolutePath());
			timer.end();
			logger.info("Finished Bulk Load in " + timer.getTimeTakenSeconds() + " secs.");
			jmxLoader.close();
		} catch (Exception e) {
			logger.error("Could not process JMX Loader due to error : " + e.getMessage());
			e.printStackTrace();
		}		
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
