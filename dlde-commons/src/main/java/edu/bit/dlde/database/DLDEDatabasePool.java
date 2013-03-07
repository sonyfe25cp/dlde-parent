//package edu.bit.dlde.database;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//import com.jolbox.bonecp.BoneCPDataSource;
//
//import edu.bit.dlde.utils.DLDEConfiguration;
//import edu.bit.dlde.utils.DLDELogger;
//
///**
// * 
// * @author ChenJie
// * 
// */
//public class DLDEDatabasePool {
//
//	private DLDELogger logger = new DLDELogger();
//
//	private static DLDEDatabasePool instance = null;
//
//	private DLDEConfiguration config = null;
//
//	boolean flag = false;
//
//	private DLDEDatabasePool(boolean flag) {
//		try {
//			this.flag = flag;
//			start();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			logger.error("init database pool error , please check the file  'database.properties'");
//			e.printStackTrace();
//		}
//	}
//
//	public static DLDEDatabasePool getInstance() {
//		if (instance == null) {
//			instance = new DLDEDatabasePool(false);
//		}
//		return instance;
//	}
//
//	public static DLDEDatabasePool getInstance4Maven() {
//		if (instance == null) {
//			instance = new DLDEDatabasePool(true);
//		}
//		return instance;
//	}
//
//	private BoneCPDataSource datasource;
//
//	private void start() throws Exception {
//		if (!flag) {
//			config = DLDEConfiguration.getInstance("database.properties");
//		} else {
//			config = DLDEConfiguration.getInstance4Maven("database.properties");
//		}
//		String driver = config.getValue("dbDriver");
//		String jdbcUrl = config.getValue("dbUrl");
//		String username = config.getValue("dbUser");
//		String password = config.getValue("dbPasswd");
//
//		String maxValue = config.getValue("max");
//		String minValue = config.getValue("min");
//		String incrementValue = config.getValue("increment");
//
//		int max = maxValue == null ? 50 : Integer.parseInt(config
//				.getValue("max"));
//		int min = minValue == null ? 20 : Integer.parseInt(config
//				.getValue("min"));
//		int increment = incrementValue == null ? 10 : Integer.parseInt(config
//				.getValue("increment"));
//
//		Class.forName(driver); // load the DB driver
//		datasource = new BoneCPDataSource();
//
//		if (driver == null || jdbcUrl == null || username == null
//				|| password == null) {
//			logger.error("can't init databasepool, check the file 'database.properties'");
//		}
//
//		datasource.setJdbcUrl(jdbcUrl);
//		datasource.setUsername(username);
//		datasource.setPassword(password);
//		datasource.setMaxConnectionsPerPartition(max);
//		datasource.setMinConnectionsPerPartition(min);
//		datasource.setAcquireIncrement(increment);
//	}
//
//	public Connection getConnection() {
//		try {
//			return datasource.getConnection();
//		} catch (SQLException e) {
//			logger.error("datasource get connection error !!");
//			return null;
//		}
//	}
//
//	public void close() {
//		datasource.close();
//		instance = null;
//	}
//
//}
