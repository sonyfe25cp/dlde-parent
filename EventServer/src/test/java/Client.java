
import java.net.Socket;
import java.io.*;

public class Client {
	public Client() {
	}

	public static void main(String[] args) {
		Socket client = null;
		DataOutputStream out = null;
		DataInputStream in = null;
		try {
			client = new Socket("127.0.0.1", 9901);
			client.setSoTimeout(10000);
			out = new DataOutputStream((client.getOutputStream()));

			String query = "GB";
			byte[] request = query.getBytes();
			out.write(request);
			out.flush();
			client.shutdownOutput();

			in = new DataInputStream(client.getInputStream());
			byte[] reply = new byte[40];
			in.read(reply);
			System.out.println("Time: " + new String(reply, "UTF8"));

			in.close();
			out.close();
			client.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
