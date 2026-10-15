package member.kimkc.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import member.domain.MemberDTO;
import util.security.AES256;
import util.security.SecretMyKey;

public class MemberDAO_imple implements MemberDAO {

	private DataSource ds; // DataSource ds 가 DBCP(DB Connection Pool)이다. 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private AES256 aes;
	
	// 생성자
	public MemberDAO_imple() {
		
		try {
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
		    ds = (DataSource)envContext.lookup("jdbc/myoracle");
		    // lookup()속에 /Hi-shoes/src/main/webapp/META-INF/context.xml에 지정한 이름을 적어준다. 
		    
		    aes = new AES256(SecretMyKey.KEY);
		    // SecretMyKey.KEY 은 우리가 만든 암호화/복호화 키이다.
		    
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
	
	// 사용한 자원을 반납하는 close() 메소드 생성하기 
	private void close() {
		try {
			if(rs != null)    {rs.close();    rs=null;}
			if(pstmt != null) {pstmt.close(); pstmt=null;}
			if(conn != null)  {conn.close();  conn=null;}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}


	
	// 구매자 정보 들고오기
	@Override
	public MemberDTO getMemberInfo(String userid) throws SQLException {
		MemberDTO member = new MemberDTO();
		
		try {
			conn = ds.getConnection();
			
			String sql = " select userid, email, mobile, postcode, address, detailaddress, extraaddress, company "
					+ " from tbl_member where userid = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			
			rs = pstmt.executeQuery();
			rs.next();
			
			member.setUserid(rs.getString("userid"));
			member.setEmail(aes.decrypt(rs.getString("email")));
			member.setMobile(aes.decrypt(rs.getString("mobile")));
			member.setPostcode(String.valueOf(rs.getInt("postcode")));
			member.setAddress(rs.getString("address"));
			member.setDetailaddress(rs.getString("detailaddress"));
			member.setExtraaddress(rs.getString("extraaddress"));
			member.setCompany(rs.getString("company"));
			
			
		} catch(UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		}  finally {
			close();
		}
		
		return member;
	}
	
	//==============================================================================================================================
	
	
	
	
	
	

}
