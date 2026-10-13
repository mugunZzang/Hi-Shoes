package member.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.jasper.tagplugins.jstl.core.Param;

import member.domain.MemberDTO;
import util.security.AES256;
import util.security.SecretMyKey;
import util.security.Sha256;

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
	
	// 회원가입을 해주는 메서드 (tbl_member 테이블에 insert)
	@Override
	public int registerMember(MemberDTO member) throws SQLException {
		int n = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " insert into tbl_member(userseq, userid, pwd, company, busiNum, email, mobile, postcode, address, detailaddress, extraaddress) "
					   + " values(seq_userseq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getUserid());
			pstmt.setString(2, Sha256.encrypt(member.getPwd())); // 암호를 SHA256 알고리즘으로 단방향 암호화 시킨다. 
			pstmt.setString(3, member.getCompany());
			pstmt.setString(4, member.getBusiNum());
			pstmt.setString(5, aes.encrypt(member.getEmail()));  // 이메일을 AES256 알고리즘으로 양방향 암호화 시킨다.
			pstmt.setString(6, aes.encrypt(member.getMobile())); // 휴대폰번호를 AES256 알고리즘으로 양방향 암호화 시킨다.			
			pstmt.setString(7, member.getPostcode());
			pstmt.setString(8, member.getAddress());
			pstmt.setString(9, member.getDetailaddress());
			pstmt.setString(10, member.getExtraaddress()); 			
			
			n = pstmt.executeUpdate();
			
		} catch(UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return n;
	}	// end of public int registerMember(MemberDTO member) throws SQLException------------

	// 사업자등록번호중복검사
	@Override
	public boolean busiNumDuplicateCheck(String busiNum) throws SQLException {
		boolean isExists = false;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select busiNum "
					   + " from tbl_member"
					   + " where busiNum = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, busiNum);
			
			rs = pstmt.executeQuery(); 
			
			isExists = rs.next(); // 행이 있으면(중복된 userid) true,
			                      // 행이 없으면(사용가능한 userid) false  
			
		} finally {
			close();
		}
		
		return isExists;
	}	// end of public boolean busiNumDuplicateCheck(String busiNum) throws SQLException-----------
	
	// ID 중복검사 (tbl_member 테이블에서 userid 가 존재하면 true 를 리턴해주고, userid 가 존재하지 않으면 false 를 리턴한다)
	@Override
	public boolean idDuplicateCheck(String userid) throws SQLException {
		boolean isExists = false;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select userid "
					   + " from tbl_member"
					   + " where userid = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			
			rs = pstmt.executeQuery(); 
			
			isExists = rs.next(); // 행이 있으면(중복된 userid) true,
			                      // 행이 없으면(사용가능한 userid) false  
			
		} finally {
			close();
		}
		
		return isExists;
	}	// end of public boolean idDuplicateCheck(String userid) throws SQLException-------------

	// email 중복검사 (tbl_member 테이블에서 email 이 존재하면 true 를 리턴해주고, email 이 존재하지 않으면 false 를 리턴한다)
	@Override
	public boolean emailDuplicateCheck(String email) throws SQLException {
		boolean isExists = false;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select email "
					   + " from tbl_member"
					   + " where email = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, aes.encrypt(email));
			
			rs = pstmt.executeQuery(); 
			
			isExists = rs.next(); // 행이 있으면(중복된 email) true,
			                      // 행이 없으면(사용가능한 email) false  
			
		} catch(UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return isExists;		
	}	// end of public boolean emailDuplicateCheck(String email) throws SQLException----------

	// 로그인 처리 
	@Override
	public MemberDTO login(Map<String, String> paraMap) throws SQLException {
		
		MemberDTO member = null;
		
		try {
			conn = ds.getConnection();
			
			String sql =  " WITH "
						+ " A AS "
						+ " (select userid, company, busiNum, "
						+ "         to_char(registerday, 'yyyy-mm-dd hh24:mi:ss') AS registerday, "
						+ "         idle, email, mobile, postcode, address, detailaddress, extraaddress, "
						+ "         TRUNC( months_between(sysdate, lastpwdchangedate) ) AS pwdchangegap "
						+ "  from tbl_member "
						+ "  where status = '가입중' and userid = ? and pwd = ? " 
						+ " ) "
						+ " , "
						+ " B AS "
						+ " (select TRUNC( months_between(sysdate, MAX(logindate)) ) AS lastlogingap " 
						+ "  from tbl_loginhistory "
						+ "  where fk_userid = ? "
						+ " ) "
						+ " SELECT userid, company, busiNum, registerday, "
						+ "        idle, email, mobile, postcode, address, detailaddress, extraaddress, "
						+ "        pwdchangegap, "
						+ "        lastlogingap "
						+ " FROM A CROSS JOIN B ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, paraMap.get("userid"));
			pstmt.setString(2, Sha256.encrypt(paraMap.get("pwd")) );
			pstmt.setString(3, paraMap.get("userid"));
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				member = new MemberDTO();
				
				member.setUserid(rs.getString("userid"));
				member.setCompany(rs.getString("company"));				
				member.setBusiNum(rs.getString("busiNum"));
				
				// LocalDate 입력시 rs.getObject("컬럼명", LocalDate.class) 로 한다. 
				
				member.setRegisterday(rs.getString("registerday"));
				
				member.setEmail(aes.decrypt(rs.getString("email")) );
				member.setMobile(aes.decrypt(rs.getString("mobile")) );
				
				member.setPostcode(rs.getString("postcode"));
				member.setAddress(rs.getString("address"));
				member.setDetailaddress(rs.getString("detailaddress")); 
				member.setExtraaddress(rs.getString("extraaddress"));
				
				if( rs.getInt("lastlogingap") >= 12 ) {
					// 마지막으로 로그인 한 날짜시간이 현재시각으로 부터 12개월(1년)이 지났으면 휴면으로 지정 
					member.setIdle("휴면중");
					
					if(rs.getString("idle") == "할동중") {
						// === tbl_member 테이블의 idle 컬럼의 값을 1로 변경하기 === //
						sql = " update tbl_member set idle = '휴면중' "
							+ " where userid = ? ";
						
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, paraMap.get("userid"));
						
						pstmt.executeUpdate();
					}
					
				}// end of if( rs.getInt("lastlogingap") >= 12 )------
				
				// === 휴면대상이 아닌 회원만 tbl_loginhistory(로그인기록) 테이블에 insert 하기 시작 === // 
				if( rs.getInt("lastlogingap") < 12 ) {
					sql = " insert into tbl_loginhistory(historyno, fk_userid, clientip) " 
						+ " values(seq_historyno.nextval, ?, ?) ";
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, paraMap.get("userid"));
					pstmt.setString(2, paraMap.get("clientip"));
					
					pstmt.executeUpdate();
				// === 휴면대상이 아닌 회원만 tbl_loginhistory(로그인기록) 테이블에 insert 하기 끝 === //
					
				// --- 휴면대상이 아닌 회원중에 마지막으로 비밀번호를 변경한 날짜가 현재시각으로 부터 6개월이 지났는지 알아봐서
			    //     6개월이 지난 경우라면 로그인시 비밀번호를 변경해라는 메시지가 출력되는 대상으로 만들어 주도록 한다.
					if( rs.getInt("pwdchangegap") >= 6 ) {
						member.setRequirePwdChange(true); 
						// 마지막으로 비밀번호를 변경한 날짜가 현재시각으로 부터 6개월이 지났으면 true
						// 마지막으로 비밀번호를 변경한 날짜가 현재시각으로 부터 6개월이 지나지 않았으면 false 
						// 로그인시 비밀번호를 변경해라는 alert 를 띄우도록 할때 사용한다. 
					}
					
				}// end of if( rs.getInt("lastlogingap") < 12 )--------------	
					
				
			}// end of if(rs.next())---------------------
			
		} catch(UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return member;
		
	}	// end of public MemberDTO login(Map<String, String> paraMap) throws SQLException------------


	// 아이디 찾기(사명, 이메일을 입력받아서 해당 사용자의 아이디를 알려준다) 
	@Override
	public String findUserid(Map<String, String> paraMap) throws SQLException{
		String userid = null;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select userid "
					   + " from tbl_member "
					   + " where status = '가입중' and company = ? and email = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("company"));
			pstmt.setString(2, aes.encrypt(paraMap.get("email")) );
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				userid = rs.getString("userid");
			}
			
		} catch(UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return userid;
	} // end of public String findUserid(Map<String, String> paraMap) throws SQLException-----------


	// 비밀번호 찾기(아이디, 이메일을 입력받아서 해당 사용자가 존재하는지 유무를 알려준다) 
	@Override
	public boolean isUserExist(Map<String, String> paraMap) throws SQLException {
		boolean isUserExist = false;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select userid "
					   + " from tbl_member "
					   + " where status = '가입중' and userid = ? and email = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("userid"));
			pstmt.setString(2, aes.encrypt(paraMap.get("email")) );
			
			rs = pstmt.executeQuery();
			
			isUserExist = rs.next();
			
		} catch(UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}		
		
		return isUserExist;
	}	// end of public boolean isUserExist(Map<String, String> paraMap) throws SQLException---------


	// 비밀번호 변경하기 
	@Override
	public int pwdUpdate(Map<String, String> paraMap) throws SQLException {
		int n = 0;
		
		try {
			conn = ds.getConnection();
			String sql = " update tbl_member set pwd = ?, lastpwdchangedate = sysdate "
						+ " where userid = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, Sha256.encrypt(paraMap.get("new_pwd")) );	//암호를 SHA256 알고리즘으로 단방향 암호화 시킨다.
			pstmt.setString(2, paraMap.get("userid"));
			
			n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return n;
		
	}	// end of public int pwdUpdate(Map<String, String> paraMap) throws SQLException---------
	

}
