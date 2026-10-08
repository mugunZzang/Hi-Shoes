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

    // 공통 WHERE 생성 (count / list 둘 다 사용)
    private String buildPurchaseWhere(Map<String, String> paraMap, List<String> values) {

        List<String> conditions = new ArrayList<>();

        String supname   = paraMap.get("supname");
        String startDate = paraMap.get("startDate");
        String endDate   = paraMap.get("endDate");
        String status = paraMap.get("status");   // 입고 상태 컬럼

        
        if (supname != null && !supname.trim().isEmpty()) {
            conditions.add(" p.fk_supname = ? ");
            values.add(supname);
        }

        if (startDate != null && !startDate.trim().isEmpty()) {
            conditions.add(" p.purtime >= TO_DATE(?, 'yyyy-mm-dd') ");
            values.add(startDate);
        }

        if (endDate != null && !endDate.trim().isEmpty()) {
            // 종료일 당일 포함: 종료일 + 1일 미만
            conditions.add(" p.purtime < TO_DATE(?, 'yyyy-mm-dd') + 1 ");
            values.add(endDate);
        }

        if ("wait".equals(status)) {
        	// 납품기한까지 남아있고, 현재 미입고 상태
            conditions.add(" p.instock = '미입고' AND p.purdeadline >= TRUNC(SYSDATE) ");
        } else if ("done".equals(status)) {
        	// 입고처리가 완료된 건
            conditions.add(" p.instock = '입고' ");
        } else if ("delay".equals(status)) {
        	// 기한이 지났지만 입고가 완료되지 않은 건
            conditions.add(" p.instock = '미입고' AND p.purdeadline < TRUNC(SYSDATE) ");
        }
        
        return conditions.isEmpty() ? "" : " WHERE " + String.join(" AND ", conditions);
    }
    

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

	// 발주 목록 조회(SELECT)
	@Override
	public List<Map<String, String>> selectPurchaseList() throws SQLException {
		List<Map<String, String>> purchase_map_list = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " SELECT "
					   + "    p.purnum, "
					   + "    p.fk_supname, "
					   + "    TO_CHAR(p.purtime, 'yyyy-mm-dd') AS purtime, "
					   + "    TO_CHAR(p.purdeadline, 'yyyy-mm-dd') AS purdeadline, "
					   + "    p.instock, "
					   + "    COUNT(pd.purdetailnum) AS product_count, "
					   + "    SUM(pd.purqty) AS total_quantity "
					   + " FROM tbl_purchase p "
					   + " JOIN tbl_purdetail pd "
					   + "    ON p.purnum = pd.fk_purnum "
					   + " GROUP BY "
					   + "    p.purnum, "
					   + "    p.fk_supname, "
					   + "    p.purtime, "
					   + "    p.purdeadline, "
					   + "    p.instock "
					   + " ORDER BY p.purtime DESC ";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Map<String, String> map = new HashMap<>();
				
				map.put("purnum", String.valueOf(rs.getInt("purnum")));
				map.put("fk_supname", rs.getString("fk_supname"));
				map.put("purtime", rs.getString("purtime"));
				map.put("purdeadline", rs.getString("purdeadline"));
				map.put("instock", rs.getString("instock"));
				map.put("product_count", String.valueOf(rs.getInt("product_count")));
				map.put("total_quantity", String.valueOf(rs.getInt("total_quantity")));
				
				purchase_map_list.add(map);
			}
		} finally {
			close();
		}
		
		return purchase_map_list;
	} // end of public List<Map<String, String>> selectPurchaseList() throws SQLException-----------------------

	
    // 전체 발주 개수
	@Override
	public int getTotalCountPurchase(Map<String, String> paraMap) throws SQLException {
		int totalCountPurchase = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " SELECT COUNT(*) AS TOTALPURCHASECOUNT"
					   + " FROM tbl_purchase p ";
			
			List<String> values = new ArrayList<>();
			sql += buildPurchaseWhere(paraMap, values);

			pstmt = conn.prepareStatement(sql);
			
			int index = 1;
			for (String value : values) {
			    pstmt.setString(index++, value);
			}
			
			rs = pstmt.executeQuery();
			rs.next();
			totalCountPurchase = rs.getInt("TOTALPURCHASECOUNT");
			
		} finally {
			close();
		}
		
		return totalCountPurchase;
	} // end of public int getTotalCountPurchase(Map<String, String> paraMap) throws SQLException----------------

    // 현재 페이지의 발주 목록 페이징 처리 O
	@Override
	public List<Map<String, String>> selectPurchaseList(Map<String, String> paraMap) throws SQLException {
		List<Map<String, String>> purchaseList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			List<String> values = new ArrayList<>();
			String where = buildPurchaseWhere(paraMap, values);
			
			String sql = " SELECT p.purnum, p.fk_supname, TO_CHAR(p.purtime, 'yyyy-mm-dd') AS purtime "
					   + " , TO_CHAR(p.purdeadline, 'yyyy-mm-dd') AS purdeadline, p.instock,  "
					   + " COUNT(d.fk_purnum) AS product_count,  "
					   + " NVL(SUM(d.purqty), 0) AS total_quantity  "
					   + " FROM tbl_purchase p  "
					   + " LEFT JOIN tbl_purdetail d  "
					   + " ON p.purnum = d.fk_purnum  "
					   + where
					   + " GROUP BY p.purnum, p.fk_supname, p.purtime, p.purdeadline, p.instock  "
					   + " ORDER BY p.purtime DESC, p.purnum DESC  "
					   + " OFFSET (TO_NUMBER(?) - 1) * 10 ROWS  "
					   + " FETCH NEXT 10 ROWS ONLY  ";
			
			pstmt = conn.prepareStatement(sql);
			int index = 1;                                          
			for (String value : values) {
			    pstmt.setString(index++, value);
			}
			pstmt.setString(index, paraMap.get("currentShowPageNo"));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Map<String, String> map = new HashMap<>();
				
				map.put("purnum", String.valueOf(rs.getInt("purnum")));
				map.put("fk_supname", rs.getString("fk_supname"));
				map.put("purtime", rs.getString("purtime"));
				map.put("purdeadline", rs.getString("purdeadline"));
				map.put("instock", rs.getString("instock"));
				map.put("product_count", String.valueOf(rs.getInt("product_count")));
				map.put("total_quantity", String.valueOf(rs.getInt("total_quantity")));
				
				purchaseList.add(map);
				
			}
		} finally {
			close();
		}
		
		return purchaseList;
	} // end of public List<Map<String, String>> selectPurchaseList(Map<String, String> paraMap) throws SQLException--------

	

	// 입고처리 버튼 클릭시 재고 테이블의 수량 UPDATE
	@Override
	public int purdetailStockUpdate(String purnum) throws SQLException {

		int updateSuccessCount = 0; // 성공한 UPDATE 건수를 누적할 변수
		
		// 발주 상세 N 건 조회
		// 발주 상세 테이블에서 해당 발주 번호에 대한 발주 상세건 조회
		// 발주 상세건에 대해서 List에 저장
		// 이후 List의 크기만큼 반복하면서 UPDATE
		// Update 시 재고테이블의 snum과 같은것을 확인하여 List에서 꺼낸 후 발주 상세의 수량을 합침
		// 변경 결과는 boolean 이면 될듯
		List<Map<String, Integer>> purdetailList = new ArrayList<>();
		try {
			// Transaction 처리
			conn = ds.getConnection();
			conn.setAutoCommit(false);   // 오토커밋 해제
			
			// 발주 상세 테이블에서 해당 발주 번호에 대한 발주 상세건 조회
			String sql = " SELECT fk_snum, purqty "
					   + " FROM tbl_purdetail "
					   + " WHERE fk_purnum = TO_NUMBER(?) ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, purnum);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Map<String, Integer> map = new HashMap<>();
				
				map.put("fk_snum", rs.getInt("fk_snum"));
				map.put("purqty", rs.getInt("purqty"));
				
				purdetailList.add(map);
			} // end of while-------------
			
			if(purdetailList.size() == 0) {
				System.out.println("현재 선택한 발주번호는 발주상세가 존재하지 않음");
			}
			rs.close();
			pstmt.close();
			
			
			// 이후 List의 크기만큼 반복하면서 UPDATE
			sql = " UPDATE tbl_stock SET sqty = sqty + ? "
			    + " WHERE snum = ? ";
			pstmt = conn.prepareStatement(sql);
			
			for(int i = 0; i < purdetailList.size(); i++) {
			    // List에서 i번째 Map을 꺼낸다.
			    Map<String, Integer> currentMap = purdetailList.get(i);
			    
			    // Map에서 Key("purqty", "fk_snum")를 이용해 정수 값을 추출합니다.
			    int purqty = currentMap.get("purqty");
			    int fk_snum = currentMap.get("fk_snum");
			    
			    // ? 자리에 맞게 파라미터를 세팅합니다.
			    pstmt.setInt(1, purqty);   // 첫 번째 ? (sqty = sqty + ?)
			    pstmt.setInt(2, fk_snum);  // 두 번째 ? (WHERE snum = ?)
			    
			    // 쿼리 실행
			    int row = pstmt.executeUpdate(); 
				
			    updateSuccessCount += row;
			} // end of for-------------------------
			
			// 발주 테이블에서 해당 행 입고여부 '입고'로 바꾸기
			
			pstmt.close();
			
			sql = " UPDATE tbl_purchase SET instock='입고' "
			    + " WHERE purnum = TO_NUMBER(?) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, purnum);
			int n = 0;
			n = pstmt.executeUpdate();
			
			if(n == 1) {
				System.out.println("발주 테이블 입고여부 변경 완료");
			} else {
				System.out.println("발주 테이블 입고여부 변경 실패");
			}
			
			if(updateSuccessCount == purdetailList.size()) {
			    //전부 성공한 경우에만 커밋
			    conn.commit();
			    System.out.println("발주 상품 " + updateSuccessCount + "건 재고 반영 성공! 커밋 완료.");
			    
			} else {
			    // 단 하나라도 반영이 안 되었거나(0건 수정), 비정상적이라면 전체 취소
			    conn.rollback();
			    System.out.println("오류 발생: 요청 건수(" + purdetailList.size() + ")와 수정 건수(" + updateSuccessCount + ") 불일치로 인한 롤백 처리.");
			}
			
			pstmt.close();
		}  catch (SQLException | NumberFormatException e) {
	        e.printStackTrace();
	        if (conn != null) conn.rollback();
	        updateSuccessCount = 0;
	        
	    } finally {
	    	if (conn != null) conn.setAutoCommit(true);   // 커넥션 풀이면 원복
			close();
			
		}
		
		return updateSuccessCount;
		
	} // end of public int purdetailStockUpdate(String purnum) throws SQLException-------------------

	
	
}









