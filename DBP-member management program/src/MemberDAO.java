import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

@SuppressWarnings({"rawtypes","unchecked"})
public class MemberDAO {
	private static final String DRIVER = "org.mariadb.jdbc.Driver";
	private static final String URL = "jdbc:mariadb://127.0.0.1:3306/mydb2";
	private static final String PASS = "system";
	private static final String USER = "root";
	
	Member_List mList;
	
	public Connection getConn() {
		Connection con = null;
		
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USER,PASS);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	public MemberDTO getMemberDTO(String id) {
		MemberDTO dto = new MemberDTO();
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			
			con = getConn();
			String sql = "SELECT * FROM tb_staff as a, tb_staff_info as b WHERE a.id=b.id and b.id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				dto.setId(rs.getString("id"));
				dto.setPwd(rs.getString("Pwd"));
				dto.setName(rs.getString("Name"));
				dto.setTel(rs.getString("tel"));
				dto.setAddr(rs.getString("addr"));
				dto.setBirth(rs.getString("birth"));
				dto.setJob(rs.getString("job"));
				dto.setGender(rs.getString("gender"));
				dto.setEmail(rs.getString("email"));
				dto.setIntro(rs.getString("intro"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}
	
	public Vector getMemberList() {
		Vector data = new Vector();
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			con = getConn();
			String sql = "SELECT * FROM tb_staff, tb_staff_info WHERE tb_staff.id = tb_staff_info.id";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String tel = rs.getString("tel");
				String addr = rs.getString("addr");
				String birth = rs.getString("birth");
				String job = rs.getString("job");
				String gender = rs.getString("gender");
				String email = rs.getString("email");
				String intro = rs.getString("intro");
				
				Vector row = new Vector();
				row.add(id);
				row.add(pwd);
				row.add(name);
				row.add(tel);
				row.add(addr);
				row.add(birth);
				row.add(job);
				row.add(gender);
				row.add(email);
				row.add(intro);
				
				data.add(row);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public boolean insertMember(MemberDTO dto) {
		boolean ok = false;
		
		Connection con = null;
		PreparedStatement ps = null, ps2 = null;
		
		try {
			con = getConn();
			String sql = "INSERT INTO tb_staff(id, pwd, name) VALUES(?, ?, ?)";
			
			ps = con.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getPwd());
			ps.setString(3, dto.getName());
			
			int r = ps.executeUpdate();
			
			String sql2 = "INSERT INTO tb_staff_info(id, tel, addr, birth, job, gender, email, intro) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
			ps2 = con.prepareStatement(sql2);
			ps2.setString(1, dto.getId());
			ps2.setString(2, dto.getTel());
			ps2.setString(3, dto.getAddr());
			ps2.setString(4, dto.getBirth());
			ps2.setString(5, dto.getJob());
			ps2.setString(6, dto.getGender());
			ps2.setString(7, dto.getEmail());
			ps2.setString(8, dto.getIntro());
			
			int r2 = ps2.executeUpdate();
			
			if(r>0 && r2>0) {
				ok = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ok;
	}
	
	public boolean updateMember(MemberDTO vMem) {
		boolean ok = false;
		Connection con = null;
		PreparedStatement ps = null, ps2 = null;
		
		try {
			con = getConn();
			String sql = "UPDATE tb_staff SET name=? WHERE id=? AND pwd=?";
			
			ps = con.prepareStatement(sql);
			
			ps.setString(1, vMem.getName());
			ps.setString(2, vMem.getId());
			ps.setString(3, vMem.getPwd());
			
			int r = ps.executeUpdate();
			
			String sql2 = "UPDATE tb_staff_info SET tel=?, addr=?, birth=?, job=?, gender=?, email=?, intro=? WHERE id=? AND EXISTS(SELECT pwd FROM tb_staff WHERE tb_staff.pwd = ?)";
			
			ps2 = con.prepareStatement(sql2);
			
			ps2.setString(1, vMem.getTel());
			ps2.setString(2, vMem.getAddr());
			ps2.setString(3, vMem.getBirth());
			ps2.setString(4, vMem.getJob());
			ps2.setString(5, vMem.getGender());
			ps2.setString(6, vMem.getEmail());
			ps2.setString(7, vMem.getIntro());
			ps2.setString(8, vMem.getId());
			ps2.setString(9, vMem.getPwd());
			
			int r2 = ps2.executeUpdate();
			
			if(r>0 && r2>0) {
				ok = true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return ok;
	}
	
	public boolean deleteMember(String id, String pwd) {
		boolean ok = false;
		Connection con = null;
		PreparedStatement ps = null, ps2 = null;
		
		try {
			con = getConn();
			String sql2 = "DELETE FROM tb_staff_info WHERE id=? AND EXISTS(SELECT pwd FROM tb_staff WHERE tb_staff.pwd = ?)";
			ps2 = con.prepareStatement(sql2);
			ps2.setString(1, id);
			ps2.setString(2, pwd);
			int r2 = ps2.executeUpdate();
			
			String sql = "DELETE FROM tb_staff WHERE id=? AND pwd=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pwd);
			int r = ps.executeUpdate();
			
			if(r > 0 && r2>0)
				ok = true;
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return ok;
	}
}
