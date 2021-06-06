package network;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class host2ip {

	public static void main(String[] args) throws UnknownHostException {
		String hostname = "www.naver.com";
		InetAddress address = InetAddress.getByName(hostname);
		System.out.println("IP 주소 : " + address.getHostAddress());
	}

}
