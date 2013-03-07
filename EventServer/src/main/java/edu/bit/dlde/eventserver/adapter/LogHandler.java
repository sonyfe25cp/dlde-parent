package edu.bit.dlde.eventserver.adapter;

import java.util.Date;

import edu.bit.dlde.eventserver.model.Request;
import edu.bit.dlde.utils.DLDELogger;

public class LogHandler extends EventAdapter {
	private DLDELogger logger = new DLDELogger();

	public LogHandler() {
	}

	public void onClosed(Request request) throws Exception {
		String log = new Date().toString() + " from "
				+ request.getAddress().toString();
		logger.info(log);
	}

	public void onError(String error) {
		logger.error("Error: " + error);
	}
}
