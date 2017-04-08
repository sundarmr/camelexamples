package com.camel.failover.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyAppDbLocker {
	final static Logger LOG = LoggerFactory.getLogger(MyAppDbLocker.class);
	String serverName;
	String applicationName;
	boolean leaseOwner;
	long leaseRenewTime=100000;
	public boolean isLeaseOwner() {
		return leaseOwner;
	}

	public void setLeaseOwner(boolean leaseOwner) {
		this.leaseOwner = leaseOwner;
	}

	private Thread thread;

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public void start() {
		this.thread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					isLeased();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}

	public void isLeased() throws Exception {
		/*
		 * Have to deal with exceptions and rewrite the code 
		 * Also the lease renewal time and the first timer route
		 * needs to be synched in such a way that the possibility of the both 
		 * the servers running the route is near impossible.
		 */
		while (true) {
			Class.forName("org.postgresql.Driver");

			String url = "jdbc:postgresql://localhost/quartz?user=quartz&password=quartz";
			Connection connection = DriverManager.getConnection(url);

			
			String checkIfAppExists = "select count(1) from APP_CLUSTER_LOCKTABLE where APPLICATION_NAME='SAMPLE'";
			PreparedStatement prepareStatement = connection.prepareStatement(checkIfAppExists);
			ResultSet executeQuery2 = prepareStatement.executeQuery();
			if (executeQuery2.next()) {
				int int1 = executeQuery2.getInt(1);
				if (int1 == 0) {
					LOG.info("The current app does not exist creating one ..");
					prepareStatement = connection.prepareStatement(
							"INSERT INTO app_cluster_locktable (application_name,server_name) values ('SAMPLE',?)");
					prepareStatement.setString(1, getServerName());
					int executeUpdate = prepareStatement.executeUpdate();
					if (executeUpdate == 1) {
						LOG.info("Initial data inserted");
					}
				}
			}
			String updateStatement = "UPDATE APP_CLUSTER_LOCKTABLE SET APPLICATION_NAME= 'SAMPLE' , TIME=? , SERVER_NAME='"
					+ getServerName() + "' WHERE APPLICATION_NAME='SAMPLE' and TIME is NULL or TIME<?";
			String leaseTest = "SELECT TIME,server_name from APP_CLUSTER_LOCKTABLE WHERE APPLICATION_NAME=?";
			
			prepareStatement = connection.prepareStatement(leaseTest);
			prepareStatement.setString(1, "SAMPLE");
			ResultSet executeQuery = prepareStatement.executeQuery();
			final long now = System.currentTimeMillis();
			LOG.info("Result set was null:"+executeQuery.wasNull());
			while (executeQuery.next()) {
				long time = executeQuery.getLong(1);
				String serverName = executeQuery.getString(2);
				LOG.info("the time from  server is " + time);
				LOG.info("now is " + now);
				if (time != 0 && time > now) {
					if (!getServerName().equalsIgnoreCase(serverName)) {
						LOG.info("Lease is owned by another system " + serverName);
						setLeaseOwner(false);
					}

				}
			}

			prepareStatement = connection.prepareStatement(updateStatement);
			prepareStatement.setLong(1, now + 30000);
			prepareStatement.setLong(2, now);
			int executeUpdate = prepareStatement.executeUpdate();
			if (executeUpdate == 1) {
				LOG.info("Lease Acquired ....by" + getServerName());
				setLeaseOwner(true);
			}
			Thread.sleep(leaseRenewTime);
		}
	}
}