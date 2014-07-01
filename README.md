DSE 4.5 Bulk Loader example
========================================================
This demo creates the sstable files and loads them through jmx to a dse cluster.


## Adding the dse-4.5.0 jar

You will need to add the dse jar to your local maven repository before you start. To do this, do the <DSE_INSTALL_DIR>/lib/

There you should see the dse-4.5.0.jar file. 

Run the following

	mvn install:install-file -Dfile=dse-4.5.0.jar -DgroupId=com.datastax -DartifactId=dse -Dversion=4.5.0 -Dpackaging=jar
	
## Running the demo 

To run this code, you need to have your cluster 'cassandra.yaml', dse.yaml and 'log4j-tools.properties' in the 'src/main/resources' directory.

You will need a java 7 runtime along with maven 3 to run this demo. Start DSE 4.5.X This demo just runs as a standalone process on the localhost.

This demo uses quite a lot of memory so it is worth setting the MAVEN_OPTS to run maven with more memory

    export MAVEN_OPTS=-Xmx512M


## Schema Setup
Note : This will drop the keyspace "datastax_bulkload_demo" and create a new one. All existing data will be lost. 

To create the a single node cluster with replication factor of 1 for standard localhost setup, run the following

    mvn clean compile exec:java -Dexec.mainClass="com.datastax.demo.SchemaSetup"

To run the bulk loader, this defaults to 1 million rows.

    mvn clean compile exec:java -Dexec.mainClass="com.datastax.bulkloader.Main" 
    
To run it other settings for no of rows, jmx host and port

	mvn clean compile exec:java -Dexec.mainClass="com.datastax.bulkloader.Main"  -DnoOfRows=5000000 -Djmxhost=cassandra1 -Djmxport=7191    
		
To remove the tables and the schema, run the following.

    mvn clean compile exec:java -Dexec.mainClass="com.datastax.demo.SchemaTeardown"
	
