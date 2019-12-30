package network;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SocketTest {

	public static void main(String[] args) throws UnknownHostException, IOException {
		try (Socket s = new Socket("time-c.nist.gov", 13)) {
			InputStream inStream = s.getInputStream();
			Scanner in = new Scanner(inStream);
			while(in.hasNextLine()) {
				String line = in.nextLine();
				System.out.println(line);
			}
			in.close();
		}
	}

}
