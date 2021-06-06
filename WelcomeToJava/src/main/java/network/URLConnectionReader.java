package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class URLConnectionReader {

	public static void main(String[] args) throws IOException {
		URL site = new URL("http://www.kangwon.ac.kr/");
		URLConnection url = site.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(url.getInputStream()));
		String line;
		while((line = in.readLine()) != null) {
			System.out.println(line);
		}
		in.close();
	}

}
