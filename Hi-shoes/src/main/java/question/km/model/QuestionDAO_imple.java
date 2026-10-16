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
	
	// 생성자
	public QuestionDAO_imple() {
		
		try {
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
		    ds = (DataSource)envContext.lookup("jdbc/myoracle");
		    // lookup()속에 /MyMVC/src/main/webapp/META-INF/context.xml에 지정한 이름을 적어준다. 
		    
		} catch (NamingException e) {
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
	
		    String sql = " select qnanum, fk_pname, fk_userid, qtitle, "
		               + " qcontents, qwritedate, qanswer "
		               + " from tbl_qna q join tbl_product p "
		               + " on q.fk_pnum = p.pnum ";
	
		    String colname = paraMap.get("searchType");
		    String searchWord = paraMap.get("searchWord");
	
		    boolean hasSearch = colname != null
		                     && searchWord != null
		                     && !"".equals(searchWord.trim())
		                     && ("pname".equals(colname)
		                         || "userid".equals(colname));
	
		    if(hasSearch) {
		        // 검색대상 및 검색어가 있는 경우
	
		        if("pname".equals(colname)) {
		            sql += " where fk_pname like '%'|| ? ||'%' ";
		        }
		        else if("userid".equals(colname)) {
		            sql += " where fk_userid like '%'|| ? ||'%' ";
		        }
		    }
	
		    sql += " order by qnanum desc "
		         + " offset (?-1)*10 row "
		         + " fetch next 10 row only ";
	
		    pstmt = conn.prepareStatement(sql);
	
		    int currentShowPageNo = Integer.parseInt(paraMap.get("currentShowPageNo"));
	
		    int idx = 1;
	
		    if(hasSearch) {
		        pstmt.setString(idx++, searchWord.trim());
		    }
	
		    pstmt.setInt(idx++, currentShowPageNo);
	
		    rs = pstmt.executeQuery();
	
		    while(rs.next()) {
	
		        Map<String, String> qnaMap = new HashMap<>();
	
		        qnaMap.put("qnanum",String.valueOf(rs.getInt("qnanum")));
		        qnaMap.put("fk_pname",rs.getString("fk_pname"));
		        qnaMap.put("qtitle",rs.getString("qtitle"));
		        qnaMap.put("fk_userid",rs.getString("fk_userid"));
		        qnaMap.put("qcontents",rs.getString("qcontents"));
		        qnaMap.put("qwritedate",rs.getString("qwritedate"));
		        qnaMap.put("qanswer",rs.getString("qanswer"));
	
		        questionList.add(qnaMap);

	    }// end of while(rs.next())

		} finally {
		    close();
		}

	return questionList;
}
	
	// 유저 문의사항 게시글 불러오기
		@Override
		public List<Map<String,String>> select_user_question_list(Map<String, String> paraMap) throws Exception {
			List<Map<String, String>> questionList = new ArrayList<>();

		    try {
		        conn = ds.getConnection();

		        String sql = " select q.qnanum, p.fk_pname, q.qtitle, "
		                   + "        q.qcontents, to_char(q.qwritedate, 'yyyy-mm-dd') as qwritedate, q.qanswer, q.islock"
		                   + " from tbl_qna q "
		                   + " join tbl_product p "
		                   + " on q.fk_pnum = p.pnum "
		                   + " where q.fk_userid = ? ";

		        String colname = paraMap.get("searchType");
		        String searchWord = paraMap.get("searchWord");

		        boolean hasSearch = "pname".equals(colname)
		                         && searchWord != null
		                         && !searchWord.isBlank();

		        if (hasSearch) {
		            sql += " and p.fk_pname like '%' || ? || '%' ";
		        }

		        sql += " order by q.qnanum desc "
		             + " offset ? rows "
		             + " fetch next 10 rows only ";

		        pstmt = conn.prepareStatement(sql);

		        int idx = 1;

		        // 로그인한 회원의 문의만 조회
		        pstmt.setString(idx++, paraMap.get("userid"));

		        // 상품명 검색어
		        if (hasSearch) {
		            pstmt.setString(idx++, searchWord);
		        }

		        // 페이지 번호를 실제 건너뛸 행 개수로 변환
		        int currentShowPageNo =
		                Integer.parseInt(paraMap.get("currentShowPageNo"));

		        pstmt.setInt(idx++, (currentShowPageNo - 1) * 10);

		        rs = pstmt.executeQuery();

		        while (rs.next()) {
		            Map<String, String> qnaMap = new HashMap<>();

		            qnaMap.put("qnanum",
		                    String.valueOf(rs.getInt("qnanum")));
		            qnaMap.put("fk_pname", rs.getString("fk_pname"));
		            qnaMap.put("qtitle", rs.getString("qtitle"));
		            qnaMap.put("qcontents", rs.getString("qcontents"));
		            qnaMap.put("qwritedate", rs.getString("qwritedate"));
		            qnaMap.put("qanswer", rs.getString("qanswer"));
		            qnaMap.put("islock", rs.getString("islock"));

		            questionList.add(qnaMap);
		        }

		    } finally {
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
		                   + " from tbl_qna q "
		                   + " join tbl_product p "
		                   + " on q.fk_pnum = p.pnum ";

		        String colname = paraMap.get("searchType");
		        String searchWord = paraMap.get("searchWord");

		        boolean hasSearch = colname != null
		                         && searchWord != null
		                         && !searchWord.trim().isEmpty()
		                         && ("pname".equals(colname)
		                             || "userid".equals(colname));

		        if (hasSearch) {
		            if ("pname".equals(colname)) {
		                sql += " where p.fk_pname like '%' || ? || '%' ";
		            }
		            else if ("userid".equals(colname)) {
		                sql += " where q.fk_userid like '%' || ? || '%' ";
		            }
		        }

		        pstmt = conn.prepareStatement(sql);

		        if (hasSearch) {
		            pstmt.setString(1, searchWord.trim());
		        }

		        rs = pstmt.executeQuery();

		        if (rs.next()) {
		            totalCountOrder = rs.getInt("CNT");
		        }

		    } finally {
		        close();
		    }

		    return totalCountOrder;
	}
	
	// === 유저 전체 페이지 개수 ===
		@Override
		public int getTotalUserCountOrder(Map<String, String> paraMap) throws Exception {
			
			int totalCountOrder = 0;

		    try {
		        conn = ds.getConnection();

		        String sql = " select count(*) as CNT "
		                   + " from tbl_qna q "
		                   + " join tbl_product p "
		                   + " on q.fk_pnum = p.pnum "
		                   + " where q.fk_userid = ? ";

		        String colname = paraMap.get("searchType");
		        String searchWord = paraMap.get("searchWord");

		        boolean hasSearch = "pname".equals(colname)
		                         && searchWord != null
		                         && !searchWord.isBlank();

		        if (hasSearch) {
		            sql += " and p.fk_pname like '%' || ? || '%' ";
		        }

		        pstmt = conn.prepareStatement(sql);

		        int idx = 1;

		        pstmt.setString(idx++, paraMap.get("userid"));

		        if (hasSearch) {
		            pstmt.setString(idx++, searchWord);
		        }

		        rs = pstmt.executeQuery();

		        if (rs.next()) {
		            totalCountOrder = rs.getInt("CNT");
		        }

		    } finally {
		        close();
		    }

		    return totalCountOrder;
		}
	
	// 하나 불러오기
	@Override
	public Map<String, String> selectQuestionOne(String qnanum) throws Exception {

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
	              + " where q.qnanum = to_number(?) ";

	        pstmt = conn.prepareStatement(sql);

	        pstmt.setString(1, qnanum);

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

	// 문의사항 답변 작성하기
	@Override
	public int wirteAnswer(Map<String, String> paraMap) throws Exception {
		int n = 0;
		
		try {
			
			conn = ds.getConnection();

	        String sql = " update tbl_qna set qanswer = ? "
	                   + " where qnanum = ? ";

	        pstmt = conn.prepareStatement(sql);

	        pstmt.setString(1, paraMap.get("qanswer"));
	        pstmt.setInt(2, Integer.parseInt(paraMap.get("qnanum")));

	        n = pstmt.executeUpdate();
	        
		} finally {
			close();
		}
		
		return n;
	}

	// 문의사항 삭제
	@Override
	public int questionDelete(String num) throws Exception {
		int n = 0;
		try {
			
			conn = ds.getConnection();
			
			String sql = " delete from tbl_qna"
					   + " where qnanum = to_number(?) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, num);
			
			n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return n;
	}


}
