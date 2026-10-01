package shop.kimkc.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import shop.kimkc.domain.CatalogueDTO;
import shop.kimkc.domain.ProductDTO;


public class ProductDAO_imple implements ProductDAO {
	
	private DataSource ds; // DataSource ds 가 DBCP(DB Connection Pool)이다. 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	// 생성자
	public ProductDAO_imple() {
		
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

	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	

	// 검색키워드를 적용한 상품목록 가져오기
	@Override
	public List<ProductDTO> getProductList(String searchKeyword) throws SQLException {
		
		List<ProductDTO> productList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
					
			
			String sql = " SELECT A.pnum, A.pimage1, B.pname, B.brand, B.saleprice, B.regprice "
					+ " FROM tbl_product A JOIN tbl_catalogue B "
					+ " ON A.fk_pname = B.pname "
					+ " WHERE A.fk_pname LIKE ? "
					+ " ORDER BY A.pnum ASC "; 
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "%" + searchKeyword + "%");
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				//System.out.println("값이 있긴함");
				ProductDTO pdto = new ProductDTO();
				pdto.setPnum(rs.getInt("pnum"));
				pdto.setPimage1(rs.getString("pimage1"));
				System.out.println("판매번호 : " + rs.getInt("pnum"));
				
				CatalogueDTO cdto = new CatalogueDTO();
				cdto.setPname(rs.getString("pname"));
				cdto.setSaleprice(rs.getInt("saleprice"));
				cdto.setRegprice(rs.getInt("regprice"));
				cdto.setBrand(rs.getString("brand"));
				
				pdto.setCatalogueDTO(cdto);
				
				productList.add(pdto);
			}
			
		} finally {
			close();
		}
		
		return productList;
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
