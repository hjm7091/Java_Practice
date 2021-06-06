package management.program.score;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

@SuppressWarnings({"rawtypes", "unchecked"})
public class DatabaseManager {
	private static final String className = "org.mariadb.jdbc.Driver";
	private static final String url = "jdbc:mariadb://127.0.0.1:3306/database_programming";
	private static final String ID = "root";
	private static final String PW = "system";
	
	static {
		try {
			Class.forName(className);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConn() {
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(url, ID, PW);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	public int insertScore(Score score) {
		Connection con = null;
		CallableStatement cstmt = null;
		
		int result = 0;
		try {
			con = getConn();
			cstmt = con.prepareCall("{call insertScoreProcedure(?,?,?,?,?,?)}");
			cstmt.setString(1,score.getName());
			cstmt.setInt(2,score.getKor());
			cstmt.setInt(3,score.getEng());
			cstmt.setInt(4,score.getMath());
			cstmt.setInt(5,score.getTotal());
			cstmt.setInt(6,score.getAverage());
			
			result = cstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(cstmt != null) {
					cstmt.close();
				}
				if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public int DeleteScore(Score score) {
		Connection con = null;
		CallableStatement cstmt = null;
		
		int result = 0;
		try {
			con = getConn();
			cstmt = con.prepareCall("{call deleteScoreProcedure(?)}");
			cstmt.setString(1, score.getName());
			
			result = cstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(cstmt != null) {
					cstmt.close();
				}
				if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public int UpdateScore(Score score) {
		Connection con = null;
		CallableStatement cstmt = null;
		
		int result = 0;
		try {
			con = getConn();
			cstmt = con.prepareCall("{call updateScoreProcedure(?,?,?,?,?,?)}");
			cstmt.setString(1,score.getName());
			cstmt.setInt(2,score.getKor());
			cstmt.setInt(3,score.getEng());
			cstmt.setInt(4,score.getMath());
			cstmt.setInt(5,score.getTotal());
			cstmt.setInt(6,score.getAverage());
			
			result = cstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(cstmt != null) {
					cstmt.close();
				}
				if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public Vector getScore() {
		Vector data = new Vector();
		Connection con = null;
		ResultSet rs = null;
		CallableStatement cstmt = null;
		
		try {
			con = getConn();
			cstmt = con.prepareCall("{call selectScoreProcedure()}");
			rs = cstmt.executeQuery();
			
			while(rs.next()) {
				int ran = rs.getInt("ran");
				String name = rs.getString("name");
				int kor = rs.getInt("kor");
				int eng = rs.getInt("eng");
				int math = rs.getInt("math");
				int total = rs.getInt("total");
				int average = rs.getInt("average");
				String grade = rs.getString("grade");
				
				Vector row = new Vector();
				row.add(ran);
				row.add(name);
				row.add(kor);
				row.add(eng);
				row.add(math);
				row.add(total);
				row.add(average);
				row.add(grade);
				data.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(cstmt != null) cstmt.close();
				if(con != null) con.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return data;
	}	
}
