package question.km.model;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import util.security.AES256;
import util.security.SecretMyKey;

public class QuestionDAO_imple implements QuestionDAO {

	private DataSource ds; // DataSource ds 가 DBCP(DB Connection Pool)이다. 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private AES256 aes;
	
	// 생성자
	public QuestionDAO_imple() {
		
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
	
	// 문의사항 게시글 불러오기
	@Override
	public List<Map<String,String>> select_question_list(Map<String, String> paraMap) throws Exception {
		List<Map<String,String>> questionList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " select qnanum, fk_pname, fk_userid, qcontents, qwritedate "
					   + " from tbl_qna q join tbl_product p "
					   + " on q.fk_pnum = pnum ";
			
			String colname = paraMap.get("searchType");
			String searchWord = paraMap.get("searchWord");
			
			if(!"".equals(colname) && !"".equals(searchWord)) {
				// 검색대상 및 검색어가 있는 경우
				
				if("pname".equals(colname)) {
					sql += " where fk_pname like '%'|| ? ||'%' ";
					// 컬럼명과 테이블명은 위치홀더(?)로 사용하면 안된다.!!!!
				}
				else if("userid".equals(colname)) {
					sql += " where fk_userid like '%'|| ? ||'%' ";
				}
				
			}// end of if(!"".equals(colname) && !"".equals(searchWord))
			
			sql += " order by qnanum desc "
				+  " offset(?-1)*10 row "
				+  " fetch next 10 row only ";
			
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
				
				Map<String, String> qnaMap = new HashMap<>();
				qnaMap.put("qnanum",String.valueOf(rs.getInt("qnanum")));
				qnaMap.put("fk_pname",rs.getString("fk_pname"));
				qnaMap.put("fk_userid",rs.getString("fk_userid"));
				qnaMap.put("qcontents",rs.getString("qcontents"));
				qnaMap.put("qwritedate",rs.getString("qwritedate"));
				
				questionList.add(qnaMap);
				
			}// end of while(rs.next())
					
			
		}finally {
			
			close();
		}
		
		
		return questionList;
	}

	// === 전체 페이지 개수 ===
	@Override
	public int getTotalCountOrder(Map<String, String> paraMap) throws Exception {
		
		int totalCountOrder = 0;
		   
		   try {
			   conn = ds.getConnection();
			   
			   String sql = " select count(*) as CNT "
			   			  + " From tbl_qna ";
				   
			   pstmt = conn.prepareStatement(sql);
		   
			   rs = pstmt.executeQuery();
			   
			   rs.next();
			   
			   totalCountOrder = rs.getInt("CNT");
			
		   } finally {
			   close();
		   }
		   
		   return totalCountOrder;
	}
	
	@Override
	public Map<String, String> selectQuestionOne(int qnanum) throws Exception {

	    Map<String, String> qnaMap = null;

	    try {

	        conn = ds.getConnection();

	        String sql =
	                " select q.qnanum, "
	              + "        q.fk_userid, "
	              + "        q.fk_pnum, "
	              + "        p.fk_pname, "
	              + "        q.qcontents, "
	              + "        q.qwritedate, "
	              + "        q.islock, "
	              + "        q.qanswer "
	              + " from tbl_qna q "
	              + " join tbl_product p "
	              + " on q.fk_pnum = p.pnum "
	              + " where q.qnanum = ? ";

	        pstmt = conn.prepareStatement(sql);

	        pstmt.setInt(1, qnanum);

	        rs = pstmt.executeQuery();

	        if(rs.next()) {

	            qnaMap = new HashMap<>();
	            qnaMap.put("qnanum",String.valueOf(rs.getInt("qnanum")));
	            qnaMap.put("fk_userid",rs.getString("fk_userid"));
	            qnaMap.put("fk_pnum",String.valueOf(rs.getInt("fk_pnum")));
	            qnaMap.put("fk_pname",rs.getString("fk_pname"));
	            qnaMap.put("qcontents",rs.getString("qcontents"));
	            qnaMap.put("qwritedate",rs.getString("qwritedate"));
	            qnaMap.put("islock",rs.getString("islock"));
	            qnaMap.put("qanswer",rs.getString("qanswer"));
	        }

	    }
	    finally {

	        close();

	    }

	    return qnaMap;
	}
	
	@Override
	public int questionAnswer(Map<String, String> paraMap) throws Exception {

	    int result = 0;

	    try {

	        conn = ds.getConnection();

	        String sql =
	                " update tbl_qna "
	              + " set qanswer = ? "
	              + " where qnanum = ? ";

	        pstmt = conn.prepareStatement(sql);

	        pstmt.setString(1, paraMap.get("qanswer"));
	        pstmt.setInt(2,
	                Integer.parseInt(paraMap.get("qnanum")));

	        result = pstmt.executeUpdate();

	    }
	    finally {

	        close();

	    }

	    return result;
	}


}
