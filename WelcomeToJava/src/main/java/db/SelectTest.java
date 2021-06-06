package db;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

class MyFrame extends JFrame{
	
	JTextField id, title, p, year, price;
	JButton previousButton, nextButton, InsertButton, deleteButton, searchButton;
	ResultSet rs;
	Statement stmt;
	
	public MyFrame() throws SQLException{
		super("Database Viewer");
		Connection conn = makeConnection();
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		rs = stmt.executeQuery("select * from books");
		setLayout(new GridLayout(0, 2));
		add(new JLabel("ID", JLabel.CENTER));
		add(id = new JTextField());
		add(new JLabel("TITLE", JLabel.CENTER));
		add(title = new JTextField());
		add(new JLabel("PUBLISHER", JLabel.CENTER));
		add(p = new JTextField());
		add(new JLabel("YEAR", JLabel.CENTER));
		add(year = new JTextField());
		add(new JLabel("PRICE", JLabel.CENTER));
		add(price = new JTextField());
	
		previousButton = new JButton("Previous");
		previousButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(!rs.isFirst()) {
						rs.previous();
						id.setText(" " + rs.getInt("book_id"));
						title.setText(" " + rs.getString("title"));
						p.setText(" " + rs.getString("publisher"));
						year.setText(" " + rs.getString("year"));
						price.setText(" " + rs.getInt("price"));
					}
				} catch (SQLException er) {
					er.printStackTrace();
				}
			}
		});
		
		nextButton = new JButton("Next");
		nextButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(!rs.isLast()) {
						rs.next();
						id.setText(" " + rs.getInt("book_id"));
						title.setText(" " + rs.getString("title"));
						p.setText(" " + rs.getString("publisher"));
						year.setText(" " + rs.getString("year"));
						price.setText(" " + rs.getInt("price"));
					}
				} catch (SQLException er) {
					er.printStackTrace();
				}
			}
		});
		
		add(nextButton);
		add(previousButton);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(350, 200);
		setVisible(true);
	}
	
	public Connection makeConnection() {
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String uid = "scott";
		String upw = "tiger";
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 적재 성공");
			con = DriverManager.getConnection(url, uid, upw);
			System.out.println("데이터베이스 연결 성공");
		}catch (ClassNotFoundException e) {
			System.out.println("드라이버를 찾을 수 없습니다.");
		}catch (SQLException e) {
			System.out.println("연결에 실패하였습니다.");
		}
		return con;
	}
}

public class SelectTest {

	public static void main(String[] args) throws SQLException{
		new MyFrame();
	}

}
