package notice.km.model;

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

import notice.km.domain.NoticeDTO;
import util.security.AES256;
import util.security.SecretMyKey;

public class NoticeDAO_imple implements NoticeDAO {

	private DataSource ds; // DataSource ds 가 DBCP(DB Connection Pool)이다. 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private AES256 aes;
	
	// 생성자
	public NoticeDAO_imple() {
		
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
	
	// 공지사항 게시글 불러오기
	@Override
	public List<NoticeDTO> select_notice_list(Map<String, String> paraMap) throws Exception {
		List<NoticeDTO> noticeList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " select nnum, nsubject, ncontents, nwritedate, nimage "
					  +  " from tbl_notice ";
			
			String colname = paraMap.get("searchType");
			String searchWord = paraMap.get("searchWord");
			
			if(!"".equals(colname) && !"".equals(searchWord)) {
				// 검색대상 및 검색어가 있는 경우
				
				if("subject".equals(colname)) {
					sql += " where nsubject like '%'|| ? ||'%' ";
					// 컬럼명과 테이블명은 위치홀더(?)로 사용하면 안된다.!!!!
				}
				else if("content".equals(colname)) {
					sql += " where ncontents like '%'|| ? ||'%' ";
				}
				
			}// end of if(!"".equals(colname) && !"".equals(searchWord))
			
			sql += " order by nnum desc "
				 + " offset(?-1)*10 row "
				 + " fetch next 10 row only ";
			
			   	    
			
			pstmt = conn.prepareStatement(sql);
			
			int currentShowPageNo = Integer.parseInt(paraMap.get("currentShowPageNo"));
			
			if(!"".equals(colname) && !"".equals(searchWord)) {
				// 검색대상 및 검색어가 있는 경우
				pstmt.setString(1, searchWord);
				pstmt.setInt(2, currentShowPageNo);
			}
			
			pstmt.setInt(1, currentShowPageNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				NoticeDTO ndto = new NoticeDTO();
				
				ndto.setNnum(rs.getInt("nnum"));
				ndto.setNsubject(rs.getString("nsubject"));
				ndto.setNcontents(rs.getString("ncontents"));
				ndto.setNwritedate(rs.getString("nwritedate"));
				ndto.setNimage(rs.getString("nimage"));
				
				noticeList.add(ndto);
				
			}// end of while(rs.next())
					
			
		}finally {
			
			close();
		}
		
		
		return noticeList;
	}

	// === 전체 페이지 개수 ===
	@Override
	public int getTotalCountOrder() throws Exception {

		int totalCountOrder = 0;
		   
		   try {
			   conn = ds.getConnection();
			   
			   String sql = " select count(*) as CNT "
			   			  + " From tbl_notice ";
				   
			   pstmt = conn.prepareStatement(sql);
		   
			   rs = pstmt.executeQuery();
			   
			   rs.next();
			   
			   totalCountOrder = rs.getInt("CNT");
			
		   } finally {
			   close();
		   }
		   
		   return totalCountOrder;
	
	}// end of public int getTotalCountOrder() throws Exception

	// 공지사항에 넣어주기
	@Override
	public int noticeInsert(NoticeDTO ndto) throws Exception {
		int n = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " insert into tbl_notice (nnum, nsubject, ncontents, nimage) "
					  +  " values (SEQ_NNUM.nextval,?,?,?) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, ndto.getNsubject());
			pstmt.setString(2, ndto.getNsubject());
			pstmt.setString(3, ndto.getNimage());
			
			n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return n;
	}

}
