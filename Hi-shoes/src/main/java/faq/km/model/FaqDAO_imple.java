package faq.km.model;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import faq.km.domain.FaqDTO;
import notice.km.domain.NoticeDTO;
import util.security.AES256;
import util.security.SecretMyKey;

public class FaqDAO_imple implements FaqDAO {

	private DataSource ds; // DataSource ds 가 DBCP(DB Connection Pool)이다. 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private AES256 aes;
	
	// 생성자
	public FaqDAO_imple() {
		
		try {
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
		    ds = (DataSource)envContext.lookup("jdbc/myoracle");
		    // lookup()속에 /MyMVC/src/main/webapp/META-INF/context.xml에 지정한 이름을 적어준다. 
		    
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
	
	// faq 게시글 불러오기
	@Override
	public List<FaqDTO> select_faq_list(Map<String, String> paraMap) throws Exception {
		List<FaqDTO> faqList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " select fnum, fsubject, fcontents, fcategory "
					  +  " from tbl_faq ";
			
			String searchType = paraMap.get("searchType");
			
			
			if(!"".equals(searchType)) {
				sql += " where fcategory = ? ";
			}
			
			sql += " order by fnum desc "
				 + " offset(?-1)*10 row "
				 + " fetch next 10 row only ";
			
			pstmt = conn.prepareStatement(sql);
			
			int currentShowPageNo = Integer.parseInt(paraMap.get("currentShowPageNo"));
			
			if(!"".equals(searchType)) {
				pstmt.setString(1, searchType);
				pstmt.setInt(2, currentShowPageNo);
			}
			
			pstmt.setInt(1, currentShowPageNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				FaqDTO fdto = new FaqDTO();
				
				fdto.setFnum(rs.getInt("fnum"));
				fdto.setFsubject(rs.getString("fsubject"));
				fdto.setFcontents(rs.getString("fcontents"));
				fdto.setFcategory(rs.getString("fcategory"));
				
				faqList.add(fdto);
				
			}// end of while(rs.next())
					
			
		}finally {
			
			close();
		}
		
		
		return faqList;
	}


	// === 전체 페이지 개수 ===
	@Override
	public int getTotalCountOrder() throws Exception {
		
		int totalCountOrder = 0;
		   
		   try {
			   conn = ds.getConnection();
			   
			   String sql = " select count(*) as CNT "
			   			  + " From tbl_faq ";
				   
			   pstmt = conn.prepareStatement(sql);
		   
			   rs = pstmt.executeQuery();
			   
			   rs.next();
			   
			   totalCountOrder = rs.getInt("CNT");
			
		   } finally {
			   close();
		   }
		   
		   return totalCountOrder;
	}// public int getTotalCountOrder() throws Exception


	// faq 입력해주기
	@Override
	public int faqInsert(FaqDTO fdto) throws Exception {
		int n = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " insert into tbl_faq (fnum, fsubject, fcontents, fcategory) "
					  +  " values (SEQ_fNUM.nextval,?,?,?) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, fdto.getFsubject());
			pstmt.setString(2, fdto.getFsubject());
			pstmt.setString(3, fdto.getFcategory());
			
			n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return n;
	}

}
