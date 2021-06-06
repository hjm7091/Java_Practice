package chat;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MessengerA {

	protected JTextField textField;
	protected JTextArea textArea;
	DatagramSocket socket;
	DatagramPacket packet;
	InetAddress address = null;
	final int myPort = 5000;
	final int otherPort = 6000;
	
	public MessengerA() throws IOException {
		MyFrame f = new MyFrame();
		address = InetAddress.getByName("127.0.0.1");
		socket = new DatagramSocket(myPort);
	}
	
	public void process() {
		while(true) {
			try {
				byte[] buf = new byte[256];
				packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				textArea.append("RECEIVED: " + new String(buf) + "\n");
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	class MyFrame extends JFrame implements ActionListener{
		
		public MyFrame() {
			super("MessengerA");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			textField = new JTextField(30);
			textField.addActionListener(this);
			
			textArea = new JTextArea(10, 30);
			textArea.setEditable(false);
			
			add(textField, BorderLayout.PAGE_END);
			add(textArea, BorderLayout.CENTER);
			pack();
			setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent a) {
			String s = textField.getText();
			byte[] buffer = s.getBytes();
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, otherPort);
			try {
				socket.send(packet);
			}catch (IOException e) {
				e.printStackTrace();
			}
			textArea.append("SENT: " + s + "\n");
			textField.selectAll();
			textArea.setCaretPosition(textArea.getDocument().getLength());
		}
	}
	
	public static void main(String[] args) throws IOException{
		MessengerA m = new MessengerA();
		m.process();
	}

}
