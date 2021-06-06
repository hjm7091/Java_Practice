package management.program.member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

@SuppressWarnings({"rawtypes","unchecked"})
public class MemberDAO {
    private static final String DRIVER = "org.mariadb.jdbc.Driver";
    private static final String URL = "jdbc:mariadb://127.0.0.1:3306/database_programming";
    private static final String PASS = "system";
    private static final String USER = "root";

    MemberList mList;

    public Connection getConn() {
        Connection con = null;

        try {
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL,USER,PASS);
        } catch(Exception e) {
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
                dto.setPwd(rs.getString("pwd"));
                dto.setName(rs.getString("name"));
                dto.setTel(rs.getString("tel"));
                dto.setAddress(rs.getString("address"));
                dto.setBirth(rs.getString("birth"));
                dto.setJob(rs.getString("job"));
                dto.setGender(rs.getString("gender"));
                dto.setEmail(rs.getString("email"));
                dto.setIntro(rs.getString("intro"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            closeResources(con, rs, ps);
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
                String address = rs.getString("address");
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
                row.add(address);
                row.add(birth);
                row.add(job);
                row.add(gender);
                row.add(email);
                row.add(intro);

                data.add(row);
            }
        } catch(Exception e) {
            e.printStackTrace();
            closeResources(con, rs, ps);
        }
        return data;
    }

    public boolean insertMember(MemberDTO dto) {
        boolean ok = false;

        Connection con = null;
        PreparedStatement ps1 = null, ps2 = null;

        try {
            con = getConn();
            String sql = "INSERT INTO tb_staff(id, pwd, name) VALUES(?, ?, ?)";

            ps1 = con.prepareStatement(sql);
            ps1.setString(1, dto.getId());
            ps1.setString(2, dto.getPwd());
            ps1.setString(3, dto.getName());

            int r = ps1.executeUpdate();

            String sql2 = "INSERT INTO tb_staff_info(id, tel, address, birth, job, gender, email, intro) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            ps2 = con.prepareStatement(sql2);
            ps2.setString(1, dto.getId());
            ps2.setString(2, dto.getTel());
            ps2.setString(3, dto.getAddress());
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
            closeResources(con, null, ps1, ps2);
        }

        return ok;
    }

    public boolean updateMember(MemberDTO vMem) {
        boolean ok = false;
        Connection con = null;
        PreparedStatement ps1 = null, ps2 = null;

        try {
            con = getConn();
            String sql = "UPDATE tb_staff SET name=? WHERE id=? AND pwd=?";

            ps1 = con.prepareStatement(sql);

            ps1.setString(1, vMem.getName());
            ps1.setString(2, vMem.getId());
            ps1.setString(3, vMem.getPwd());

            int r = ps1.executeUpdate();

            String sql2 = "UPDATE tb_staff_info SET tel=?, address=?, birth=?, job=?, gender=?, email=?, intro=? WHERE id=? AND EXISTS(SELECT pwd FROM tb_staff WHERE tb_staff.pwd = ?)";

            ps2 = con.prepareStatement(sql2);

            ps2.setString(1, vMem.getTel());
            ps2.setString(2, vMem.getAddress());
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
            closeResources(con, null, ps1, ps2);
        }

        return ok;
    }

    public boolean deleteMember(String id, String pwd) {
        boolean ok = false;
        Connection con = null;
        PreparedStatement ps1 = null, ps2 = null;

        try {
            con = getConn();
            String sql2 = "DELETE FROM tb_staff_info WHERE id=? AND EXISTS(SELECT pwd FROM tb_staff WHERE tb_staff.pwd = ?)";
            ps2 = con.prepareStatement(sql2);
            ps2.setString(1, id);
            ps2.setString(2, pwd);
            int r2 = ps2.executeUpdate();

            String sql = "DELETE FROM tb_staff WHERE id=? AND pwd=?";
            ps1 = con.prepareStatement(sql);
            ps1.setString(1, id);
            ps1.setString(2, pwd);
            int r = ps1.executeUpdate();

            if(r > 0 && r2 > 0)
                ok = true;

        } catch(SQLException e) {
            e.printStackTrace();
            closeResources(con, null, ps1, ps2);
        }
        return ok;
    }

    private void closeResources(Connection con, ResultSet rs, PreparedStatement... ps) {
        try {
            if (con != null) con.close();
            if (rs != null) rs.close();
            for (PreparedStatement p : ps)
                if (p != null) p.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
