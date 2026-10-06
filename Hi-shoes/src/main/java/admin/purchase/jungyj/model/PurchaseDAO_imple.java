package admin.purchase.jungyj.model;

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

public class PurchaseDAO_imple implements PurchaseDAO {

	
	private DataSource ds;                // DataSource ds 는 DBCP(Database Connection Pool) 이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private AES256 aes;
	
	// 생성자
	public PurchaseDAO_imple() {
		try {
			Context initContext = new InitialContext();
	        Context envContext  = (Context)initContext.lookup("java:/comp/env");
	        ds = (DataSource)envContext.lookup("jdbc/myoracle");
	        // lookup()속에 /MyMVC/src/main/webapp/META-INF/context.xml에 지정한 이름을 적어주면 자동 주입됩니다.

	        aes = new AES256(SecretMyKey.KEY);
		} catch (NamingException e) {
			e.printStackTrace();
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		
	}
	
	// method
	
	// 사용한 자원을 반납하는 close() 메소드 생성하기 
    private void close() {
       try {
          if(rs != null)    {rs.close();    rs=null;}
          if(pstmt != null) {pstmt.close(); pstmt=null;}
          if(conn != null)  {conn.close();  conn=null;}
       } catch(SQLException e) {
          e.printStackTrace();
       }
    } // end of private void close()---------------

	
    

	// 발주 전체 저장 메서드
	@Override
	public int purchaseAdd(Map<String, Object> paraMap) throws SQLException {
		int result = 0;
		
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);   // 수동 커밋
			
	        String[] catalogueName_arr = (String[]) paraMap.get("catalogueName_arr");
	        String[] size_arr          = (String[]) paraMap.get("size_arr");
	        String[] color_arr         = (String[]) paraMap.get("color_arr");
	        String[] qty_arr           = (String[]) paraMap.get("qty_arr");
	        String[] price_arr         = (String[]) paraMap.get("price_arr");
	        
	        // === 1. 발주번호 채번(select) === //
	        String sql = " SELECT seq_purnum.nextval FROM dual ";
	        pstmt = conn.prepareStatement(sql);
	        rs = pstmt.executeQuery();
	        rs.next();
	        int purNum = rs.getInt(1);
//	        System.out.println("~~~ 확인용 purNum : " +purNum);
	        rs.close();
	        pstmt.close();
	        
	        // === 2. 발주 insert === //
	        sql = " INSERT INTO tbl_purchase(purnum, fk_supname,  purdeadline)"
	        	+ " VALUES(?, ?, sysdate + 7)";
	        
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, purNum);
	        pstmt.setString(2, (String) paraMap.get("supname"));
	        
	        int n1 = pstmt.executeUpdate();
//	        System.out.println("~~~ 확인용 n1 : " +n1);

	        pstmt.close();
	        
	        // === 3. 발주상세 insert (행마다 반복) === //
	        int n2 = 0;
	        
	        for(int i = 0; i < catalogueName_arr.length; i++) {
	        	
	            int ssize = Integer.parseInt(size_arr[i]);  // 신발 사이즈
	            int sNum = 0;
	            
	            // 3-1 재고 테이블에서 제품번호 조회
	            sql = " SELECT snum FROM tbl_stock "
	            	+ " WHERE fk_pname = ? AND ssize = ? AND color = ? ";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, catalogueName_arr[i]);
	            pstmt.setInt(2, ssize);
	            pstmt.setString(3, color_arr[i]);
	            
	            rs = pstmt.executeQuery();
	            
	            if(rs.next()) {
	            	// 발주하려는 제품명의 사이즈와 색상이 재고테이블에 있는 경우
	            	sNum = rs.getInt(1);
	            	rs.close();
	            	pstmt.close();
	            } else {
	            	// 발주하려는 제품명의 사이즈와 색상이 재고테이블에 없는 경우
	                rs.close();
	                pstmt.close();
	                
	                // 3-1-1 재고 채번
	                sql = " SELECT seq_snum.nextval FROM DUAL ";
	                pstmt = conn.prepareStatement(sql);
	                rs = pstmt.executeQuery();
	                
	                rs.next();
	                sNum = rs.getInt(1);
	                rs.close();
	                pstmt.close();
	                
	                // 3-1-2 재고 INSERT (재고량 0)
	                sql = " INSERT INTO tbl_stock(snum, fk_pname, ssize, color, sqty) "
	                    + " VALUES (?, ?, ?, ?, 0)";
	            	pstmt = conn.prepareStatement(sql);
	            	pstmt.setInt(1, sNum);
	            	pstmt.setString(2, catalogueName_arr[i]);
	            	pstmt.setInt(3, ssize);
	            	pstmt.setString(4, color_arr[i]);
	            	
	            	pstmt.executeUpdate();
	            	
	            	pstmt.close();
	            }
	            
	            // 3-2 발주상세 INSERT
	            sql = " INSERT INTO tbl_purdetail(purdetailnum, fk_purnum, fk_snum, purqty, purdprice) "
	            	+ " VALUES(seq_purdetailnum.nextval, ?, ?, ?, ?) ";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setInt(1, purNum);
	            pstmt.setInt(2, sNum);
	            pstmt.setInt(3, Integer.parseInt(qty_arr[i]));
	            pstmt.setInt(4, Integer.parseInt(price_arr[i]));
	            
	            n2 += pstmt.executeUpdate();
	            pstmt.close();
	        
	        }
	        // === 4. 모두 성공 시 commit, 아니면 rollback === //
	        if (n1 == 1 && n2 == catalogueName_arr.length) {
	        	System.out.println("대박 ^_^ 전부 성공");
	            conn.commit();
	            paraMap.put("purchaseNo", purNum);   // 컨트롤러로 발주번호 전달
	            result = 1;
	        }
	        else {
	        	System.out.println("꾸웩 어디선가 실패... 로그 확인 필요");
	            conn.rollback();
	        }
		}  catch (SQLException | NumberFormatException e) {
	        e.printStackTrace();
	        if (conn != null) conn.rollback();
	        result = 0;
	    } finally {
	    	if (conn != null) conn.setAutoCommit(true);   // 커넥션 풀이면 원복
			close();
		}
		
		return result;
	} // end of public int purchaseAdd(Map<String, Object> paraMap) throws SQLException-----------------------

	
	// 발주 + 공급업체 1건 검색
	@Override
	public Map<String, String> selectPurchase(String purnum) throws SQLException {
		Map<String, String> purchaseMap = new HashMap<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " SELECT P.purnum, to_char(P.purtime,'yyyy-mm-dd') AS purtime,  "
					   + "        S.supname, S.sbusinum, S.ceo, S.smobile, S.semail  "
					   + " FROM tbl_purchase P JOIN tbl_supplier S ON P.fk_supname = S.supname  "
					   + " WHERE P.purnum = TO_NUMBER(?)  ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, purnum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				purchaseMap.put("purnum", String.valueOf(rs.getInt("purnum")));
				purchaseMap.put("purtime", rs.getString("purtime"));
				purchaseMap.put("supname", rs.getString("supname"));
				purchaseMap.put("sbusinum", String.valueOf(rs.getInt("sbusinum")));
				purchaseMap.put("ceo", rs.getString("ceo"));
				purchaseMap.put("smobile", aes.decrypt(rs.getString("smobile")));
				purchaseMap.put("semail", aes.decrypt(rs.getString("semail")));
				
			}
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
		return purchaseMap;
	} // end of public Map<String, String> selectPurchase(String punum) throws SQLException-----------------------

	// 발주 상세 N 건 조회
	@Override
	public List<Map<String, String>> selectPurchaseDetail(String purnum) throws SQLException {
		List<Map<String, String>> purchaseDetailList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " SELECT P.purdetailnum, P.purqty, P.purdprice,  "
					   + "		                   P.purqty * P.purdprice AS amount, "
					   + "       S.color, S.ssize, S.fk_pname "
					   + " FROM tbl_purdetail P INNER JOIN tbl_stock S "
					   + " ON P.fk_snum = S.snum "
					   + " WHERE fk_purnum = TO_NUMBER(?)  "
					   + " ORDER BY purdetailnum  ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, purnum);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Map<String, String> map = new HashMap<>(); 
				map.put("purdetailnum", String.valueOf(rs.getInt("purdetailnum")));
				map.put("purqty", String.valueOf(rs.getInt("purqty")));
				map.put("purdprice", String.valueOf(rs.getInt("purdprice")));
				map.put("amount", String.valueOf(rs.getInt("amount")));
				map.put("color", rs.getString("color"));
				map.put("ssize", String.valueOf(rs.getInt("ssize")));
				map.put("fk_pname", rs.getString("fk_pname"));
				
				purchaseDetailList.add(map);
			}
		} finally {
			close();
		}
		
		return purchaseDetailList;
	} // end of public List<Map<String, String>> selectPurchaseDetail(String purnum) throws SQLException-----------

}
