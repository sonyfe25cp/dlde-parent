package edu.bit.dlde.utils;

import org.apache.log4j.LogManager;


public class DLDELogger {

	private org.apache.log4j.Logger logger = null;

	public DLDELogger() {
		logger = LogManager.getLogger(new Exception().getStackTrace()[1]
				.getClassName());
	}

	public void error(String string) {
		logger.error(string);
	}

	public void debug(String string) {
		if(logger.isDebugEnabled())
			logger.debug(string);
	}

	public void info(String string) {
		if(logger.isInfoEnabled())
			logger.info(string);
	}

}
