package network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLTest {

	public static void main(String[] args) throws Exception {
		String site = "http://www.google.com/search?q=java";
		URL url = new URL(site);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("User-Agent", "Mozilla/5.0");
		int resCode = conn.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer output = new StringBuffer();
		while((inputLine = in.readLine()) != null) {
			output.append(inputLine+"\n");
		}
		in.close();
		System.out.println(output);
	}

}
