package edu.bit.dlde.eventserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.bit.dlde.eventserver.adapter.Reader;
import edu.bit.dlde.eventserver.adapter.Writer;
import edu.bit.dlde.eventserver.model.Request;
import edu.bit.dlde.utils.DLDELogger;

public class SingleEngine implements Runnable {
	private DLDELogger logger = new DLDELogger();
	private static Selector selector;

	private int port;
	private String ip;
	private static int MAX_THREADS = 8;

	private ServerSocketChannel sschannel;
	private InetSocketAddress address;
	protected Notifier notifier;
	private static List wpool = new LinkedList(); // 回应池

	public SingleEngine(String ip, int port) throws IOException{
		this.ip = ip;
		this.port = port;
		try {
			init();
		} catch (IOException e) {
			logger.error("server init error !");
			throw e;
		}
	}

	public void init() throws IOException {
		// 获取事件触发器
		notifier = Notifier.getNotifier();

		// 创建读写线程池
		for (int i = 0; i < MAX_THREADS; i++) {
			Thread r = new Reader();
			Thread w = new Writer();
			r.start();
			w.start();
		}

		// 创建无阻塞网络套接
		selector = Selector.open();
		sschannel = ServerSocketChannel.open();
		sschannel.configureBlocking(false);
		address = new InetSocketAddress(ip, port);
		ServerSocket ss = sschannel.socket();
		ss.bind(address);
		sschannel.register(selector, SelectionKey.OP_ACCEPT);
	}

	public void run() {
		System.out.println("Server started ...");
		System.out.println("Server listening on port: " + port);
		try {
			while (true) {
				int num = 0;
				num = selector.select();
				if (num > 0) {
					Iterator<SelectionKey> iterator = selector.selectedKeys()
							.iterator();
					while (iterator.hasNext()) {
						SelectionKey key = iterator.next();
						iterator.remove(); // 删除此消息
						if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
							// Accept the new connection
							ServerSocketChannel ssc = (ServerSocketChannel) key
									.channel();
							notifier.fireOnAccept();

							SocketChannel sc = ssc.accept();
							sc.configureBlocking(false);

							// 触发接受连接事件
							Request request = new Request(sc);
							notifier.fireOnAccepted(request);

							// 注册读操作,以进行下一步的读操作
							sc.register(selector, SelectionKey.OP_READ, request);
						} else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
							Reader.processRequest(key); // 提交读服务线程读取客户端数据
							key.cancel();
						} else if ((key.readyOps() & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE) {
							Writer.processRequest(key); // 提交写服务线程向客户端发送回应数据
							key.cancel();
						}

					}
				} else {
					addRegister(); // 在Selector中注册新的写通道
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加新的通道注册
	 */
	private void addRegister() {
		synchronized (wpool) {
			while (!wpool.isEmpty()) {
				SelectionKey key = (SelectionKey) wpool.remove(0);
				SocketChannel schannel = (SocketChannel) key.channel();
				try {
					schannel.register(selector, SelectionKey.OP_WRITE,
							key.attachment());
				} catch (Exception e) {
					try {
						schannel.finishConnect();
						schannel.close();
						schannel.socket().close();
						notifier.fireOnClosed((Request) key.attachment());
					} catch (Exception e1) {

					} finally {
						notifier.fireOnError("Error occured in addRegister: "
								+ e.getMessage());
					}
				}
			}
		}
	}

	/**
	 * 提交新的客户端写请求于主服务线程的回应池中
	 */
	public static void processWriteRequest(SelectionKey key) {
		synchronized (wpool) {
			wpool.add(wpool.size(), key);
			wpool.notifyAll();
		}
		selector.wakeup(); // 解除selector的阻塞状态，以便注册新的通道
	}
}
