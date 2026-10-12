package admin.catalogue.jungyj.model;

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

public class CatalogueDAO_imple implements CatalogueDAO {

	private DataSource ds;                // DataSource ds 는 DBCP(Database Connection Pool) 이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	// 생성자
	public CatalogueDAO_imple() {
		try {
			Context initContext = new InitialContext();
	        Context envContext  = (Context)initContext.lookup("java:/comp/env");
	        ds = (DataSource)envContext.lookup("jdbc/myoracle");
	        // lookup()속에 /MyMVC/src/main/webapp/META-INF/context.xml에 지정한 이름을 적어주면 자동 주입됩니다.
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

    // =====================================================================================================================================
	// 카탈로그 등록(INSERT)
	@Override
	public int catalogueRegister(Map<String, String> paraMap) throws SQLException {
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " INSERT INTO tbl_catalogue(pname, fk_catenum, purprice, regprice, saleprice, brand) "
					   + " VALUES (?, TO_NUMBER(?), TO_NUMBER(?), TO_NUMBER(?), TO_NUMBER(?), ?) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, paraMap.get("pname"));
			pstmt.setString(2, paraMap.get("fk_catenum"));
			pstmt.setString(3, paraMap.get("pnamepurprice"));
			pstmt.setString(4, paraMap.get("regprice"));
			pstmt.setString(5, paraMap.get("saleprice"));
			pstmt.setString(6, paraMap.get("brand"));

			result = pstmt.executeUpdate();
			
			if(result == 1) {
				System.out.println("[INFO] CatalogueDAO_imple Class tbl_catalogue INSERT Success");
			} else {
				System.out.println("[ERROR] CatalogueDAO_imple Class tbl_catalogue INSERT Fail");
			}
			
		} finally {
			close();
		}
		
		return result;
	} // end of public int catalogueRegister(Map<String, String> paraMap) throws SQLException -------------------------------

	
	// 제품명 중복검사
	@Override
	public boolean pnameDuplicateCheck(String pname) throws SQLException {
		
		boolean isExists = false;
		
		try {
			conn = ds.getConnection();
			
			String sql = " SELECT pname " 
					   + " FROM tbl_catalogue "
					   + " WHERE pname = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, pname);
			
			rs = pstmt.executeQuery();
			
			
			isExists = rs.next();  // 행이 있으면(중복된 pname) true,
			                       // 행이 없으면(사용가능한 pname) false,
		} finally {
			close();
		}
			
		return isExists;

	} // end of public boolean pnameDuplicateCheck(String pname) throws SQLException-------------------------------------

	
	// 카탈로그 개수 조회(SELECT)
	@Override
	public int getTotalCountCatalogue(Map<String, String> paraMap) throws SQLException {

	    int totalCount = 0;

	    try {
	    	conn = ds.getConnection();
	    	
		    String sql = " SELECT COUNT(*) "
		               + " FROM tbl_catalogue catal "
		               + " INNER JOIN tbl_category cate "
		               + " ON catal.fk_catenum = cate.catenum ";

		    List<String> conditions = new ArrayList<>();

		    String productName = paraMap.get("productName");
		    String categoryName = paraMap.get("categoryName");
		    String brandName = paraMap.get("brandName");

		    if (productName != null && !productName.trim().isEmpty()) {
		        conditions.add(" catal.pname LIKE '%' || ? || '%' ");
		    }

		    if (categoryName != null && !categoryName.trim().isEmpty()) {
		        conditions.add(" cate.catename LIKE '%' || ? || '%' ");
		    }

		    if (brandName != null && !brandName.trim().isEmpty()) {
		        conditions.add(" catal.brand LIKE '%' || ? || '%' ");
		    }

		    if (!conditions.isEmpty()) {
		        sql += " WHERE " + String.join(" AND ", conditions);
		    }

		    pstmt = conn.prepareStatement(sql);

		    int index = 1;

		    if (productName != null && !productName.trim().isEmpty()) {
		        pstmt.setString(index++, productName);
		    }

		    if (categoryName != null && !categoryName.trim().isEmpty()) {
		        pstmt.setString(index++, categoryName);
		    }

		    if (brandName != null && !brandName.trim().isEmpty()) {
		        pstmt.setString(index++, brandName);
		    }

		    rs = pstmt.executeQuery();

		    if (rs.next()) {
		        totalCount = rs.getInt(1);
		    }

		} finally {
			close();
		}

	    return totalCount;
	} // end of public int getTotalCountCatalogue(Map<String, String> paraMap) throws SQLException


	// 검색내역이 존재하면 존재하는 검색내용을 기준으로 구분하여 카탈로그를 페이징 처리하여 조회해온다.
	@Override
	public List<Map<String, String>> getCatalogueList(Map<String, String> paraMap) throws SQLException {
		List<Map<String, String>> catalogue_map_List = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " SELECT catal.pname, "
					+ "       catal.purprice, "
					+ "       catal.regprice, "
					+ "       catal.saleprice, "
					+ "       catal.brand, "
					+ "       cate.catename "
					+ " FROM tbl_catalogue catal "
					+ " INNER JOIN tbl_category cate "
					+ " ON catal.fk_catenum = cate.catenum "; 
					

			List<String> conditions = new ArrayList<>();
			List<String> values = new ArrayList<>();
			
			String productName = (String) paraMap.get("productName");
			String categoryName = (String) paraMap.get("categoryName");
			String brandName = (String) paraMap.get("brandName");
			
			if (productName != null && !productName.trim().isEmpty()) {
			    conditions.add(" catal.pname LIKE '%' || ? || '%' ");
			    values.add(productName);
			}
			
			if (categoryName != null && !categoryName.trim().isEmpty()) {
			    conditions.add(" cate.catename LIKE '%' || ? || '%' ");
			    values.add(categoryName);
			}
			
			if (brandName != null && !brandName.trim().isEmpty()) {
			    conditions.add(" catal.brand LIKE '%' || ? || '%' ");
			    values.add(brandName);
			}
			
			if (!conditions.isEmpty()) {
			    sql += " WHERE " + String.join(" AND ", conditions);
			}
			
			sql += " ORDER BY catal.pname DESC, catal.brand DESC "
			     + " OFFSET (TO_NUMBER(?)-1) * 10 ROWS "
			     + " FETCH NEXT 10 ROWS ONLY ";			
	
			pstmt = conn.prepareStatement(sql);

			int index = 1;

			for (String value : values) {
			    pstmt.setString(index++, value);
			}
			
			pstmt.setString(index, paraMap.get("currentShowPageNo"));

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String pname = rs.getString("pname");
				String purprice = String.valueOf(rs.getInt("purprice"));
				String regprice =  String.valueOf(rs.getInt("regprice"));
				String saleprice =  String.valueOf(rs.getInt("saleprice"));
				String brand = rs.getString("brand");
				String catename = rs.getString("catename");
				
				Map<String, String> catalMap = new HashMap<>();
				
				catalMap.put("pname", pname);
				catalMap.put("purprice", purprice);
				catalMap.put("regprice", regprice);
				catalMap.put("saleprice", saleprice);
				catalMap.put("brand", brand);
				catalMap.put("catename", catename);
				
				catalogue_map_List.add(catalMap);
				
			} // end of while()-------------------------------------
			
		} finally {
			close();
		}
		
		return catalogue_map_List;
	} // end of public List<Map<String, String>> getCatalogueList(Map<String, String> paraMap) throws SQLException------------------

	
	// 정가, 판매가, 구매가, 브랜드 값 변경(UPDATE)
	@Override
	public int catalogueEdit(Map<String, String> paraMap) throws SQLException {
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " UPDATE tbl_catalogue SET purprice = ?, regprice = ?, saleprice = ?, brand = ? "
					   + " WHERE pname = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, Integer.parseInt(paraMap.get("purprice")));
			pstmt.setInt(2, Integer.parseInt(paraMap.get("regprice")));
			pstmt.setInt(3, Integer.parseInt(paraMap.get("saleprice")));
			pstmt.setString(4, paraMap.get("brand"));
			pstmt.setString(5, paraMap.get("pname"));
			
			result = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return result;
	} // end of public int catalogueEdit(Map<String, String> paraMap) throws SQLException-----------------------

	
	// 카탈로그 삭제(DELETE)
	@Override
	public int catalogueDelete(String pname) throws SQLException {
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " DELETE FROM tbl_catalogue "
					   + " WHERE pname = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, pname);
	
			result = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return result;
	} // end of public int catalogueDelete(String pname) throws SQLException--------------------------------
	
	
    
}
