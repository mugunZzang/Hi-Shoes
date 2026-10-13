package admin.kth.model;


import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
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

import admin.kth.model.AdminMainDAO;


public class AdminMainDAO_imple implements AdminMainDAO{

	private DataSource ds; // DataSource ds 가 DBCP(DB Connection Pool)이다. 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private AES256 aes;
	
	//생성자
	public AdminMainDAO_imple() {

		try {
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
		    ds = (DataSource)envContext.lookup("jdbc/myoracle");
		    // lookup()속에 /MyMVC/src/main/webapp/META-INF/context.xml에 지정한 이름을 적어준다. 
		    aes = new AES256(SecretMyKey.KEY);
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	//사용한 자원을 반납하는 close() 메소드 생성하기 
		private void close() {
			try {
				if(rs != null)    {rs.close();    rs=null;}
				if(pstmt != null) {pstmt.close(); pstmt=null;}
				if(conn != null)  {conn.close();  conn=null;}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		
	//주문건 계산하기
	@Override
	public int getOrderCount() throws SQLException {

	
	int count = 0;

	try {
	    conn = ds.getConnection();

	    String sql =  " select count(*) " 
	                + " from tbl_order "
	                + " where orderstatus = ? ";

	    pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1, "접수");
	    rs = pstmt.executeQuery();

	    if (rs.next()) {
	        count = rs.getInt(1);
	    }

	} finally {
	    close();
	}

	return count;


	}//end of public int getOrderCount() throws SQLException 
	
	
	//승인건 계산하기
	@Override
	public int getApproveCount() throws SQLException {

	
	int count = 0;

	try {
	    conn = ds.getConnection();

	    String sql = " select count(*) " +
	                 " from tbl_order " +
	                 " where orderstatus = ? ";

	    pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1, "상품 준비 중");

	    rs = pstmt.executeQuery();

	    if (rs.next()) {
	        count = rs.getInt(1);
	    }

	} finally {
	    close();
	}

	return count;
	

	}//end of public int getApproveCount() throws SQLException

	//출고건 계산하기
	@Override
	public int getDeliveryCount() throws SQLException {

	
	int count = 0;

	try {
	    conn = ds.getConnection();

	    String sql = " select count(*) " +
	                 " from tbl_order " +
	                 " where orderstatus = ? ";

	    pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1, "출고 완료");

	    rs = pstmt.executeQuery();

	    if (rs.next()) {
	        count = rs.getInt(1);
	    }

	} finally {
	    close();
	}

	return count;

	}//end of public int getDeliveryCount() throws SQLException
	
	
	   //오늘 하루 매출 건수 계산하기
		@Override
		public int getTodayDeliveryCount() throws SQLException {
			
			int count = 0;
            
			try {
			    conn = ds.getConnection();
				
				String sql= " SELECT COUNT(*) "
						  + " FROM tbl_order "
						  + " WHERE orderstatus = ? "
						  + " AND orderdate >= TRUNC(SYSDATE) "
						  + " AND orderdate < TRUNC(SYSDATE) + 1 ";
				
				
				pstmt = conn.prepareStatement(sql);
			    pstmt.setString(1, "출고 완료");

			    rs = pstmt.executeQuery();

			    if (rs.next()) {
			        count = rs.getInt(1);
			    }

			} finally {
			    close();
			}

			return count;
		}//end of public int getTodayDeliveryCount() throws SQLException
		
	
	   //오늘 하루 매출 수량 계산하기
		@Override
		public int getTodaySalesCount() throws SQLException {
			
			int count = 0;
                 
			try {
			    conn = ds.getConnection();
				
				String sql= " SELECT NVL(SUM(od.oqty), 0) AS salescount "
						  + " FROM tbl_order o "
						  + " JOIN tbl_orderdetail od "
						  + " ON o.onum = od.fk_onum "
						  + " WHERE orderstatus = ? "
						  + " AND orderdate >= TRUNC(SYSDATE) "
						  + " AND orderdate < TRUNC(SYSDATE) + 1 ";

				
				pstmt = conn.prepareStatement(sql);
			    pstmt.setString(1, "출고 완료");

			    rs = pstmt.executeQuery();

			    if (rs.next()) {
			        count = rs.getInt(1);
			    }

			} finally {
			    close();
			}

			return count;
		}//end of public int getTodaySalesCount() throws SQLException
		
		
		//오늘 하루 매출액 계산하기 
		@Override
		public int getTodaySalesSum() throws SQLException {
			
			int count = 0;
            
			try {
			    conn = ds.getConnection();
				
				String sql= " SELECT NVL(SUM(od.oqty * od.odrprice), 0) AS salessum "
						  + " FROM tbl_order o "
						  + " JOIN tbl_orderdetail od "
						  + " ON o.onum = od.fk_onum "
						  + " WHERE orderstatus = ? "
						  + " AND orderdate >= TRUNC(SYSDATE) "
						  + " AND orderdate < TRUNC(SYSDATE) + 1 ";

				
				pstmt = conn.prepareStatement(sql);
			    pstmt.setString(1, "출고 완료");

			    rs = pstmt.executeQuery();

			    if (rs.next()) {
			        count = rs.getInt(1);
			    }

			} finally {
			    close();
			}

			return count;
		}//end of public int getTodaySalesSum() throws SQLException


		// 재고현황 목록 조회하기
		@Override
		public List<Map<String, String>> getStockList() throws SQLException {

		    List<Map<String, String>> stockList = new ArrayList<>();

		    try {
		        conn = ds.getConnection();

		        String sql = " SELECT c.pname AS PNAME "
		                   + " , s.sqty AS SQTY "
		                   + " , CASE "
		                   + " WHEN s.sqty = 0 THEN '품절' "
		                   + " WHEN s.sqty <= 5 THEN '부족' "
		                   + " ELSE '정상' "
		                   + " END AS STOCKSTATUS "
		                   + " , s.ssize AS SSIZE "
		                   + " , s.color AS COLOR "
		                   + " FROM tbl_stock s "
		                   + " JOIN tbl_catalogue c "
		                   + " ON s.fk_pname = c.pname "
		                   + " ORDER BY DBMS_RANDOM.VALUE "
		                   + " FETCH FIRST 5 ROWS ONLY ";

		        pstmt = conn.prepareStatement(sql);

		        rs = pstmt.executeQuery();

		        while (rs.next()) {

					    Map<String, String> map = new HashMap<>();
					
					    map.put("PNAME", rs.getString("PNAME"));
					    map.put("SQTY", rs.getString("SQTY"));
					    map.put("STOCKSTATUS", rs.getString("STOCKSTATUS"));
					    map.put("SSIZE", rs.getString("SSIZE"));
					    map.put("COLOR", rs.getString("COLOR"));
					
					    stockList.add(map);
					}

		    } finally {
		        close();
		    }

		    return stockList;
		}//end of public List<Map<String, String>> getStockList() throws SQLException

		
		
		// 공지사항 목록 조회하기
		@Override
		public List<Map<String, String>> getNoticeList() throws SQLException {
			
		List<Map<String, String>> noticeList = new ArrayList<>();

		try {
		    conn = ds.getConnection();

		   String sql = " SELECT nsubject AS NSUBJECT "
		                   + "      , TO_CHAR(nwritedate, 'MM-DD') AS NWRITEDATE "
		                   + " FROM tbl_notice "
		                   + " ORDER BY nwritedate DESC "
		                   + " FETCH FIRST 6 ROWS ONLY ";

		    pstmt = conn.prepareStatement(sql);
		    rs = pstmt.executeQuery();

		    while (rs.next()) {
		        Map<String, String> map = new HashMap<>();

		        map.put("NSUBJECT", rs.getString("NSUBJECT"));
		        map.put("NWRITEDATE", rs.getString("NWRITEDATE"));

		        noticeList.add(map);
		    }

		} finally {
		    close();
		}

		return noticeList;
   }//end of public List<Map<String, String>> getNoticeList() throws SQLException

		
	
        //문의사항 목록 가져오기
		@Override
		public List<Map<String, String>> getQnaList() throws SQLException {
			List<Map<String, String>> qnaList = new ArrayList<>();

			try {
			    conn = ds.getConnection();

			   String sql = " SELECT qtitle AS QTITLE "  
			               + " , TO_CHAR(qwritedate, 'MM-DD') AS QWRITEDATE "
			               + " FROM tbl_qna "
			               + " ORDER BY qwritedate DESC "
			               + " FETCH FIRST 6 ROWS ONLY ";

			    pstmt = conn.prepareStatement(sql);
			    rs = pstmt.executeQuery();

			    while (rs.next()) {
			        Map<String, String> map = new HashMap<>();

			        map.put("QTITLE", rs.getString("QTITLE"));
			        map.put("QWRITEDATE", rs.getString("QWRITEDATE"));

			        qnaList.add(map);
			    }

			} finally {
			    close();
			}

			return qnaList;
		}//end of public List<Map<String, String>> getQnaList() throws SQLException
		
	
	



	

}
