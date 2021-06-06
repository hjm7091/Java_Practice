package network;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TranslationClient extends JFrame implements ActionListener{

	private BufferedReader in;
	private PrintWriter out;
	private JTextField field;
	private JTextArea area;
	private String first, second;
	private Socket socket;
	
	public TranslationClient() throws Exception, IOException {
		setTitle("클라이언트");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		field = new JTextField(50);
		field.addActionListener(this);
		
		area = new JTextArea(10, 50);
		
		area.setEditable(false);
		add(field, BorderLayout.NORTH);
		add(area, BorderLayout.CENTER);
		
		socket = new Socket("localhost", 9101);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		
		first = in.readLine();
		second = in.readLine();
		area.append(first + "\n");
		area.append(second + "\n");
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		String input = field.getText();
		if(input.equals("clear")) {
			area.setText("");
			area.append(first+"\n");
			area.append(second+"\n");
		}
		else if(input.equals("end")) {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(0);
		}
		else {
			out.println(input);
			String response = null;
			try {
				response = in.readLine();
			} catch (IOException e) {
				System.out.println("소켓 종료 오류" + e);
			}
			area.append(response+"\n");
		}
	}

	public static void main(String[] args) throws IOException, Exception {
		TranslationClient client = new TranslationClient();
	}
}
