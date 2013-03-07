package edu.bit.dlde.eventserver.model;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Response {
	private SocketChannel sc;

	public Response(SocketChannel sc) {
		this.sc = sc;
	}

	/**
	 * 向客户端写数据
	 * 
	 * @param data
	 *            byte[]　待回应数据
	 */
	public void send(byte[] data) throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(data.length);
		buffer.put(data, 0, data.length);
		buffer.flip();
		sc.write(buffer);
	}
}
